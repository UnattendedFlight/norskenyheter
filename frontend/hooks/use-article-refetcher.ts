import {Article, ArticleStatus} from "@/lib/api/types";
import {useCallback, useEffect, useRef} from "react";
import axios from "axios";
import {API_URL} from "@/lib/constants/global";

export default function useArticleRefetcher(article: Article, updateArticle: (article: Article) => void) {
    const refetchRef = useRef<NodeJS.Timeout | null>(null);
    const sendingRefetchRef = useRef(false);

    const checkStatus = useCallback(async () => {
        const {data} = await axios.get<Article>(`${API_URL}/api/articles/${article.id}`);
        if (!data || data.status !== ArticleStatus.COMPLETED) {
            refetchRef.current = setTimeout(() => {
                checkStatus();
            }, 2500);
        } else {
            if (data) {
                updateArticle(data);
            }
        }
    }, [article.id, updateArticle]);

    const refetchData = useCallback(async (e: any) => {
        e.preventDefault();
        e.stopPropagation();
        if (sendingRefetchRef.current) {
            return;
        }
        console.log("Refetching data");
        if (refetchRef.current) {
            clearTimeout(refetchRef.current);
        }
        sendingRefetchRef.current = true;
        await axios.put(`${API_URL}/api/articles/${article.id}/refetch`);
        sendingRefetchRef.current = false;
        updateArticle({
            ...article,
            status: ArticleStatus.PROCESSING
        });
        refetchRef.current = setTimeout(() => {
            checkStatus();
        }, 2500);
    }, [checkStatus, article, updateArticle]);

    useEffect(() => {
        return () => {
            if (refetchRef.current) {
                clearTimeout(refetchRef.current);
            }
        };
    }, []);

    return refetchData;
}