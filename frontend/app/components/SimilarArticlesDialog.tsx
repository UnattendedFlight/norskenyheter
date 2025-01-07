"use client";

import {Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle} from "@/components/ui/dialog";
import {useAtom} from "jotai/react";
import {selectedArticleAtom} from "@/lib/atoms/article";
import {useMemo} from "react";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import {Article} from "@/lib/api/types";
import {ArticleCard} from "@/app/components/ArticleCard";
import Image from "next/image";
import HoverOver from "@/app/components/HoverOver";

export default function SimilarArticlesDialog() {
    const [article, setArticle] = useAtom(selectedArticleAtom)

    const open = useMemo(() => article !== null, [article]);
    const onClose = () => setArticle(null);

    const similarArticlesQuery = useQuery({
        queryKey: ["articles", article?.id ?? null, "similar"],
        queryFn: async () => {
            const {data} = await axios.get<Array<Article>>(`http://localhost:8080/api/articles/${article.id}/similar`);
            return data;
        },
        enabled: open
    })
    return (
        <Dialog open={open} onOpenChange={onClose}>
            <DialogContent className={"max-w-[90vw]"}>
                <DialogHeader>
                    <DialogTitle>Lignende artikler</DialogTitle>
                    <DialogDescription>
                        Her er noen artikler som ligner på den du ser på
                    </DialogDescription>
                </DialogHeader>
                {/*Article*/}
                {article && (
                    <div className={"flex gap-4"}>
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
                                    <h2 className={"text-lg font-semibold"}>{article?.aiGeneratedTitle}</h2>
                                )}
                            >
                                <p className={"text-xs text-gray-500"}>
                                    {article?.originalTitle}
                                </p>
                            </HoverOver>

                            <p>{article?.summary}</p>
                        </div>
                    </div>
                )}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 overflow-y-auto max-h-[50vh]">
                    {similarArticlesQuery.data?.map(article => (
                        <ArticleCard article={article} setNewArticleData={(a) => {
                        }} key={`similar-article-${article.articleId}`} onSelectArticle={setArticle}/>
                    ))}
                </div>
            </DialogContent>
        </Dialog>
    );
}