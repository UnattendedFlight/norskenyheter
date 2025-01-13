"use client";

import {Dialog, DialogContent, DialogDescription, DialogTitle} from "@/components/ui/dialog";
import {useAtom} from "jotai/react";
import {selectedArticleAtom} from "@/lib/atoms/article";
import {useMemo} from "react";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import {Article} from "@/lib/api/types";
import {ArticleCard} from "@/app/components/ArticleCard";
import Image from "next/image";
import HoverOver from "@/app/components/HoverOver";
import {API_URL} from "@/lib/constants/global";

export default function SimilarArticlesDialog() {
    const [article, setArticle] = useAtom(selectedArticleAtom)

    const open = useMemo(() => article !== null, [article]);
    const onClose = () => setArticle(null);

    const similarArticlesQuery = useQuery({
        queryKey: ["articles", article?.id ?? null, "similar"],
        queryFn: async () => {
            if (!article) return [];
            const {data} = await axios.get<Array<Article>>(`${API_URL}/api/articles/${article?.id}/similar`);
            return data;
        },
        enabled: open && article !== null
    })
    return (
        <Dialog open={open} onOpenChange={onClose}>
            <DialogContent className={"max-w-[90vw] rounded-xl overflow-hidden px-0 md:px-4 py-0 md:py-4"}>
                {/*Article*/}
                <div className={"max-h-[90vh] overflow-y-auto px-4 md:px-0 py-4 md:py-4 md:pb-0"}>
                    {article && (
                        <>
                            <div className={"flex gap-4 flex-col md:flex-row"}>
                                <div className={"flex-shrink-0"}>
                                    <Image
                                        src={article.imageUrl || "/#"}
                                        alt={article.aiGeneratedTitle || article.originalTitle}
                                        width={600}
                                        height={400}
                                        className="w-full h-48 object-cover rounded-xl overflow-hidden"
                                        style={{aspectRatio: "600/400"}}
                                        objectFit={"cover"}
                                    />
                                </div>
                                <div>
                                    <HoverOver
                                        trigger={(
                                            <h2 className={"text-lg font-semibold text-left leading-none"}>{article?.aiGeneratedTitle}</h2>
                                        )}
                                    >
                                        <p className={"text-xs text-gray-500"}>
                                            {article?.originalTitle}
                                        </p>
                                    </HoverOver>

                                    <p className={"text-sm text-muted-foreground"}>{article?.summary}</p>
                                </div>
                            </div>
                            {/*Separator */}
                            <div className={"border-b border-gray-200 my-16"}/>
                        </>
                    )}
                    <div className={"mb-4"}>
                        <DialogTitle>Lignende artikler</DialogTitle>
                        <DialogDescription>
                            Her er noen artikler som ligner på den du ser på
                        </DialogDescription>
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {similarArticlesQuery.data?.map(article => (
                            <ArticleCard article={article} setNewArticleData={() => {
                            }} key={`similar-article-${article.articleId}`} onSelectArticle={setArticle}/>
                        ))}
                        {similarArticlesQuery.isLoading && (
                            Array.from({length: 5}, (_, i) => (
                                <div key={`placeholder-${i}`} className="animate-pulse gap-1 flex flex-col">
                                    <div className="h-48 bg-gray-200 rounded-md"/>
                                    <div className="h-4 bg-gray-200 w-1/2 rounded-md"/>
                                    <div className="h-4 bg-gray-200 w-1/4 rounded-md"/>
                                </div>
                            ))
                        )}
                    </div>
                </div>
            </DialogContent>
        </Dialog>
    );
}