export type RssItemVG = {
    title: string;
    pubDate: string;
    description: string;
    link: string;
    guid: string;
    category: string;
    // VG specific fields with namespace
    "vg:waplink": string;
    "vg:body": string;
    "vg:img": string;
    "vg:articleImg": string;
    // Image fields
    image: string;
    imgRegular: string;
    enclosure: {
        url: string;
        type: string;
    };
}