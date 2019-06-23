#!/bin/bash
#
# This script provides a way to re-download and process an individual ontology file

KB_KEY=$1
KABOB_CONTAINER_VERSION=$2
DATASOURCE_KEY=$3
ONT_URL=$4

DID=""
echo "Starting kabob container to process ${ONT_URL}..."
DID=${DID}" "`docker run -d --name "ccp_ont_dload_${DATASOURCE_KEY}" --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} ./download-single-ontology.sh ${ONT_URL}`
docker wait $DID
docker rm $DID
