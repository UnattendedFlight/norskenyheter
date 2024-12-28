"use client";

import {useIsFetching, useQueryClient} from "@tanstack/react-query";
import {useCallback, useState} from "react";
import {Button} from "@/components/ui/button";
import {LoaderCircleIcon, RefreshCw} from "lucide-react";
import {toast} from "sonner";

export default function QueryKeyRefresh({queryKey, exact = false}: Readonly<{ queryKey: string[]; exact?: boolean }>) {
    const [isRefreshing, setIsRefreshing] = useState(false);
    const queryClient = useQueryClient();
    const isLoading = useIsFetching({
        queryKey,
        exact,
    });

    const refresh = useCallback(() => {
        setIsRefreshing(true);
        queryClient.refetchQueries({queryKey, exact}).then(() => {
            toast.success("Artikler oppdatert");
        }).catch(() => {
            toast.error("Kunne ikke oppdatere artikler");
        }).finally(() => {
            setIsRefreshing(false);
        });
    }, [queryClient, queryKey, exact]);

    return (
        <Button
            variant="outline"
            onClick={refresh}
            disabled={isRefreshing || isLoading > 0}
        >
            {(isRefreshing || isLoading > 0) ? <LoaderCircleIcon className="animate-spin h-4 w-4"/> :
                <RefreshCw className="h-4 w-4"/>}
        </Button>
    );
}