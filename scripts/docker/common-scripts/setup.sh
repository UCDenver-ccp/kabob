#!/bin/bash
#

mkdir -p /kabob_data/logs
mkdir -p /kabob_data/backups

chmod 755 /kabob.git/scripts/download/process-other-downloads.sh
/kabob.git/scripts/download/process-other-downloads.sh

ONT_DIR=/kabob_data/ontology
if [ -d "$ONT_DIR" ]; then
    # clean the ontology directory if it exists
    rm -rf "$ONT_DIR"
fi
mkdir -p "$ONT_DIR"
chmod 755 /kabob.git/scripts/download/process-ontologies.sh

cd /kabob.git && /kabob.git/scripts/download/process-ontologies.sh "$ONT_DIR" mvn

