import {feedUtils} from "@/lib/feed/utils";
import {
    AftenpostenFeedRawItem,
    DagbladetFeedRawItem,
    FeedItem,
    NewsSite,
    NRKFeedRawItem,
    VGFeedRawItem
} from "@/lib/feed/types";

export const transformVGItem = (item: VGFeedRawItem): FeedItem => {
    const findBestImage = (item: VGFeedRawItem): string | undefined => {
        return item["vg:img"] ||
            item["vg:articleImg"] ||
            item.image ||
            item.imgRegular ||
            item.enclosure?.url;
    };

    return {
        id: item.guid,
        title: item.title,
        description: item.description || '',
        url: item.link,
        publishedAt: feedUtils.formatDate(item.pubDate),
        category: item.category,
        imageUrl: findBestImage(item),
        source: NewsSite.VG
    };
};


export const transformDagbladetItem = (item: DagbladetFeedRawItem): FeedItem | null => {
    if (!feedUtils.isValidGuid(item.guid)) {
        return null;
    }

    return {
        id: item.guid,
        title: item.title,
        description: item.description || '',
        url: item.link,
        publishedAt: item.pubDate ? feedUtils.formatDate(item.pubDate) : new Date().toISOString(),
        imageUrl: item.enclosure?.url,
        source: NewsSite.DAGBLADET
    };
};

export const transformAftenpostenItem = (item: AftenpostenFeedRawItem): FeedItem | null => {
    if (!feedUtils.isValidGuid(item.guid)) {
        return null;
    }

    return {
        id: item.guid,
        title: item.title,
        description: item.description || '',
        url: item.link,
        publishedAt: feedUtils.formatDate(item.pubDate),
        imageUrl: item.enclosure?.url,
        source: NewsSite.AFTENPOSTEN
    };
}

export const transformNRKItem = (item: NRKFeedRawItem): FeedItem | null => {
    if (!feedUtils.isValidGuid(item.guid)) {
        return null;
    }

    const imageContent = item["media:content"]?.find(content =>
        content.medium === 'image' || content.type?.startsWith('image/'));

    return {
        id: item.guid,
        title: item.title,
        description: item.description || '',
        url: item.link,
        publishedAt: feedUtils.formatDate(item.pubDate),
        imageUrl: imageContent?.url,
        source: NewsSite.NRK
    };
}