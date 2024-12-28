export interface FeedItem {
    id: string;
    title: string;
    description: string;
    url: string;
    publishedAt: string;
    category?: string;
    imageUrl?: string;
    source: NewsSite;
}

/*
  vg:
    name: VG
  nrk:
    name: NRK
  db:
    name: Dagbladet
  ap:
    name: Aftenposten
  tv2:
    name: TV2
  na:
    name: Nettavisen
  bt:
    name: Bergens Tidende
  ab:
    name: Aftenbladet
  aa:
    name: Adresseavisen
 */

export enum NewsSite {
    VG = 'VG',
    NRK = 'NRK',
    DAGBLADET = 'DB',
    AFTENPOSTEN = 'AP',
    TV2 = 'TV2',
    NETTAVISEN = 'NA',
    BERGENS_TIDENDE = 'BT',
    AFTENBLADET = 'AB',
    ADRESSEAVISEN = 'AA'


}

export interface VGFeedRawItem {
    title: string;
    pubDate: string;
    description?: string;
    link: string;
    guid: string;
    "vg:waplink"?: string;
    "vg:body"?: string;
    category?: string;
    "vg:img"?: string;
    "vg:articleImg"?: string;
    image?: string;
    imgRegular?: string;
    enclosure?: {
        url: string;
        type: string;
    };
}

export interface DagbladetFeedRawItem {
    title: string;
    link: string;
    guid: string;
    pubDate?: string;
    description?: string;
    enclosure?: {
        url: string;
        length: string;
        type: string;
    };
}

export interface AftenpostenFeedRawItem {
    title: string;
    link: string;
    guid: string;
    pubDate: string;
    description?: string;
    enclosure?: {
        url: string;
        length: string;
        type: string;
    };
}

export interface NRKFeedRawItem {
    title: string;
    link: string;
    description?: string;
    category?: string | string[];
    pubDate: string;
    guid: string;
    "dc:creator"?: string;
    "dc:date"?: string;
    "media:content"?: Array<{
        medium?: string;
        type?: string;
        url?: string;
    }>;
}
