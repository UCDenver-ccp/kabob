#!/bin/bash
#

SCRIPT_DIR=/kabob.git/scripts/docker/blazegraph-specific

echo "arg1: ${1}"

/kabob.git/scripts/docker/common-scripts/build-from-scratch.sh ${SCRIPT_DIR} ${1} blazegraph
