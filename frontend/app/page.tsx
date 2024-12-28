import NewsFeed from "@/app/components/NewsFeed";
import {Suspense} from "react";
import {SiteHeader} from "@/app/components/SiteHeader";

export default function Home() {
    return (
        <>
            <Suspense fallback={<p>Loading...</p>}>
                <SiteHeader/>
            </Suspense>
            <main className="container mx-auto px-4 py-8">
                <Suspense fallback={<p>Loading...</p>}>
                    <NewsFeed/>
                </Suspense>
            </main>
        </>
    );
}
