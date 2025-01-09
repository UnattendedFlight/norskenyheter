import aiohttp
import asyncio
import csv
from urllib.parse import urljoin
from typing import List, Dict
import xml.etree.ElementTree as ET
import time

RSS_PATHS = [
    '/rss',
    '/feed',
    '/service/rich-rss'
]

async def check_rss_endpoint(session: aiohttp.ClientSession, base_url: str, path: str) -> Dict:
    """Check if an RSS endpoint exists and returns valid XML."""
    url = urljoin(base_url, path)
    try:
        async with session.get(url, timeout=10) as response:
            if response.status == 200:
                content = await response.text()
                try:
                    # Try to parse as XML
                    ET.fromstring(content)
                    print(f"✅ Found valid RSS feed at {url}")
                    return {
                        'valid': True,
                        'url': url,
                        # store the first 25000 characters of the response or the whole response if it's shorter
                        'response': content[:25000] if len(content) > 25000 else content
                    }
                except ET.ParseError:
                    print(f"❌ Invalid XML at {url}")
                    return {'valid': False, 'url': url, 'response': 'Invalid XML'}
            print(f"❌ HTTP {response.status} at {url}")
            return {'valid': False, 'url': url, 'response': f'Status code: {response.status}'}
    except Exception as e:
        print(f"❌ Error checking {url}: {str(e)}")
        return {'valid': False, 'url': url, 'response': f'Error: {str(e)}'}

async def process_website(session: aiohttp.ClientSession, base_url: str, current: int, total: int) -> Dict:
    """Process a single website checking all RSS paths."""
    print(f"\n[{current}/{total}] Checking {base_url}")

    # Ensure URL has http:// or https://
    if not base_url.startswith(('http://', 'https://')):
        base_url = 'https://' + base_url

    results = await asyncio.gather(
        *[check_rss_endpoint(session, base_url, path) for path in RSS_PATHS]
    )

    # Find the first valid RSS endpoint
    valid_result = next((r for r in results if r['valid']), None)

    if valid_result:
        print(f"✨ Successfully found RSS feed for {base_url}")
    else:
        print(f"📪 No valid RSS feeds found for {base_url}")

    return {
        'name': base_url,  # Use the full base URL as name
        'url': valid_result['url'] if valid_result else '',  # Use the full endpoint URL if valid
        'hasValidRssEndpoint': bool(valid_result),
        'response': valid_result['response'] if valid_result else 'No valid RSS endpoint found'
    }

async def main():
    start_time = time.time()
    print("🚀 Starting RSS feed checker...")

    # Read URLs from the text file
    with open('urls.txt', 'r') as f:
        urls = [line.strip() for line in f if line.strip()]

    print(f"📋 Loaded {len(urls)} URLs to check")
    print(f"🔍 Checking RSS paths: {', '.join(RSS_PATHS)}\n")

    async with aiohttp.ClientSession() as session:
        tasks = [process_website(session, url, i+1, len(urls))
                for i, url in enumerate(urls)]
        results = await asyncio.gather(*tasks)

    # Write results to CSV
    fieldnames = ['name', 'url', 'hasValidRssEndpoint', 'response']
    output_file = 'rss_results.csv'
    with open(output_file, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(results)

    # Print summary
    elapsed_time = time.time() - start_time
    valid_feeds = sum(1 for r in results if r['hasValidRssEndpoint'])

    print("\n📊 Summary:")
    print(f"✓ Processed {len(results)} websites")
    print(f"✓ Found {valid_feeds} valid RSS feeds")
    print(f"✓ Took {elapsed_time:.2f} seconds")
    print(f"✓ Results saved to {output_file}")

if __name__ == "__main__":
    asyncio.run(main())