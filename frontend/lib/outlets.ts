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
        url: "https://vg.no/rss/feed",
        description: "Verdens Gang"
    },
    {
        id: NewsSite.DAGBLADET,
        name: "Dagbladet",
        type: "RSS",
        url: "https://dagbladet.no/?lab_viewport=rss",
        description: "Dagbladet"
    },
    {
        id: NewsSite.AFTENPOSTEN,
        name: "Aftenposten",
        type: "RSS",
        url: "https://aftenposten.no/rss",
        description: "Aftenposten"
    },
    {
        id: NewsSite.NRK,
        name: "NRK",
        type: "RSS",
        url: "https://nrk.no/toppsaker.rss",
        description: "NRK"
    },
    {
        id: NewsSite.TV2,
        name: "TV2",
        type: "RSS",
        url: "https://tv2.no/rss",
        description: "TV2"
    },
    {
        id: NewsSite.NETTAVISEN,
        name: "Nettavisen",
        type: "RSS",
        url: "https://nettavisen.no/rss",
        description: "Nettavisen"
    },
    {
        id: NewsSite.BERGENS_TIDENDE,
        name: "Bergens Tidende",
        type: "RSS",
        url: "https://bt.no/rss",
        description: "Bergens Tidende"
    },
    {
        id: NewsSite.AFTENBLADET,
        name: "Aftenbladet",
        type: "RSS",
        url: "https://aftenbladet.no/rss",
        description: "Aftenbladet"
    },
    {
        id: NewsSite.ADRESSEAVISEN,
        name: "Adresseavisen",
        type: "RSS",
        url: "https://adressa.no/rss/",
        description: "Adresseavisen"
    },
    {
        id: NewsSite.AVISEN_AGDER,
        name: "Avisen Agder",
        type: "RSS",
        url: "https://avisenagder.no/service/rich-rss",
        description: "Avisen Agder"
    },
    {
        id: NewsSite.AGDERPOSTEN,
        name: "Agderposten",
        type: "RSS",
        url: "https://agderposten.no/rss",
        description: "Agderposten"
    },
    {
        id: NewsSite.GRORUDDALEN,
        name: "Groruddalen",
        type: "RSS",
        url: "https://groruddalen.no/feed/",
        description: "Groruddalen"
    },
    {
        id: NewsSite.AMTA,
        name: "Amta",
        type: "RSS",
        url: "https://amta.no/service/rich-rss",
        description: "Amta"
    },
    {
        id: NewsSite.ALTAPOSTEN,
        name: "Altaposten",
        type: "RSS",
        url: "https://altaposten.no/rss",
        description: "Altaposten"
    },
    {
        id: NewsSite.RETTEN,
        name: "Retten",
        type: "RSS",
        url: "https://retten.no/service/rich-rss",
        description: "Retten"
    },
    {
        id: NewsSite.ARENDALS_TIDENDE,
        name: "Arendals Tidende",
        type: "RSS",
        url: "https://arendalstidende.no/rss",
        description: "Arendals Tidende"
    },
    {
        id: NewsSite.ASKOYVARINGEN,
        name: "Askøyværingen",
        type: "RSS",
        url: "https://askoyv.no/rss",
        description: "Askøyværingen"
    },
    {
        id: NewsSite.AURA_AVIS,
        name: "Aura Avis",
        type: "RSS",
        url: "https://auraavis.no/service/rich-rss",
        description: "Aura Avis"
    },
    {
        id: NewsSite.AUST_AGDER_BLAD,
        name: "Aust-Agder Blad",
        type: "RSS",
        url: "https://austagderblad.no/service/rich-rss",
        description: "Aust-Agder Blad"
    },
    {
        id: NewsSite.AN,
        name: "AN",
        type: "RSS",
        url: "https://an.no/service/rich-rss",
        description: "AN"
    },
    {
        id: NewsSite.BA,
        name: "BA",
        type: "RSS",
        url: "https://ba.no/service/rich-rss",
        description: "Bergensavisen"
    },
    {
        id: NewsSite.BIRKENESAVISA,
        name: "Birkenesavisa",
        type: "RSS",
        url: "https://birkenes-avisa.no/rss",
        description: "Birkenesavisa"
    },
    {
        id: NewsSite.BLADET,
        name: "Bladet",
        type: "RSS",
        url: "https://bladet.no/rss",
        description: "Bladet"
    },
    {
        id: NewsSite.BLV,
        name: "BLV",
        type: "RSS",
        url: "https://blv.no/service/rich-rss",
        description: "BLV"
    },
    {
        id: NewsSite.BODOPOSTEN,
        name: "Bodøposten",
        type: "RSS",
        url: "https://xn--bodposten-n8a.no/feed/",
        description: "Bodøposten"
    },
    {
        id: NewsSite.BUDSTIKKA,
        name: "Budstikka",
        type: "RSS",
        url: "https://budstikka.no/service/rich-rss",
        description: "Budstikka"
    },
    {
        id: NewsSite.BYGDANYTT,
        name: "Bygdanytt",
        type: "RSS",
        url: "https://bygdanytt.no/rss",
        description: "Bygdanytt"
    },
    {
        id: NewsSite.BYGDEBLADET,
        name: "Bygdebladet",
        type: "RSS",
        url: "https://bygdebladet.no/service/rich-rss",
        description: "Bygdebladet"
    },
    {
        id: NewsSite.BYGDEPOSTEN,
        name: "Bygdeposten",
        type: "RSS",
        url: "https://bygdeposten.no/service/rich-rss",
        description: "Bygdeposten"
    },
    {
        id: NewsSite.BOMLO_NYTT,
        name: "Bømlo-nytt",
        type: "RSS",
        url: "https://bomlo-nytt.no/rss",
        description: "Bømlo-nytt"
    },
    {
        id: NewsSite.DAG_OG_TID,
        name: "Dag og Tid",
        type: "RSS",
        url: "https://dagogtid.no/feed",
        description: "Dag og Tid"
    },
    {
        id: NewsSite.DAGSAVISEN,
        name: "Dagsavisen",
        type: "RSS",
        url: "https://dagsavisen.no/arc/outboundfeeds/rss/",
        description: "Dagsavisen"
    },
    {
        id: NewsSite.DALANE_TIDENDE,
        name: "Dalane Tidende",
        type: "RSS",
        url: "https://dalane-tidende.no/service/rich-rss",
        description: "Dalane Tidende"
    },
    {
        id: NewsSite.DIGI,
        name: "Digi",
        type: "RSS",
        url: "https://digi.no/rss",
        description: "Digi"
    },
    {
        id: NewsSite.DT,
        name: "DT",
        type: "RSS",
        url: "https://dt.no/service/rich-rss",
        description: "DT"
    },
    {
        id: NewsSite.DRANGEDALSPOSTEN,
        name: "Drangedalsposten",
        type: "RSS",
        url: "https://drangedalsposten.no/service/rich-rss",
        description: "Drangedalsposten"
    },
    {
        id: NewsSite.DRIVA,
        name: "Driva",
        type: "RSS",
        url: "https://driva.no/rss",
        description: "Driva"
    },
    {
        id: NewsSite.DOLEN,
        name: "Dølen",
        type: "RSS",
        url: "https://dolen.no/rss",
        description: "Dølen"
    },
    {
        id: NewsSite.E24,
        name: "E24",
        type: "RSS",
        url: "https://e24.no/rss",
        description: "E24"
    },
    {
        id: NewsSite.EUB,
        name: "EUB",
        type: "RSS",
        url: "https://eub.no/service/rich-rss",
        description: "EUB"
    },
    {
        id: NewsSite.EIKERNYTT,
        name: "Eikernytt",
        type: "RSS",
        url: "https://eikernytt.no/rss",
        description: "Eikernytt"
    },
    {
        id: NewsSite.ENEBAKK_AVIS,
        name: "Enebakk Avis",
        type: "RSS",
        url: "https://enebakkavis.no/service/rich-rss",
        description: "Enebakk Avis"
    },
    {
        id: NewsSite.ENERGI24,
        name: "Energi24",
        type: "RSS",
        url: "https://energi24.no/rss",
        description: "Energi24"
    },
    {
        id: NewsSite.F7,
        name: "F7",
        type: "RSS",
        url: "https://f7.no/rss",
        description: "F7"
    },
    {
        id: NewsSite.FANAPOSTEN,
        name: "Fanaposten",
        type: "RSS",
        url: "https://fanaposten.no/service/rich-rss",
        description: "Fanaposten"
    },
    {
        id: NewsSite.FIRDA,
        name: "Firda",
        type: "RSS",
        url: "https://firda.no/service/rich-rss",
        description: "Firda"
    },
    {
        id: NewsSite.FIRDA_TIDEND,
        name: "Firda Tidend",
        type: "RSS",
        url: "https://firdatidend.no/rss",
        description: "Firda Tidend"
    },
    {
        id: NewsSite.FIRDAPOSTEN,
        name: "Firdaposten",
        type: "RSS",
        url: "https://firdaposten.no/service/rich-rss",
        description: "Firdaposten"
    },
    {
        id: NewsSite.FITJARPOSTEN,
        name: "Fitjarposten",
        type: "RSS",
        url: "https://fitjarposten.no/rss",
        description: "Fitjarposten"
    },
    {
        id: NewsSite.FJORDABLADET,
        name: "Fjordabladet",
        type: "RSS",
        url: "https://fjordabladet.no/rss",
        description: "Fjordabladet"
    },
    {
        id: NewsSite.FJORDENES_TIDENDE,
        name: "Fjordenes Tidende",
        type: "RSS",
        url: "https://fjt.no/rss",
        description: "Fjordenes Tidende"
    },
    {
        id: NewsSite.FJORDINGEN,
        name: "Fjordingen",
        type: "RSS",
        url: "https://fjordingen.no/rss",
        description: "Fjordingen"
    },
    {
        id: NewsSite.FOLKEBLADET,
        name: "Folkebladet",
        type: "RSS",
        url: "https://folkebladet.no/rss",
        description: "Folkebladet"
    },
    {
        id: NewsSite.FOSNA_FOLKET,
        name: "Fosna-Folket",
        type: "RSS",
        url: "https://fosna-folket.no/rss",
        description: "Fosna-Folket"
    },
    {
        id: NewsSite.FJUKEN,
        name: "Fjuken",
        type: "RSS",
        url: "https://fjuken.no/rss",
        description: "Fjuken"
    },
    {
        id: NewsSite.FRAMTID_I_NORD,
        name: "Framtid i Nord",
        type: "RSS",
        url: "https://framtidinord.no/rss",
        description: "Framtid i Nord"
    },
    {
        id: NewsSite.FREDRIKSSTAD_BLAD,
        name: "Fredriksstad Blad",
        type: "RSS",
        url: "https://f-b.no/service/rich-rss",
        description: "Fredriksstad Blad"
    },
    {
        id: NewsSite.FREMOVER,
        name: "Fremover",
        type: "RSS",
        url: "https://fremover.no/service/rich-rss",
        description: "Fremover"
    },
    {
        id: NewsSite.FROSTINGEN,
        name: "Frostingen",
        type: "RSS",
        url: "https://frostingen.no/service/rich-rss",
        description: "Frostingen"
    },
    {
        id: NewsSite.FAEDRELANDSVENNEN,
        name: "Fædrelandsvennen",
        type: "RSS",
        url: "https://fvn.no/rss",
        description: "Fædrelandsvennen"
    },
    {
        id: NewsSite.GJENGANGEREN,
        name: "Gjengangeren",
        type: "RSS",
        url: "https://gjengangeren.no/service/rich-rss",
        description: "Gjengangeren"
    },
    {
        id: NewsSite.GJESDALBUEN,
        name: "Gjesdalbuen",
        type: "RSS",
        url: "https://gjesdalbuen.no/service/rich-rss",
        description: "Gjesdalbuen"
    },
    {
        id: NewsSite.GLOBALT_PERSPEKTIV,
        name: "Globalt Perspektiv",
        type: "RSS",
        url: "https://globaltperspektiv.no/rss",
        description: "Globalt Perspektiv"
    },
    {
        id: NewsSite.GLOMDALEN,
        name: "Glåmdalen",
        type: "RSS",
        url: "https://glomdalen.no/service/rich-rss",
        description: "Glåmdalen"
    },
    {
        id: NewsSite.GRANNAR,
        name: "Grannar",
        type: "RSS",
        url: "https://grannar.no/feed/",
        description: "Grannar"
    },
    {
        id: NewsSite.GRENDA,
        name: "Grenda",
        type: "RSS",
        url: "https://grenda.no/rss",
        description: "Grenda"
    },
    {
        id: NewsSite.GRIMSTAD_ADRESSETIDENDE,
        name: "Grimstad Adressetidende",
        type: "RSS",
        url: "https://gat.no/rss",
        description: "Grimstad Adressetidende"
    },
    {
        id: NewsSite.GUDBRANDSDOLEN_DAGNINGEN,
        name: "Gudbrandsdølen Dagningen",
        type: "RSS",
        url: "https://gd.no/service/rich-rss",
        description: "Gudbrandsdølen Dagningen"
    },
    {
        id: NewsSite.HADELAND,
        name: "Hadeland",
        type: "RSS",
        url: "https://hadeland.net/service/rich-rss",
        description: "Hadeland"
    },
    {
        id: NewsSite.HALDEN_ARBEIDERBLAD,
        name: "Halden Arbeiderblad",
        type: "RSS",
        url: "https://ha-halden.no/service/rich-rss",
        description: "Halden Arbeiderblad"
    },
    {
        id: NewsSite.HALLINGDOLEN,
        name: "Hallingdølen",
        type: "RSS",
        url: "https://hallingdolen.no/rss",
        description: "Hallingdølen"
    },
    {
        id: NewsSite.HAMAR_ARBEIDERBLAD,
        name: "Hamar Arbeiderblad",
        type: "RSS",
        url: "https://h-a.no/service/rich-rss",
        description: "Hamar Arbeiderblad"
    },
    {
        id: NewsSite.HARDANGER_FOLKEBLAD,
        name: "Hardanger Folkeblad",
        type: "RSS",
        url: "https://hardanger-folkeblad.no/service/rich-rss",
        description: "Hardanger Folkeblad"
    },
    {
        id: NewsSite.HARDANGER_TIDENDE,
        name: "Hardanger Tidende",
        type: "RSS",
        url: "https://ht.no/rss",
        description: "Hardanger Tidende"
    },
    {
        id: NewsSite.HAUGESUND_AVIS,
        name: "Haugesunds Avis",
        type: "RSS",
        url: "https://h-avis.no/service/rich-rss",
        description: "Haugesunds Avis"
    },
    {
        id: NewsSite.HELGELENDINGEN,
        name: "Helgelendingen",
        type: "RSS",
        url: "https://helg.no/service/rich-rss",
        description: "Helgelendingen"
    },
    {
        id: NewsSite.HELGELANDS_BLAD,
        name: "Helgelands Blad",
        type: "RSS",
        url: "https://hblad.no/rss",
        description: "Helgelands Blad"
    },
    {
        id: NewsSite.HEROYFJERDINGEN,
        name: "Herøyfjerdingen",
        type: "RSS",
        url: "https://heroyfjerdingen.no/rss",
        description: "Herøyfjerdingen"
    },
    {
        id: NewsSite.HITRA_FROYA,
        name: "Hitra-Frøya",
        type: "RSS",
        url: "https://hitra-froya.no/rss",
        description: "Hitra-Frøya"
    },
    {
        id: NewsSite.AVISA_HORDALAND,
        name: "Avisa Hordaland",
        type: "RSS",
        url: "https://avisa-hordaland.no/service/rich-rss",
        description: "Avisa Hordaland"
    },
    {
        id: NewsSite.HORDALAND_FOLKEBLAD,
        name: "Hordaland Folkeblad",
        type: "RSS",
        url: "https://hf.no/rss",
        description: "Hordaland Folkeblad"
    },
    {
        id: NewsSite.IFINNMARK,
        name: "iFinnmark",
        type: "RSS",
        url: "https://ifinnmark.no/service/rich-rss",
        description: "iFinnmark"
    },
    {
        id: NewsSite.INDRE24,
        name: "Indre24",
        type: "RSS",
        url: "https://indre24.no/rss",
        description: "Indre24"
    },
    {
        id: NewsSite.ITROMSo,
        name: "iTromsø",
        type: "RSS",
        url: "https://itromso.no/rss",
        description: "iTromsø"
    },
    {
        id: NewsSite.ITAVISEN,
        name: "ITavisen",
        type: "RSS",
        url: "https://itavisen.no/feed",
        description: "ITavisen"
    },
    {
        id: NewsSite.INDRE_AKERSHUS_BLAD,
        name: "Indre Akershus Blad",
        type: "RSS",
        url: "https://indre.no/service/rich-rss",
        description: "Indre Akershus Blad"
    },
    {
        id: NewsSite.JARLSBERG_AVIS,
        name: "Jarlsberg Avis",
        type: "RSS",
        url: "https://jarlsbergavis.no/service/rich-rss",
        description: "Jarlsberg Avis"
    },
    {
        id: NewsSite.JARBLADET,
        name: "Jærbladet",
        type: "RSS",
        url: "https://jbl.no/service/rich-rss",
        description: "Jærbladet"
    },
    {
        id: NewsSite.KARMOYNYTT, name: "Karmøynytt",
        type: "RSS",
        url: "https://karmoynytt.no/service/rich-rss",
        description: "Karmøynytt"
    },
    {
        id: NewsSite.KLAR_TALE,
        name: "Klar Tale",
        type: "RSS",
        url: "https://klartale.no/rss",
        description: "Klar Tale"
    },
    {
        id: NewsSite.KRAGERO_BLAD_VESTMAR,
        name: "Kragerø Blad Vestmar",
        type: "RSS",
        url: "https://kv.no/service/rich-rss",
        description: "Kragerø Blad Vestmar"
    },
    {
        id: NewsSite.KVINNHERINGEN,
        name: "Kvinnheringen",
        type: "RSS",
        url: "https://kvinnheringen.no/service/rich-rss",
        description: "Kvinnheringen"
    },
    {
        id: NewsSite.LAGENDALSPOSTEN,
        name: "Lågendalsposten",
        type: "RSS",
        url: "https://laagendalsposten.no/service/rich-rss",
        description: "Lågendalsposten"
    },
    {
        id: NewsSite.LIERPOSTEN,
        name: "Lierposten",
        type: "RSS",
        url: "https://lierposten.no/service/rich-rss",
        description: "Lierposten"
    },
    {
        id: NewsSite.LILLESANDS_POSTEN,
        name: "Lillesands-Posten",
        type: "RSS",
        url: "https://lp.no/rss",
        description: "Lillesands-Posten"
    },
    {
        id: NewsSite.LINDSNES_AVIS,
        name: "Lindsnes Avis",
        type: "RSS",
        url: "https://l-a.no/rss",
        description: "Lindsnes Avis"
    },
    {
        id: NewsSite.LISTER24,
        name: "Lister24",
        type: "RSS",
        url: "https://lister24.no/rss",
        description: "Lister24"
    },
    {
        id: NewsSite.LOFOT_TIDENDE,
        name: "Lofot-Tidende",
        type: "RSS",
        url: "https://lofot-tidende.no/service/rich-rss",
        description: "Lofot-Tidende"
    },
    {
        id: NewsSite.LOFOTPOSTEN,
        name: "Lofotposten",
        type: "RSS",
        url: "https://lofotposten.no/service/rich-rss",
        description: "Lofotposten"
    },
    {
        id: NewsSite.LYNGDALS_AVIS,
        name: "Lyngdals Avis",
        type: "RSS",
        url: "https://lyngdalsavis.no/service/rich-rss",
        description: "Lyngdals Avis"
    },
    {
        id: NewsSite.MORGENBLADET,
        name: "Morgenbladet",
        type: "RSS",
        url: "https://morgenbladet.no/rss",
        description: "Morgenbladet"
    },
    {
        id: NewsSite.MOSS_AVIS,
        name: "Moss Avis",
        type: "RSS",
        url: "https://moss-avis.no/service/rich-rss",
        description: "Moss Avis"
    },
    {
        id: NewsSite.NAMDALSAVISA,
        name: "Namdalsavisa",
        type: "RSS",
        url: "https://namdalsavisa.no/service/rich-rss",
        description: "Namdalsavisa"
    },
    {
        id: NewsSite.NATIONEN,
        name: "Nationen",
        type: "RSS",
        url: "https://nationen.no/service/rich-rss",
        description: "Nationen"
    },
    {
        id: NewsSite.NORDHORDLAND,
        name: "Nordhordland",
        type: "RSS",
        url: "https://nordhordland.no/service/rich-rss",
        description: "Nordhordland"
    },
    {
        id: NewsSite.NORDLYS,
        name: "Nordlys",
        type: "RSS",
        url: "https://nordlys.no/service/rich-rss",
        description: "Nordlys"
    },
    {
        id: NewsSite.NORDSTRANDS_BLAD,
        name: "Nordstrands Blad",
        type: "RSS",
        url: "https://noblad.no/service/rich-rss",
        description: "Nordstrands Blad"
    },
    {
        id: NewsSite.NUMEDALSNETT,
        name: "Numedalsnett",
        type: "RSS",
        url: "https://numedal.net/rss",
        description: "Numedalsnett"
    },
    {
        id: NewsSite.NY_TID,
        name: "Ny Tid",
        type: "RSS",
        url: "https://nytid.no/rss",
        description: "Ny Tid"
    },
    {
        id: NewsSite.NYTTIUKA,
        name: "NyttiUka",
        type: "RSS",
        url: "https://nyttiuka.no/rss",
        description: "NyttiUka"
    },
    {
        id: NewsSite.OPDALINGEN,
        name: "Opdalingen",
        type: "RSS",
        url: "https://opdalingen.no/rss",
        description: "Opdalingen"
    },
    {
        id: NewsSite.OFOTINGEN,
        name: "Ofotingen",
        type: "RSS",
        url: "https://ofotingen.no/rss",
        description: "Ofotingen"
    },
    {
        id: NewsSite.OPP,
        name: "OPP (Oppdal)",
        type: "RSS",
        url: "https://opp.no/rss",
        description: "OPP"
    },
    {
        id: NewsSite.OPPLAND_ARBEIDERBLAD,
        name: "Oppland Arbeiderblad",
        type: "RSS",
        url: "https://oa.no/service/rich-rss",
        description: "Oppland Arbeiderblad"
    },
    {
        id: NewsSite.PORSGRUNNS_DAGBLAD,
        name: "Porsgrunns Dagblad",
        type: "RSS",
        url: "https://pd.no/service/rich-rss",
        description: "Porsgrunns Dagblad"
    },
    {
        id: NewsSite.RAKKESTAD_AVIS,
        name: "Rakkestad Avis",
        type: "RSS",
        url: "https://r-a.no/service/rich-rss",
        description: "Rakkestad Avis"
    },
    {
        id: NewsSite.RANABLAD,
        name: "Ranablad",
        type: "RSS",
        url: "https://ranablad.no/service/rich-rss",
        description: "Ranablad"
    },
    {
        id: NewsSite.RAUMNES,
        name: "Raumnes",
        type: "RSS",
        url: "https://raumnes.no/service/rich-rss",
        description: "Raumnes"
    },
    {
        id: NewsSite.RESETT,
        name: "Resett",
        type: "RSS",
        url: "https://resett.no/rss",
        description: "Resett"
    },
    {
        id: NewsSite.RINGBLAD,
        name: "Ringblad",
        type: "RSS",
        url: "https://ringblad.no/service/rich-rss",
        description: "Ringblad"
    },
    {
        id: NewsSite.ROGALANDS_AVIS,
        name: "Rogalands Avis",
        type: "RSS",
        url: "https://rogalandsavis.no/service/rich-rss",
        description: "Rogalands Avis"
    },
    {
        id: NewsSite.ROMERIKES_BLAD,
        name: "Romerikes Blad",
        type: "RSS",
        url: "https://rb.no/service/rich-rss",
        description: "Romerikes Blad"
    },
    {
        id: NewsSite.ROMSDALS_BUDSTIKKE,
        name: "Romsdals Budstikke",
        type: "RSS",
        url: "https://rbnett.no/rss",
        description: "Romsdals Budstikke"
    },
    {
        id: NewsSite.RYFYLKE,
        name: "Ryfylke",
        type: "RSS",
        url: "https://ryfylke.net/feed",
        description: "Ryfylke"
    },
    {
        id: NewsSite.ROYKEN_OG_HURUMS_AVIS,
        name: "Røyken og Hurums Avis",
        type: "RSS",
        url: "https://rha.no/service/rich-rss",
        description: "Røyken og Hurums Avis"
    },
    {
        id: NewsSite.SANDE_AVIS,
        name: "Sande Avis",
        type: "RSS",
        url: "https://sandeavis.no/service/rich-rss",
        description: "Sande Avis"
    },
    {
        id: NewsSite.SANDEFJORDS_BLAD,
        name: "Sandefjords Blad",
        type: "RSS",
        url: "https://sb.no/service/rich-rss",
        description: "Sandefjords Blad"
    },
    {
        id: NewsSite.SANDNESPOSTEN,
        name: "Sandnesposten",
        type: "RSS",
        url: "https://sandnesposten.no/service/rich-rss",
        description: "Sandnesposten"
    },
    {
        id: NewsSite.SARPSBORG_ARBEIDERBLAD,
        name: "Sarpsborg Arbeiderblad",
        type: "RSS",
        url: "https://sa.no/service/rich-rss",
        description: "Sarpsborg Arbeiderblad"
    },
    {
        id: NewsSite.SMAALENENES_AVIS,
        name: "Smaalenenes Avis",
        type: "RSS",
        url: "https://smaalenene.no/service/rich-rss",
        description: "Smaalenenes Avis"
    },
    {
        id: NewsSite.SOGNAVIS,
        name: "Sognavis",
        type: "RSS",
        url: "https://sognavis.no/service/rich-rss",
        description: "Sognavis"
    },
    {
        id: NewsSite.SOLABLADET,
        name: "Solabladet",
        type: "RSS",
        url: "https://solabladet.no/service/rich-rss",
        description: "Solabladet"
    },
    {
        id: NewsSite.STANGEAVISA,
        name: "Stangeavisa",
        type: "RSS",
        url: "https://stangeavisa.no/service/rich-rss",
        description: "Stangeavisa"
    },
    {
        id: NewsSite.STAVANGER_AFTENBLAD,
        name: "Stavanger Aftenblad",
        type: "RSS",
        url: "https://aftenbladet.no/rss",
        description: "Stavanger Aftenblad"
    },
    {
        id: NewsSite.STEINKJER_AVISA,
        name: "Steinkjer-Avisa",
        type: "RSS",
        url: "https://steinkjer-avisa.no/service/rich-rss",
        description: "Steinkjer-Avisa"
    },
    {
        id: NewsSite.STRANDBUEN,
        name: "Strandbuen",
        type: "RSS",
        url: "https://strandbuen.no/service/rich-rss",
        description: "Strandbuen"
    },
    {
        id: NewsSite.STRILEN,
        name: "Strilen",
        type: "RSS",
        url: "https://strilen.no/rss",
        description: "Strilen"
    },
    {
        id: NewsSite.SUNNHORDLAND,
        name: "Sunnhordland",
        type: "RSS",
        url: "https://sunnhordland.no/rss",
        description: "Sunnhordland"
    },
    {
        id: NewsSite.SUNNMORINGEN,
        name: "Sunnmøringen",
        type: "RSS",
        url: "https://sunnmoringen.no/rss",
        description: "Sunnmøringen"
    },
    {
        id: NewsSite.SUNNMORPOSTEN,
        name: "Sunnmørsposten",
        type: "RSS",
        url: "https://smp.no/rss",
        description: "Sunnmørsposten"
    },
    {
        id: NewsSite.SVELVIKSPOSTEN,
        name: "Svelviksposten",
        type: "RSS",
        url: "https://svelviksposten.no/service/rich-rss",
        description: "Svelviksposten"
    },
    {
        id: NewsSite.SYDVESTEN,
        name: "Sydvesten",
        type: "RSS",
        url: "https://sydvesten.no/service/rich-rss",
        description: "Sydvesten"
    },
    {
        id: NewsSite.SYNSTE_MORE,
        name: "Synste Møre",
        type: "RSS",
        url: "https://synste.no/feed",
        description: "Synste Møre"
    },
    {
        id: NewsSite.AVISA_SOR_TRONDELAG,
        name: "Avisa Sør-Trøndelag",
        type: "RSS",
        url: "https://avisa-st.no/rss",
        description: "Avisa Sør-Trøndelag"
    },
    {
        id: NewsSite.LOKALAVISA_TRYSIL,
        name: "Lokalavisa Trysil-Engerdal",
        type: "RSS",
        url: "https://lokal-avisa.no/service/rich-rss",
        description: "Lokalavisa Trysil-Engerdal"
    },
    {
        id: NewsSite.TELEMARKSAVISA,
        name: "Telemarksavisa",
        type: "RSS",
        url: "https://ta.no/service/rich-rss",
        description: "Telemarksavisa"
    },
    {
        id: NewsSite.TELEN,
        name: "Telen",
        type: "RSS",
        url: "https://telen.no/service/rich-rss",
        description: "Telen"
    },
    {
        id: NewsSite.TIDENS_KRAV,
        name: "Tidens Krav",
        type: "RSS",
        url: "https://tk.no/service/rich-rss",
        description: "Tidens Krav"
    },
    {
        id: NewsSite.TROMSO_BY,
        name: "Tromsø By",
        type: "RSS",
        url: "https://tromsoby.no/service/rich-rss",
        description: "Tromsø By"
    },
    {
        id: NewsSite.TRONDER_AVISA,
        name: "Trønder-Avisa",
        type: "RSS",
        url: "https://t-a.no/service/rich-rss",
        description: "Trønder-Avisa"
    },
    {
        id: NewsSite.TRONDERBLADET,
        name: "Trønderbladet",
        type: "RSS",
        url: "https://tronderbladet.no/rss",
        description: "Trønderbladet"
    },
    {
        id: NewsSite.TVEDESTRANDPOSTEN,
        name: "Tvedestrandsposten",
        type: "RSS",
        url: "https://tvedestrandsposten.no/service/rich-rss",
        description: "Tvedestrandsposten"
    },
    {
        id: NewsSite.TYSVAR_BYGDEBLAD,
        name: "Tysvær Bygdeblad",
        type: "RSS",
        url: "https://tysver-bygdeblad.no/feed",
        description: "Tysvær Bygdeblad"
    },
    {
        id: NewsSite.TONSBERGS_BLAD,
        name: "Tønsbergs Blad",
        type: "RSS",
        url: "https://tb.no/service/rich-rss",
        description: "Tønsbergs Blad"
    },
    {
        id: NewsSite.VAKSDALPOSTEN,
        name: "Vaksdalposten",
        type: "RSS",
        url: "https://vp.no/service/rich-rss",
        description: "Vaksdalposten"
    },
    {
        id: NewsSite.AVISA_VALDRES,
        name: "Avisa Valdres",
        type: "RSS",
        url: "https://avisa-valdres.no/service/rich-rss",
        description: "Avisa Valdres"
    },
    {
        id: NewsSite.VARDEN,
        name: "Varden",
        type: "RSS",
        url: "https://varden.no/rss",
        description: "Varden"
    },
    {
        id: NewsSite.VARINGEN,
        name: "Varingen",
        type: "RSS",
        url: "https://varingen.no/service/rich-rss",
        description: "Varingen"
    },
    {
        id: NewsSite.VENNESLA_TIDENDE,
        name: "Vennesla Tidende",
        type: "RSS",
        url: "https://venneslatidende.no/rss",
        description: "Vennesla Tidende"
    },
    {
        id: NewsSite.VESTLANDSNYTT,
        name: "Vestlandsnytt",
        type: "RSS",
        url: "https://vestlandsnytt.no/rss",
        description: "Vestlandsnytt"
    },
    {
        id: NewsSite.VEST_TELEMARK_BLAD,
        name: "Vest-Telemark Blad",
        type: "RSS",
        url: "https://vtb.no/rss",
        description: "Vest-Telemark Blad"
    },
    {
        id: NewsSite.VESTBY_AVIS,
        name: "Vestby Avis",
        type: "RSS",
        url: "https://vestbyavis.no/service/rich-rss",
        description: "Vestby Avis"
    },
    {
        id: NewsSite.VOL,
        name: "VOL",
        type: "RSS",
        url: "https://vol.no/rss",
        description: "VOL"
    },
    {
        id: NewsSite.VESTKANTAVISEN,
        name: "Vestkantavisen",
        type: "RSS",
        url: "https://vestkantavisen.no/rss",
        description: "Vestkantavisen"
    },
    {
        id: NewsSite.VESTNYTT,
        name: "Vestnytt",
        type: "RSS",
        url: "https://vestnytt.no/rss",
        description: "Vestnytt"
    },
    {
        id: NewsSite.VIKEBLADET,
        name: "Vikebladet",
        type: "RSS",
        url: "https://vikebladet.no/rss",
        description: "Vikebladet"
    },
    {
        id: NewsSite.VIGGA,
        name: "Vigga",
        type: "RSS",
        url: "https://vigga.no/rss",
        description: "Vigga"
    },
    {
        id: NewsSite.VART_LAND,
        name: "Vårt Land",
        type: "RSS",
        url: "https://vl.no/rss",
        description: "Vårt Land"
    },
    {
        id: NewsSite.OSTLANDETS_BLAD,
        name: "Østlandets Blad",
        type: "RSS",
        url: "https://oblad.no/service/rich-rss",
        description: "Østlandets Blad"
    },
    {
        id: NewsSite.OSTLANDS_POSTEN,
        name: "Østlands-Posten",
        type: "RSS",
        url: "https://op.no/service/rich-rss",
        description: "Østlands-Posten"
    },
    {
        id: NewsSite.OSTLENDINGEN,
        name: "Østlendingen",
        type: "RSS",
        url: "https://ostlendingen.no/service/rich-rss",
        description: "Østlendingen"
    },
    {
        id: NewsSite.OYBLIKK,
        name: "ØyBlikk",
        type: "RSS",
        url: "https://oyblikk.no/rss",
        description: "ØyBlikk"
    },
    {
        id: NewsSite.OYENE,
        name: "Øyene",
        type: "RSS",
        url: "https://oyene.no/service/rich-rss",
        description: "Øyene"
    },
    {
        id: NewsSite.AS_AVIS,
        name: "Ås Avis",
        type: "RSS",
        url: "https://aasavis.no/service/rich-rss",
        description: "Ås Avis"
    }
] as const satisfies readonly Outlet[];

// Extract the ID type
export type AvailableOutlets = (typeof outlets)[number]['id'] | "all";


export default outlets;