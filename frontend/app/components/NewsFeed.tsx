"use client";

import {Card, CardContent, CardFooter, CardHeader} from "@/components/ui/card"
import {InfiniteData, useInfiniteQuery, useQuery, useQueryClient} from "@tanstack/react-query";
import axios from "axios";
import {useAtomValue, useSetAtom} from "jotai/react";
import {outletStatsAtom, selectedOutletAtom} from "@/lib/atoms/outlet";
import {useCallback, useEffect, useMemo, useRef} from "react";
import {Article, ArticlePageResponse, ArticleStats} from "@/lib/api/types";
import outlets from "@/lib/outlets";
import {headerSearchAtom} from "@/lib/atoms/search";
import {FeaturedArticle} from "@/app/components/FeaturedArticle";
import {ArticleCard} from "@/app/components/ArticleCard";

export default function NewsFeed() {
    const outlet = useAtomValue(selectedOutletAtom);
    const headerSearchString = useAtomValue(headerSearchAtom);
    const outletData = useMemo(() => outlets.find((o) => o.id === outlet), [outlet]);
    const loadMoreRef = useRef<HTMLDivElement>(null);
    const canObserveRef = useRef(false);
    const setArticleStats = useSetAtom(outletStatsAtom)
    const PAGE_SIZE = 100;

    const query = useInfiniteQuery({
        queryKey: ["news", outletData?.id, {
            query: headerSearchString,
            size: PAGE_SIZE
        }],
        queryFn: async ({pageParam = 0}) => {
            if (outlet !== "all") {
                if (!outletData || !outletData.url) {
                    throw new Error("Outlet not found");
                }
            }

            const params: any = {
                status: "COMPLETED",
                page: pageParam,
                size: PAGE_SIZE,
                query: headerSearchString,
            }

            if (outlet !== "all" && outletData) {
                params.source = outletData.name;
            }
            const response = await axios.get<ArticlePageResponse>(`http://localhost:8080/api/articles`, {
                params,
            });
            return response.data;
        },
        getNextPageParam: (lastPage) => {
            const currPage = lastPage.number;
            const lastPageNumber = lastPage.totalPages;
            return currPage + 1 < lastPageNumber ? currPage + 1 : undefined;
        },
        getPreviousPageParam: (firstPage) => {
            const currPage = firstPage.number;
            return currPage > 0 ? currPage - 1 : undefined;
        },
        initialPageParam: 0,
        refetchInterval: 60000,
        refetchIntervalInBackground: true,
        retry: false
    });

    const articleStats = useQuery({
        queryKey: ["news", "stats"],
        queryFn: async () => {
            const response = await axios.get<ArticleStats>(`http://localhost:8080/api/articles/stats`);
            setArticleStats(response.data);
            return response.data;
        },
        refetchInterval: 60000,
        refetchIntervalInBackground: true,
        retry: false,
        initialData: {
            totalArticles: 0,
            articlesPerSource: {},
        } as ArticleStats
    });

    const queryClient = useQueryClient();

    useEffect(() => {
        setTimeout(() => {
            canObserveRef.current = true;
        }, 1000);
        const observer = new IntersectionObserver(
            (entries) => {
                const target = entries[0];
                if (target.isIntersecting && !query.isFetching && query.hasNextPage && canObserveRef.current) {
                    query.fetchNextPage();
                }
            },
            {
                root: null,
                rootMargin: '800px', // Start loading 200px before reaching the end
                threshold: 0.1
            }
        );

        if (loadMoreRef.current) {
            observer.observe(loadMoreRef.current);
        }

        return () => observer.disconnect();
    }, [query.isFetching, query.hasNextPage]);

    const featuredArticle = useMemo(() => {
        if (!query.isSuccess) {
            return null;
        }
        return query.data.pages.flatMap((p) => p.content).find((a) => a.aiGeneratedTitle && a.imageUrl);
    }, [query.isSuccess, query.data]);

    const setNewArticleData = useCallback((newArticle: Article) => {
        queryClient.setQueriesData({
            queryKey: ["news", outletData?.id, {
                query: headerSearchString,
                size: PAGE_SIZE
            }],
            exact: true,
        }, (oldData: InfiniteData<ArticlePageResponse>) => {
            if (!oldData) {
                return oldData;
            }
            return {
                ...oldData,
                pages: oldData.pages.map((page) => ({
                    ...page,
                    content: page.content.map((a) => a.id === newArticle.id ? newArticle : a)
                }))
            };
        })
    }, [queryClient, outletData, headerSearchString]);

    const articles = useMemo(() => {
        if (!query.isSuccess) {
            return [];
        }
        const items: Article[] = [];
        for (const page of query.data.pages) {
            for (const article of page.content) {
                if (featuredArticle) {
                    if (article.id === featuredArticle.id) {
                        continue;
                    }
                }
                items.push(article);
            }
        }
        return items;
    }, [query.isSuccess, query.data, featuredArticle]);
    return (
        <div className="space-y-6">
            {outlet === "all" ? (
                <span className="text-gray-500">
                    {articleStats.data.totalArticles} artikler
                </span>
            ) : (
                <span className="text-gray-500">
                    {articleStats.data.articlesPerSource[outletData?.name as string] || 0} artikler fra {outletData?.name} (av {articleStats.data.totalArticles})
                </span>
            )}
            {query.isSuccess && featuredArticle && (
                <FeaturedArticle article={featuredArticle} setNewArticleData={setNewArticleData}/>
            )}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {query.isSuccess && articles.map((article) => (
                    <ArticleCard key={article.id} article={article} setNewArticleData={setNewArticleData}/>
                ))}
                {query.hasNextPage && (
                    Array.from({length: 5}, (_, i) => (
                        <Card key={`placeholder-${i}`} className="h-full animate-pulse">
                            <CardHeader className="h-48 bg-gray-200"/>
                            <CardContent className="flex-grow space-y-2 p-4">
                                <div className="h-4 bg-gray-200"/>
                                <div className="h-4 bg-gray-200"/>
                                <div className="h-4 bg-gray-200"/>
                            </CardContent>
                            <CardFooter className="flex justify-between items-center">
                                <div className="h-4 bg-gray-200 w-1/4"/>
                                <div className="h-4 bg-gray-200 w-1/4"/>
                            </CardFooter>
                        </Card>
                    ))
                )}
            </div>

            {/* Loading and end states */}
            <div ref={loadMoreRef} className="py-4 text-center">
                {query.isFetching ? (
                    <div className="flex items-center justify-center space-x-2">
                        <div className="w-3 h-3 bg-blue-500 rounded-full animate-bounce [animation-delay:-0.3s]"></div>
                        <div className="w-3 h-3 bg-blue-500 rounded-full animate-bounce [animation-delay:-0.15s]"></div>
                        <div className="w-3 h-3 bg-blue-500 rounded-full animate-bounce"></div>
                    </div>
                ) : !query.hasNextPage ? (
                    <div className="text-gray-500">Vi har dessverre ikke eldre artikler</div>
                ) : null}
            </div>
        </div>
    );
}