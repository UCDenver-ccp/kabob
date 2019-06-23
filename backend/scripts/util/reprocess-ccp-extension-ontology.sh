#!/bin/bash
#
# This script provides a way to re-download and process the ccp-extension ontology

KB_KEY=$1
KABOB_CONTAINER_VERSION=$2
DATASOURCE_KEY=$3

DID=""
echo "Starting kabob container to process ccp-extension ontology..."
DID=${DID}" "`docker run -d --name "ccp_ont_dload_${DATASOURCE_KEY}" --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} ./download-single-ontology.sh "https://raw.githubusercontent.com/UCDenver-ccp/ccp-extension-ontology/master/src/ontology/ccp-extensions.owl"`
docker wait $DID
docker rm $DID
