'use client'

import SearchableSelect from "@/app/components/SearchableSelect";
import outlets from "@/lib/outlets";
import {useAtom, useAtomValue} from "jotai/react";
import {outletStatsAtom, selectedOutletAtom} from "@/lib/atoms/outlet";
import {useMemo} from "react";
import {NewsSite} from "@/lib/feed/types";

export default function OutletSelector() {
    const [selectedOutlet, setSelectedOutlet] = useAtom(selectedOutletAtom);
    const stats = useAtomValue(outletStatsAtom);

    const options = useMemo(() => {
        return [
            {value: "all", label: "Alle"},
            ...outlets.map((outlet) => {
                const stat = stats?.articlesPerSource[outlet.name];
                console.log(stat);
                return {
                    value: outlet.id,
                    label: `${outlet.name} (${stat || 0})`
                }
            })
        ]
    }, [outlets, stats?.articlesPerSource, stats?.totalArticles]);
    return (
        <SearchableSelect
            options={options}
            value={selectedOutlet}
            onValueChange={(value) => setSelectedOutlet(value as NewsSite | "all")}
            placeholder={"Velg nyhetskilde"}
            noResultsText={"Ingen resultater"}
        />
    )
}

