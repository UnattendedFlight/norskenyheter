-- V4__add_article_content.sql
ALTER TABLE articles
    ADD COLUMN content TEXT;

-- Add embedding column with 768 dimensions (compatible with common embedding models)
ALTER TABLE articles
    ADD COLUMN embedding vector(768);

-- Create an HNSW index for efficient similarity search
CREATE INDEX article_embedding_idx ON articles
    USING hnsw (embedding vector_ip_ops)
    WITH (m = 16, ef_construction = 64);