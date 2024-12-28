import {Article, ArticleStatus} from "@/lib/api/types";
import {useState} from "react";
import useArticleRefetcher from "@/hooks/use-article-refetcher";
import Link from "next/link";
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card";
import {cn} from "@/lib/utils";
import Image from "next/image";
import {ImageOff, RefreshCw, ShieldAlert} from "lucide-react";
import HoverOver from "@/app/components/HoverOver";
import {Badge} from "@/components/ui/badge";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover";
import moment from "moment";
import {Button} from "@/components/ui/button";

export function ArticleCard({article, setNewArticleData}: {
    article: Article,
    setNewArticleData: (data: Article) => void
}) {
    const [open, setOpen] = useState(false);
    const refetchData = useArticleRefetcher(article, setNewArticleData);

    return (
        <Link href={article.url} key={article.id} target={"_blank"} className={"h-full"}>
            <div className={"h-full"}>
                <Card className={cn("flex flex-col p-0 overflow-hidden h-full transition-all", {
                    "opacity-50": article.status === ArticleStatus.PROCESSING
                })}>
                    <CardHeader className={"relative overflow-hidden p-0"}>
                        {article.imageUrl ? (
                            <div className="">
                                <Image
                                    src={article.imageUrl}
                                    alt={article.aiGeneratedTitle || article.originalTitle}
                                    width={600}
                                    height={400}
                                    className="w-full h-48 object-cover"
                                    style={{aspectRatio: "600/400"}}
                                    objectFit={"cover"}
                                />
                            </div>
                        ) : (
                            <div className="h-48 bg-gray-200 flex justify-center items-center">
                                <ImageOff className={"h-12 w-12 text-gray-400"}/>
                            </div>
                        )}
                        <div className="absolute top-2 right-2 flex gap-2 items-center">
                            {article.paywalled && (
                                <HoverOver
                                    trigger={(
                                        <Badge variant={"secondary"} className={"shadow-2xl"}>
                                            <ShieldAlert className={"h-4 w-4 text-red-500"}/>
                                        </Badge>
                                    )}
                                >
                                    <p className={"text-xs text-gray-500"}>
                                        Denne artikkelen krever abonnement hos {article.source}
                                    </p>
                                </HoverOver>
                            )}
                            <Badge className="shadow-2xl">{article.source}</Badge>
                        </div>
                    </CardHeader>
                    <CardContent className="flex-grow p-4">
                        <Popover open={open} onOpenChange={setOpen}>
                            <PopoverTrigger onMouseEnter={() => setOpen(true)} onMouseLeave={() => setOpen(false)}>
                                <CardTitle className={"text-start"}>{article.aiGeneratedTitle}</CardTitle>
                            </PopoverTrigger>
                            <PopoverContent>
                                <p>{article.originalTitle}</p>
                                <small className="text-gray-500">{article.id}</small>
                            </PopoverContent>
                        </Popover>
                        <CardDescription>{article.summary}</CardDescription>
                    </CardContent>
                    <CardFooter className="flex justify-between items-center">
                    <span className="text-xs text-gray-500">
                        {moment(article.publishedAt).format("MMM DD, YYYY hh:mm A")}
                    </span>
                        <Button
                            variant={"ghost"}
                            size={"icon"}
                            onClick={refetchData}
                        >
                            <RefreshCw className={cn("h-4 w-4", {
                                "animate-spin": article.status === ArticleStatus.PROCESSING
                            })}/>
                        </Button>
                    </CardFooter>
                </Card>
            </div>
        </Link>
    );
}