// Basic article type

export interface ArticleStats {
    totalArticles: number;
    articlesPerSource: Record<string, number>;
}

export interface Article {
    id: number;
    articleId: string;
    url: string;
    source: string;
    originalTitle: string;
    aiGeneratedTitle: string | null;
    summary: string | null;
    description: string;
    paywalled: boolean;
    imageUrl: string | null;
    publishedAt: string;
    processedAt: string | null;
    retryCount: number;
    status: ArticleStatus;
}

// Article status enum
export enum ArticleStatus {
    NEW = 'NEW',
    PROCESSING = 'PROCESSING',
    COMPLETED = 'COMPLETED',
    FAILED = 'FAILED'
}

// Pagination sort information
export interface Sort {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
}

// Pagination information
export interface Pageable {
    pageNumber: number;
    pageSize: number;
    sort: Sort;
    offset: number;
    paged: boolean;
    unpaged: boolean;
}

// Page response wrapper
export interface PageResponse<T> {
    content: T[];
    pageable: Pageable;
    last: boolean;
    totalElements: number;
    totalPages: number;
    first: boolean;
    size: number;
    number: number;
    sort: Sort;
    numberOfElements: number;
    empty: boolean;
}

// Type alias for article page response
export type ArticlePageResponse = PageResponse<Article>;