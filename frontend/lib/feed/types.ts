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
    ADRESSEAVISEN = 'AA',
    AVISEN_AGDER = 'AVISEN_AGDER',
    AGDERPOSTEN = 'AGDERPOSTEN',
    GRORUDDALEN = 'GRORUDDALEN',
    AMTA = 'AMTA',
    ALTAPOSTEN = 'ALTAPOSTEN',
    RETTEN = 'RETTEN',
    ARENDALS_TIDENDE = 'ARENDALS_TIDENDE',
    ASKOYVARINGEN = 'ASKOYVARINGEN',
    AURA_AVIS = 'AURA_AVIS',
    AUST_AGDER_BLAD = 'AUST_AGDER_BLAD',
    AN = 'AN',
    BA = 'BA',
    BIRKENESAVISA = 'BIRKENESAVISA',
    BLADET = 'BLADET',
    BLV = 'BLV',
    BODOPOSTEN = 'BODOPOSTEN',
    BUDSTIKKA = 'BUDSTIKKA',
    BYGDANYTT = 'BYGDANYTT',
    BYGDEBLADET = 'BYGDEBLADET',
    BYGDEPOSTEN = 'BYGDEPOSTEN',
    BOMLO_NYTT = 'BOMLO_NYTT',
    DAG_OG_TID = 'DAG_OG_TID',
    DAGSAVISEN = 'DAGSAVISEN',
    DALANE_TIDENDE = 'DALANE_TIDENDE',
    DIGI = 'DIGI',
    DT = 'DT',
    DRANGEDALSPOSTEN = 'DRANGEDALSPOSTEN',
    DRIVA = 'DRIVA',
    DOLEN = 'DOLEN',
    E24 = 'E24',
    EUB = 'EUB',
    EIKERNYTT = 'EIKERNYTT',
    ENEBAKK_AVIS = 'ENEBAKK_AVIS',
    ENERGI24 = 'ENERGI24',
    F7 = 'F7',
    FANAPOSTEN = 'FANAPOSTEN',
    FIRDA = 'FIRDA',
    FIRDA_TIDEND = 'FIRDA_TIDEND',
    FIRDAPOSTEN = 'FIRDAPOSTEN',
    FITJARPOSTEN = 'FITJARPOSTEN',
    FJORDABLADET = 'FJORDABLADET',
    FJORDENES_TIDENDE = 'FJORDENES_TIDENDE',
    FJORDINGEN = 'FJORDINGEN',
    FOLKEBLADET = 'FOLKEBLADET',
    FOSNA_FOLKET = 'FOSNA_FOLKET',
    FJUKEN = 'FJUKEN',
    FRAMTID_I_NORD = 'FRAMTID_I_NORD',
    FREDRIKSSTAD_BLAD = 'FB',
    FREMOVER = 'FREMOVER',
    FROSTINGEN = 'FROSTINGEN',
    FAEDRELANDSVENNEN = 'FVN',
    GJENGANGEREN = 'GJENGANGEREN',
    GJESDALBUEN = 'GJESDALBUEN',
    GLOBALT_PERSPEKTIV = 'GLOBALT_PERSPEKTIV',
    GLOMDALEN = 'GLOMDALEN',
    GRANNAR = 'GRANNAR',
    GRENDA = 'GRENDA',
    GRIMSTAD_ADRESSETIDENDE = 'GAT',
    GUDBRANDSDOLEN_DAGNINGEN = 'GD',
    HADELAND = 'HADELAND',
    HALDEN_ARBEIDERBLAD = 'HALDEN_ARBEIDERBLAD',
    HALLINGDOLEN = 'HALLINGDOLEN',
    HAMAR_ARBEIDERBLAD = 'HA',
    HARDANGER_FOLKEBLAD = 'HF',
    HARDANGER_TIDENDE = 'HT',
    HAUGESUND_AVIS = 'HAUGESUND_AVIS',
    HELGELENDINGEN = 'HELGELENDINGEN',
    HELGELANDS_BLAD = 'HELGELANDS_BLAD',
    HEROYFJERDINGEN = 'HEROYFJERDINGEN',
    HITRA_FROYA = 'HITRA_FROYA',
    AVISA_HORDALAND = 'AVISA_HORDALAND',
    HORDALAND_FOLKEBLAD = 'HORDALAND_FOLKEBLAD',
    IFINNMARK = 'IFINNMARK',
    INDRE24 = 'INDRE24',
    ITROMSo = 'ITROMSo',
    ITAVISEN = 'ITAVISEN',
    INDRE_AKERSHUS_BLAD = 'INDRE_AKERSHUS_BLAD',
    JARLSBERG_AVIS = 'JARLSBERG_AVIS',
    JARBLADET = 'JBL',
    KARMOYNYTT = 'KARMOYNYTT',
    KLAR_TALE = 'KLAR_TALE',
    KRAGERO_BLAD_VESTMAR = 'KV',
    KVINNHERINGEN = 'KVINNHERINGEN',
    LAGENDALSPOSTEN = 'LAAGENDALSPOSTEN',
    LIERPOSTEN = 'LIERPOSTEN',
    LILLESANDS_POSTEN = 'LP',
    LINDSNES_AVIS = 'LINDSNES_AVIS',
    LISTER24 = 'LISTER24',
    LOFOT_TIDENDE = 'LOFOT_TIDENDE',
    LOFOTPOSTEN = 'LOFOTPOSTEN',
    LYNGDALS_AVIS = 'LYNGDALS_AVIS',
    MORGENBLADET = 'MORGENBLADET',
    MOSS_AVIS = 'MOSS_AVIS',
    NAMDALSAVISA = 'NAMDALSAVISA',
    NATIONEN = 'NATIONEN',
    NORDHORDLAND = 'NORDHORDLAND',
    NORDLYS = 'NORDLYS',
    NORDSTRANDS_BLAD = 'NOBLAD',
    NUMEDALSNETT = 'NUMEDAL',
    NY_TID = 'NY_TID',
    NYTTIUKA = 'NYTTIUKA',
    OPDALINGEN = 'OPDALINGEN',
    OFOTINGEN = 'OFOTINGEN',
    OPP = 'OPP',
    OPPLAND_ARBEIDERBLAD = 'OPPLAND_ARBEIDERBLAD',
    PORSGRUNNS_DAGBLAD = 'PD',
    RAKKESTAD_AVIS = 'RA',
    RANABLAD = 'RB',
    RAUMNES = 'RN',
    RESETT = 'RS',
    RINGBLAD = 'RBLAD',
    ROGALANDS_AVIS = 'ROGALANDS_AVIS',
    ROMERIKES_BLAD = 'ROMERIKES_BLAD',
    ROMSDALS_BUDSTIKKE = 'RBNETT',
    RYFYLKE = 'RYFYLKE',
    ROYKEN_OG_HURUMS_AVIS = 'RHA',
    SANDE_AVIS = 'SANDE_AVIS',
    SANDEFJORDS_BLAD = 'SB',
    SANDNESPOSTEN = 'SP',
    SARPSBORG_ARBEIDERBLAD = 'SARPSBORG_ARBEIDERBLAD',
    SMAALENENES_AVIS = 'SMA',
    SOGNAVIS = 'SOGNAVIS',
    SOLABLADET = 'SOLABLADET',
    STANGEAVISA = 'STA',
    STAVANGER_AFTENBLAD = 'STAVANGER_AFTENBLAD',
    STEINKJER_AVISA = 'STKA',
    STRANDBUEN = 'STBU',
    STRILEN = 'STR',
    SUNNHORDLAND = 'SH',
    SUNNMORINGEN = 'SM',
    SUNNMORPOSTEN = 'SMP',
    SVELVIKSPOSTEN = 'SVP',
    SYDVESTEN = 'SV',
    SYNSTE_MORE = 'SNS',
    AVISA_SOR_TRONDELAG = 'AVISA_SOR_TRONDELAG',
    LOKALAVISA_TRYSIL = 'LAT',
    TELEMARKSAVISA = 'TA',
    TELEN = 'TELEN',
    TIDENS_KRAV = 'TK',
    TROMSO_BY = 'TRBY',
    TRONDER_AVISA = 'TRONDER_AVISA',
    TRONDERBLADET = 'TRONDERBLADET',
    TVEDESTRANDPOSTEN = 'TVEDESTRANDPOSTEN',
    TYSVAR_BYGDEBLAD = 'TYSVAR_BYGDEBLAD',
    TONSBERGS_BLAD = 'TB',
    VAKSDALPOSTEN = 'VKP',
    AVISA_VALDRES = 'AVISA_VALDRES',
    VARDEN = 'VAR',
    VARINGEN = 'VARINGEN',
    VENNESLA_TIDENDE = 'VENNESLA_TIDENDE',
    VESTLANDSNYTT = 'VESTLANDSNYTT',
    VEST_TELEMARK_BLAD = 'VTB',
    VESTBY_AVIS = 'VESTBY_AVIS',
    VOL = 'VOL',
    VESTKANTAVISEN = 'VESTKANTAVISEN',
    VESTNYTT = 'VESTNYTT',
    VIKEBLADET = 'VIKEBLADET',
    VIGGA = 'VIGGA',
    VART_LAND = 'VL',
    OSTLANDETS_BLAD = 'OB',
    OSTLANDS_POSTEN = 'OP',
    OSTLENDINGEN = 'OL',
    OYBLIKK = 'OYBLIKK',
    OYENE = 'OYENE',
    AS_AVIS = 'AAS_AVIS',
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
