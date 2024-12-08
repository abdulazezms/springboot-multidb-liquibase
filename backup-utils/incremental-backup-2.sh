#!/bin/bash

psql -f /tmp/utils/insert-data2.sql
pg_basebackup -i /tmp/backups/incremental-1/backup_manifest -D "/tmp/backups/incremental-2"
#cp /var/lib/postgresql/data/PG_VERSION /tmp/backups/incremental-2/PG_VERSION