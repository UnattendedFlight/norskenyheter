import {FeedItem, NewsSite} from "@/lib/feed/types";
import {
    transformAftenpostenItem,
    transformDagbladetItem,
    transformNRKItem,
    transformVGItem
} from "@/lib/feed/transformers";

export const transformFeedItems = (items: unknown[], source: NewsSite): FeedItem[] => {
    const transformers: Record<NewsSite, (item: any) => FeedItem | null> = {
        [NewsSite.VG]: transformVGItem,
        [NewsSite.DAGBLADET]: transformDagbladetItem,
        [NewsSite.AFTENPOSTEN]: transformAftenpostenItem,
        [NewsSite.NRK]: transformNRKItem
    };

    const transformer = transformers[source];
    if (!transformer) {
        throw new Error(`No transformer found for source: ${source}`);
    }

    return items
        .map(transformer)
        .filter((item): item is FeedItem => item !== null);
};