#!/bin/bash

#function print_usage {
#    echo "Usage:"
#    echo "$(basename $0) [OPTIONS]"
#    echo "  [-p <port>]: The port to use when starting blazegraph"
#}
#
#while getopts "p:h" OPTION; do
#    case ${OPTION} in
#        # StarDog port
#        p) STARDOG_PORT=$OPTARG
#           ;;
#        # HELP!
#        h) print_usage; exit 0
#           ;;
#    esac
#done
#
#if [[ -z ${STARDOG_PORT} ]]; then
#    print_usage
#    exit 1
#fi


# catch the termination signal and call a script to stop all monitored processes gracefully
#trap '/stardog-${STARDOG_VERSION}/bin/stardog-admin server stop ; exit' 15

# start blazegraph by starting jetty
chown developer:developer /blazegraph-data
#cd /var/lib/jetty
#java -Xmx40G -XX:MaxDirectMemorySize=40G -Dcom.bigdata.rdf.sail.webapp.ConfigParams.propertyFile=/RWStore.properties -Dlog4j.configuration=file:///log4j.properties -DSTOP.KEY=KEY -DSTOP.PORT=2222 -jar /usr/local/jetty/start.jar

java -server -Xmx60g -jar -Dbigdata.propertyFile=/home/developer/blazegraph/conf/RWStore.properties /home/developer/blazegraph/blazegraph.jar


## create a symbolic link to the deployed blazegraph lib/ directory to /blazegraph-lib
#CP_DIR=$(grep bigdata.war /var/log/supervisor/blazegraph_process.err.log | cut -f 2 -d "{" | cut -f 2 -d "," | cut -f 3- -d "/")
#ln -s ${CP_DIR} /blazegraph-lib

# && \
#    sleep 1 && \
#    (tail -f ${STARDOG_HOME}/stardog.log &) && \
#    while (pidof java > /dev/null); do sleep 1; done


