-- src/main/resources/db/migration/V1__initial_schema.sql

CREATE TABLE articles
(
    id                 BIGSERIAL PRIMARY KEY,
    article_id         VARCHAR(255)             NOT NULL,
    url                VARCHAR(2048)            NOT NULL UNIQUE,
    source             VARCHAR(255)             NOT NULL,
    original_title     TEXT                     NOT NULL,
    ai_generated_title TEXT,
    summary            TEXT,
    description        TEXT,
    published_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at       TIMESTAMP WITH TIME ZONE,
    status             VARCHAR(50)              NOT NULL,
    image_url          VARCHAR(512),
    -- Metadata
    created_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Indexes
    CONSTRAINT idx_articles_url_unique UNIQUE (url)
);

CREATE INDEX idx_articles_status ON articles (status);
CREATE INDEX idx_articles_source ON articles (source);
CREATE INDEX idx_articles_published_at ON articles (published_at);

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Trigger to automatically update updated_at
CREATE TRIGGER update_articles_updated_at
    BEFORE UPDATE
    ON articles
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();