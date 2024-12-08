#!/bin/bash

mkdir -p /tmp/backups; pg_basebackup -D "/tmp/backups/full-1"

#cp /var/lib/postgresql/data/PG_VERSION /tmp/backups/full-1/PG_VERSION


