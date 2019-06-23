#!/bin/bash
#
# Download data source files and generate ICE RDF. This script takes two input parameters.
#
# 1) The first input parameter specifies the taxon(s) to use. If "-m"
# then a pre-set list of seven model organism taxons plus human will
# be used. Alternatively, the user can supply "-t [TAXON_LIST]" where
# [TAXON_LIST] is a comma-delimited list of NCBI taxonomy identifiers.
#
# 2) The second input parameter specifies the data source(s) to
# download and process. Multiple data sources should be listed in a
# comma-delimited string with no spaces.
#
# 3) path to Maven binary
#
# 4) run counter so that the log file name is unique
#
#
#

cd datasource.git
chmod 755 datasource-rdfizer/scripts/*.sh
./datasource-rdfizer/scripts/download-datasources-and-generate-triples.sh \
    -d /kabob_data/raw \
    -r /kabob_data/rdf \
    $1 \
    -i $2 \
    -x $3 \
    | tee /kabob_data/logs/rdf-gen.log.$4
