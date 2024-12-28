export type RssItemAP = {
    title: string;
    pubDate: string;
    description: string;
    link: string;
    guid: string;
    enclosure: {
        url: string;
        length: string;
        type: string;
    }
}