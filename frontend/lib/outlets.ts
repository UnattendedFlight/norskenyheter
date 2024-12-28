import {NewsSite} from "@/lib/feed/types";

export type Outlet = {
    id: NewsSite;
    name: string;
    type: "RSS" | "API";
    url: string;
    description: string;
}
const outlets = [
    {
        id: NewsSite.VG,
        name: "VG",
        type: "RSS",
        url: "https://www.vg.no/rss/feed",
        description: "Verdens Gang"
    },
    {
        id: NewsSite.DAGBLADET,
        name: "Dagbladet",
        type: "RSS",
        url: "https://www.dagbladet.no/?lab_viewport=rss",
        description: "Dagbladet"
    },
    {
        id: NewsSite.AFTENPOSTEN,
        name: "Aftenposten",
        type: "RSS",
        url: "https://www.aftenposten.no/rss",
        description: "Aftenposten"
    },
    {
        id: NewsSite.NRK,
        name: "NRK",
        type: "RSS",
        url: "https://www.nrk.no/toppsaker.rss",
        description: "NRK"
    },
    /*
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
    {
        id: NewsSite.TV2,
        name: "TV2",
        type: "RSS",
        url: "https://www.tv2.no/rss",
        description: "TV2"
    },
    {
        id: NewsSite.NETTAVISEN,
        name: "Nettavisen",
        type: "RSS",
        url: "https://www.nettavisen.no/rss",
        description: "Nettavisen"
    },
    {
        id: NewsSite.BERGENS_TIDENDE,
        name: "Bergens Tidende",
        type: "RSS",
        url: "https://www.bt.no/rss",
        description: "Bergens Tidende"
    },
    {
        id: NewsSite.AFTENBLADET,
        name: "Aftenbladet",
        type: "RSS",
        url: "https://www.aftenbladet.no/rss",
        description: "Aftenbladet"
    },
    {
        id: NewsSite.ADRESSEAVISEN,
        name: "Adresseavisen",
        type: "RSS",
        url: "https://www.adressa.no/rss/",
        description: "Adresseavisen"
    }
] as const satisfies readonly Outlet[];

// Extract the ID type
export type AvailableOutlets = (typeof outlets)[number]['id'] | "all";


export default outlets;