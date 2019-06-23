#!/bin/bash

#
# Build KaBOB
#

function print_usage {
    echo "Usage:"
    echo "$(basename $0) [OPTIONS] [COMMAND]"
    echo "  [-k <kb-key>]: A unique key that will be used to name docker containers for this build"
    echo "  [-v <kabob docker image version>]: The version of the kabob docker image to use."
    echo "  [COMMAND]: the command to send the docker container"

}

while getopts "k:v:h" OPTION; do
    case ${OPTION} in
        # A unique key that will be used to name docker containers for this build
        k) KB_KEY=$OPTARG
           ;;

        # kabob docker image version
        v) KABOB_CONTAINER_VERSION=$OPTARG
           ;;
        # HELP!
        h) print_usage; exit 0
           ;;
    esac
done
shift $(expr $OPTIND - 1 )
SHELL_COMMAND="$@"

if [[ -z ${KB_KEY} || -z ${SHELL_COMMAND} || -z ${KABOB_CONTAINER_VERSION} ]]; then
    print_usage
    exit 1
fi

if ! [[ -e README.md ]]; then
    echo "Please run from the root of the project."
    exit 1
fi


docker run --rm --net blazegraph-net-${KB_KEY} --volumes-from kabob_data-${KB_KEY} --volumes-from blazegraph-load-requests-${KB_KEY} ucdenverccp/kabob:${KABOB_CONTAINER_VERSION} ${SHELL_COMMAND}
