#!/bin/bash

psql -f /tmp/utils/insert-data4.sql
pg_basebackup -i /tmp/backups/incremental-3/backup_manifest -D "/tmp/backups/incremental-4"
#cp /var/lib/postgresql/data/PG_VERSION /tmp/backups/incremental-4/PG_VERSION