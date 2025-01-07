#!/bin/bash
set -e

echo 'Creating newsagg database and user...'
PGPASSWORD=H3uphef2 psql -h postgres -U postgres -d postgres -c 'CREATE DATABASE newsagg;'
PGPASSWORD=H3uphef2 psql -h postgres -U postgres -d postgres -c "CREATE USER newsagg WITH PASSWORD 'newsagg';"
PGPASSWORD=H3uphef2 psql -h postgres -U postgres -d postgres -c 'GRANT ALL PRIVILEGES ON DATABASE newsagg TO newsagg;'
PGPASSWORD=H3uphef2 psql -h postgres -U postgres -d newsagg -c 'GRANT ALL ON SCHEMA public TO newsagg;'

echo 'Creating pgvector extension...'
PGPASSWORD=H3uphef2 psql -h postgres -U postgres -d newsagg -c 'CREATE EXTENSION IF NOT EXISTS vector;'