"use client";

import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover";
import {useState} from "react";

export default function HoverOver({
                                      trigger,
                                      children,
                                  }: Readonly<{ trigger: React.ReactNode; children: React.ReactNode }>) {
    const [open, setOpen] = useState(false);
    return (
        <Popover open={open} onOpenChange={(isOpen) => setOpen(isOpen)}>
            <PopoverTrigger>
                <div
                    onMouseEnter={() => setOpen(true)}
                    onMouseLeave={() => setOpen(false)}
                    className={"flex items-center"}
                >
                    {trigger}
                </div>
            </PopoverTrigger>
            <PopoverContent>
                {children}
            </PopoverContent>
        </Popover>
    );

}