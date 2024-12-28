import {Button} from "@/components/ui/button";
import {Command, CommandEmpty, CommandGroup, CommandInput, CommandItem, CommandList} from "@/components/ui/command";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover";
import {cn} from "@/lib/utils";
import {Check, ChevronsUpDown} from "lucide-react";
import {useState} from "react";

type SearchableSelectOption = {
    value: string;
    label: string;
    keywords?: string[];
};

type SearchableSelectProps = {
    options: SearchableSelectOption[];
    value: string | null;
    onValueChange: (value: string) => void;
    placeholder: string;
    noResultsText: string;
    disabledOptions?: Record<string, string>;
};
export default function SearchableSelect({
                                             options,
                                             value,
                                             onValueChange,
                                             placeholder = "Velg ...",
                                             noResultsText = "Ingen resultater",
                                             disabledOptions = {},
                                         }: SearchableSelectProps) {
    const [open, setOpen] = useState(false);
    return (
        <Popover open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
                <Button
                    variant="outline"
                    role="combobox"
                    aria-expanded={open}
                    className="justify-between"
                >
                    {value
                        ? options.find((opt) => opt.value === value)?.label
                        : placeholder}
                    <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50"/>
                </Button>
            </PopoverTrigger>
            <PopoverContent className="p-0">
                <Command>
                    <CommandInput placeholder={placeholder}/>
                    <CommandList>
                        <CommandEmpty>{noResultsText}</CommandEmpty>
                        <CommandGroup>
                            {options.map((opt) => (
                                <CommandItem
                                    key={opt.value}
                                    value={opt.value}
                                    onSelect={(currentValue) => {
                                        onValueChange(currentValue === value ? "" : currentValue);
                                        setOpen(false);
                                    }}
                                    disabled={!!disabledOptions?.[opt.value]}
                                    keywords={opt.keywords ?? [opt.label]}
                                >
                                    <Check
                                        className={cn(
                                            "mr-2 h-4 w-4",
                                            value === opt.value ? "opacity-100" : "opacity-0",
                                        )}
                                    />
                                    {opt.label}
                                    {disabledOptions[opt.value] && (
                                        <span className="text-xs text-gray-500">
											{disabledOptions[opt.value]}
										</span>
                                    )}
                                </CommandItem>
                            ))}
                        </CommandGroup>
                    </CommandList>
                </Command>
            </PopoverContent>
        </Popover>
    );
}
