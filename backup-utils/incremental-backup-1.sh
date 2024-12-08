#!/bin/bash
psql -c "ALTER system SET summarize_wal = ON;"
psql -c "SELECT pg_reload_conf();"

psql -f /tmp/utils/insert-data1.sql
pg_basebackup -i /tmp/backups/full-1/backup_manifest -D "/tmp/backups/incremental-1"
#cp /var/lib/postgresql/data/PG_VERSION /tmp/backups/incremental-1/PG_VERSION