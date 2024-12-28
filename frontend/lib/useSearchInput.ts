import {useDebouncedValue, useThrottledValue} from "@mantine/hooks";
import {useMemo, useState} from "react";

type UseSearchInputBaseProps = {
    defaultValue?: string;
    type: "debounce" | "throttle" | "normal";
    delay?: number;
    options?: never;
};
type UseSearchInputDebounceProps = {
    defaultValue?: string;
    type: "debounce";
    delay?: number;
    options?: { leading?: boolean; };
};
type UseSearchInputThrottleProps = {
    defaultValue?: string;
    type: "throttle";
    delay?: number;
    options?: { leading: boolean; };
};
type UseSearchInputNormalProps = {
    defaultValue?: string;
    type: "normal";
    delay?: never;
    options?: never;
};
type UseSearchInputProps =
    UseSearchInputBaseProps
    | UseSearchInputDebounceProps
    | UseSearchInputThrottleProps
    | UseSearchInputNormalProps;
export default function useSearchInput({
                                           defaultValue = "",
                                           type = "normal",
                                           delay = 500,
                                           options = {},
                                       }: UseSearchInputProps) {
    const [value, setValue] = useState(defaultValue);
    const [debouncedValue, cancelDebouncedValue] = useDebouncedValue(value, delay, options as any);
    const throttledValue = useThrottledValue(value, delay);
    const useValue = useMemo(() => {
        switch (type) {
            case "debounce":
                return debouncedValue;
            case "throttle":
                return throttledValue;
            case "normal":
                return value;
        }
    }, [debouncedValue, throttledValue, value, type]);
    return {value, search: useValue, setValue};
}