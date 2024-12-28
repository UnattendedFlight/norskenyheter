-- V2__add_retry_count.sql
ALTER TABLE articles
    ADD COLUMN retry_count INTEGER DEFAULT 0;