import {atom} from "jotai";
import {Article} from "@/lib/api/types";

export const selectedArticleAtom = atom<Article | null>(null);