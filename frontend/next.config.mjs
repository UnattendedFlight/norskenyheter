/** @type {import('next').NextConfig} */
const nextConfig = {
    images: {
        // Allow all images from all https domains
        remotePatterns: [
            {
                hostname: '*',
                pathname: '**/*',
                protocol: 'https',
            },
            {
                hostname: '*.*',
                pathname: '**/*',
                protocol: 'http',
            }
        ]
    },
};

export default nextConfig;
