"use client";

import {atom} from "jotai";
import {NewsSite} from "@/lib/feed/types";
import {ArticleStats} from "@/lib/api/types";

export const selectedOutletAtom = atom<NewsSite | "all">("all");
export const outletStatsAtom = atom<ArticleStats>({totalArticles: 0, articlesPerSource: {}});