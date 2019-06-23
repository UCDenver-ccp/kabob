#!/bin/bash
#
# This script provides a way to re-download and process all ontologies

KB_KEY=$1
KABOB_CONTAINER_VERSION=$2

DID=""
echo "Starting kabob container to process all ontologies..."
DID=${DID}" "`docker run -d --name "ontology_processing" --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} ./download-ontologies.sh`
docker wait $DID
docker rm $DID
