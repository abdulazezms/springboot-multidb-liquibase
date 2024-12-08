#!/bin/bash

psql -f /tmp/utils/insert-data3.sql
pg_basebackup -i /tmp/backups/incremental-2/backup_manifest -D "/tmp/backups/incremental-3"
#cp /var/lib/postgresql/data/PG_VERSION /tmp/backups/incremental-3/PG_VERSION