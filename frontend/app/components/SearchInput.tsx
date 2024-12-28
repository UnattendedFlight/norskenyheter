import {Search} from "lucide-react";
import {Input} from "@/components/ui/input";
import useSearchInput from "@/lib/useSearchInput";
import {useEffect} from "react";
import {useSetAtom} from "jotai/react";
import {headerSearchAtom} from "@/lib/atoms/search";

export default function SearchInput() {
    const search = useSearchInput({type: "debounce", delay: 500});
    const setSearchAtom = useSetAtom(headerSearchAtom);

    useEffect(() => {
        setSearchAtom(search.search);
    }, [search.search, setSearchAtom]);
    return (
        <div className="relative w-full max-w-sm">
            <Search className="absolute left-2 top-2.5 h-4 w-4 text-muted-foreground"/>
            <Input
                placeholder="Søk etter nyheter..."
                className="pl-8 bg-background"
                value={search.value}
                onChange={(e) => search.setValue(e.target.value)}
            />
        </div>
    );
}