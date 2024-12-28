import {NextRequest, NextResponse} from 'next/server'
import outlets from "@/lib/outlets";
import axios from "axios";

export async function GET(req: NextRequest, {params}: { params: Promise<{ outlet: string; }> }) {
    const {outlet} = await params;
    const selectedOutlet = outlets.find((o) => o.id === outlet)

    if (!selectedOutlet) {
        return NextResponse.json({error: 'Outlet not found'}, {status: 404})
    }

    try {
        const response = await axios.get(selectedOutlet.url, {
            headers: {
                'User-Agent': req.headers.get('User-Agent') || ''
            }
        });

        // Return XML response
        return new NextResponse(response.data, {
            headers: {
                'Content-Type': 'application/xml',
                // Optional: Add cache control headers if needed
                'Cache-Control': 'public, s-maxage=1200, stale-while-revalidate=600'
            }
        });
    } catch (error) {
        console.error('Error fetching RSS feed:', error)
        return NextResponse.json({error: 'Failed to fetch RSS feed'}, {status: 500})
    }
}