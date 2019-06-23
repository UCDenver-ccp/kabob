#!/bin/bash

#
# Download datasources and generate RDF. This script takes two input arguments.
#
# The first input parameter is a key that will be used to distinguish
# this build from other KB builds in the same Docker environment, e.g
# production vs. development.
#
# Note that this script is capable of using a variable number (1-5) of
# docker containers to download and process the data sources in parallel. The
# second input parameter indicates the number of docker containers to
# use (and should be no more than the number of available cores on
# your machine).
#
#

function print_usage {
    echo "Usage:"
    echo "$(basename $0) [OPTIONS]"
    echo "  [-k <kb-key>]: A unique key that will be used to name docker containers for this build"
    echo "  [-c <container-count>]: The number of processes to spin up (as individual containers) when generating RDF. This count should be between 1 and 5."
    echo "  [-d <drugbank XML file path>]: OPTIONAL -- The local path to the DrugBank 'full database.xml' file. This file requires user registration to download, and therefore must be supplied by the user. This parameter is optional. If not provided, then the DrugBank resource simply will not be included in this build of KaBOB."
    echo "  [-p <pharmgkb relationships file path>]: OPTIONAL -- The local path to the PharmGKB relationships file (relationships.tsv). This file requires a PharmGKB license, and therefore must be supplied by the user. This parameter is optional. If not provided, then the PharmGKB relationships simply will not be included in this build of KaBOB."
    echo "  [-t <curated TF file path>]: OPTIONAL -- The local path to a file containing curated TF data."
    echo "  [-v <kabob docker image version>]: The version of the kabob docker image to use."
}

while getopts "k:c:d:p:t:v:h" OPTION; do
    case ${OPTION} in
        # A unique key that will be used to name docker containers for this build
        k) KB_KEY=$OPTARG
           ;;
        # The number of processes to spin up (as individual containers) when generating RDF.
        # This count should be between 1 and 5.
        c) CONTAINER_COUNT=$OPTARG
           ;;
        # OPTIONAL -- The local path to the DrugBank 'full database.xml' file. This file requires user registration to
        # download, and therefore must be supplied by the user. This parameter is optional. If not provided, then the
        # DrugBank resource simply will not be included in this build of KaBOB.
        d) DRUGBANK_FILE=$OPTARG
           ;;
        # OPTIONAL -- The local path to the PharmGKB relationships file. This file requires a license from PharmGKB,
        # and therefore must be supplied by the user. This parameter is optional. If not provided, then the
        # PharmGKB relationships resource simply will not be included in this build of KaBOB.
        p) PHARMGKB_RELATIONS_FILE=$OPTARG
           ;;
        # OPTIONAL -- The local path to the curated TF data file. This is a custom file that is not available publicly
        # at this time.
        t) CURATED_TF_FILE=$OPTARG
           ;;
        # kabob docker image version
        v) KABOB_CONTAINER_VERSION=$OPTARG
           ;;
        # HELP!
        h) print_usage; exit 0
           ;;
    esac
done

if [[ -z ${KB_KEY} || -z ${CONTAINER_COUNT} || -z ${KABOB_CONTAINER_VERSION} ]]; then
    print_usage
    exit 1
fi

if ! [[ -e README.md ]]; then
    echo "Please run from the root of the project."
    exit 1
fi

# Create a Docker volume where the downloaded data files and generated RDF will be stored:
docker create -v /kabob_data --name kabob_data-$KB_KEY ubuntu:latest

# if provided, copy the drugbank XML file into the /kabob_data container and create a metadata file (.ready)
if [[ ${DRUGBANK_FILE} ]]; then
    echo "Copying DrugBank file ($DRUGBANK_FILE) into Docker volume with key: $KB_KEY"
    docker run --rm --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} sh -c 'mkdir -p /kabob_data/raw/drugbank'
    docker cp "${DRUGBANK_FILE}" kabob_data-${KB_KEY}:'/kabob_data/raw/drugbank/full database.xml'
    docker run --rm --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} sh -c '/kabob.git/scripts/download/create-metadata-file.sh "/kabob_data/raw/drugbank/full database.xml"'
fi

# if provided, copy the pharmGKB relationships file into the /kabob_data container and create a metadata file (.ready)
if [[ ${PHARMGKB_RELATIONS_FILE} ]]; then
    echo "Copying PharmGKB file ($PHARMGKB_RELATIONS_FILE) into Docker volume with key: $KB_KEY"
    docker run --rm --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} sh -c 'mkdir -p /kabob_data/raw/pharmgkb'
    docker cp "${PHARMGKB_RELATIONS_FILE}" kabob_data-${KB_KEY}:'/kabob_data/raw/pharmgkb/relationships.tsv'
    docker run --rm --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} sh -c '/kabob.git/scripts/download/create-metadata-file.sh "/kabob_data/raw/pharmgkb/relationships.tsv"'
fi

# if provided, copy the curated TF file into the /kabob_data container and create a metadata file (.ready)
if [[ ${CURATED_TF_FILE} ]]; then
    echo "Copying Curated TF file ($CURATED_TF_FILE) into Docker volume with key: $KB_KEY"
    docker run --rm --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} sh -c 'mkdir -p /kabob_data/raw/ccp'
    docker cp "${CURATED_TF_FILE}" kabob_data-${KB_KEY}:'/kabob_data/raw/ccp/curated_tfs.tsv'
    docker run --rm --volumes-from kabob_data-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} sh -c '/kabob.git/scripts/download/create-metadata-file.sh "/kabob_data/raw/ccp/curated_tfs.tsv"'
fi

#  Initial setup (downloads ontologies used by KaBOB):
docker run --rm --volumes-from kabob_data-$KB_KEY ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} ./setup.sh

# Create data source RDF (downloads and processes publicly available databases).
chmod 755 backend/scripts/human-ice-rdf-gen.sh
backend/scripts/human-ice-rdf-gen.sh ${KB_KEY} ${KABOB_CONTAINER_VERSION} ${CONTAINER_COUNT}
