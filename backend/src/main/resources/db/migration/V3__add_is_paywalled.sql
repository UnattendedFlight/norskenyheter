-- V3__add_is_paywalled.sql
ALTER TABLE articles
    ADD COLUMN is_paywalled BOOLEAN DEFAULT FALSE;