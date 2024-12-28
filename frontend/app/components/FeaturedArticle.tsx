import {Badge} from "@/components/ui/badge"
import {Button} from "@/components/ui/button"
import Link from "next/link"
import {Article, ArticleStatus} from "@/lib/api/types";
import moment from "moment";
import useArticleRefetcher from "@/hooks/use-article-refetcher";
import {RefreshCw, ShieldAlert} from "lucide-react";
import {cn} from "@/lib/utils";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover";
import {useState} from "react";
import HoverOver from "@/app/components/HoverOver";

interface FeaturedArticleProps {
    article: Article;
    setNewArticleData: (article: Article) => void;
}

export function FeaturedArticle({article, setNewArticleData}: FeaturedArticleProps) {
    const refetchData = useArticleRefetcher(article, setNewArticleData);
    const [open, setOpen] = useState(false);
    return (
        <div className="relative h-[50vh] min-h-[400px] w-full overflow-hidden rounded-lg">
            <div
                className="absolute inset-0 bg-cover bg-center"
                style={{backgroundImage: `url(${article.imageUrl})`}}
            >
                <div className="absolute inset-0 bg-gradient-to-t from-background via-background/80 to-transparent"/>
            </div>
            <div className="absolute inset-0 flex flex-col justify-end p-6 md:p-8">
                <div className="space-y-4">
                    <div className="flex items-center gap-2">
                        {article.paywalled && (
                            <HoverOver
                                trigger={(
                                    <Badge variant={"secondary"} className={"bg-background/50 backdrop-blur-sm"}>
                                        <ShieldAlert className={"h-4 w-4 text-red-500"}/>
                                    </Badge>
                                )}
                            >
                                <p className={"text-xs text-gray-500"}>
                                    Denne artikkelen krever abonnement hos {article.source}
                                </p>
                            </HoverOver>
                        )}
                        <Badge variant="outline" className="bg-background/50 backdrop-blur-sm">
                            {article.source}
                        </Badge>
                    </div>
                    <Popover open={open} onOpenChange={setOpen}>
                        <PopoverTrigger onMouseEnter={() => setOpen(true)} onMouseLeave={() => setOpen(false)}>
                            <h2 className="text-2xl md:text-4xl font-bold leading-tight text-start">
                                {article.aiGeneratedTitle}
                            </h2>
                        </PopoverTrigger>
                        <PopoverContent>
                            <p>{article.originalTitle}</p>
                        </PopoverContent>
                    </Popover>
                    <p className="max-w-[600px] text-muted-foreground md:text-lg">
                        {article.summary}
                    </p>
                    <div className="flex items-center gap-4">
                        <span className="text-sm text-muted-foreground">
              {moment(article.publishedAt).fromNow()}
            </span>
                    </div>
                    <div className="flex gap-2">
                        <Button asChild>
                            <Link href={article.url}>Les mer</Link>
                        </Button>
                        <Button
                            variant={"ghost"}
                            size={"icon"}
                            onClick={refetchData}
                        >
                            <RefreshCw className={cn("h-4 w-4", {
                                "animate-spin": article.status === ArticleStatus.PROCESSING
                            })}/>
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    )
}

