'use client';

import Link from "next/link"
import OutletSelector from "@/app/components/OutletSelector";
import QueryKeyRefresh from "@/app/components/QueryKeyRefresh";
import SearchInput from "@/app/components/SearchInput";

// const categories = ["Alle kategorier", "Nyheter", "Sport", "Kultur", "Økonomi", "Teknologi"]

export function SiteHeader() {
    // const [selectedCategory, setSelectedCategory] = useState("Alle kategorier");

    return (
        <header
            className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60 px-8">
            <div className="container flex h-16 items-center space-x-4 sm:justify-between sm:space-x-0">
                <div className="flex gap-6 md:gap-10">
                    <Link href="/" className="flex items-center space-x-2">
                        <span className="inline-block font-bold">Norske Nyheter</span>
                    </Link>
                </div>
                <div className="flex flex-1 items-center justify-end space-x-4">
                    <div className="w-full flex items-end space-x-4 max-w-xl">
                        <OutletSelector/>
                        {/*<DropdownMenu>*/}
                        {/*    <DropdownMenuTrigger asChild>*/}
                        {/*        <Button variant="outline" className="w-[160px] justify-between">*/}
                        {/*            {selectedCategory}*/}
                        {/*        </Button>*/}
                        {/*    </DropdownMenuTrigger>*/}
                        {/*    <DropdownMenuContent>*/}
                        {/*        {categories.map((category) => (*/}
                        {/*            <DropdownMenuItem*/}
                        {/*                key={category}*/}
                        {/*                onClick={() => setSelectedCategory(category)}*/}
                        {/*            >*/}
                        {/*                {category}*/}
                        {/*            </DropdownMenuItem>*/}
                        {/*        ))}*/}
                        {/*    </DropdownMenuContent>*/}
                        {/*</DropdownMenu>*/}
                        <QueryKeyRefresh queryKey={["news"]} exact={false}/>
                        <SearchInput/>
                        {/*<ViewToggle/>
                        <ThemeToggle/>*/}
                    </div>
                </div>
            </div>
        </header>
    )
}


