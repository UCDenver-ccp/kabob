#!/bin/bash

function print_usage {
    echo "Usage:"
    echo "$(basename $0) [OPTIONS]"
    echo "  [-u <blazegraph URL>]: The URL to the blazegraph repository. If called within Docker, this should include the blazegraph-net-KEY "
    echo "  [-l <backup label>]: A label to be appended to the backup file name that will uniquely identify this particular backup."
}

while getopts "u:l:h" OPTION; do
    case $OPTION in
        # the blazegraph URL
        u) BLAZEGRAPH_URL=$OPTARG
           ;;
        # the backup label
        l) BACKUP_LABEL=$OPTARG
           ;;
        # HELP!
        h) print_usage; exit 0
           ;;
    esac
done

if [[ -z ${BLAZEGRAPH_URL} || -z ${BACKUP_LABEL} ]]; then
    echo "blazegraph url: ${BLAZEGRAPH_URL}"
	echo "backup label: ${BACKUP_LABEL}"
    print_usage
    exit 1
fi

backup_file="/kabob_data/backups/bg_backup_${BACKUP_LABEL}.jnl.gz"
echo "Backing up Blazegraph repository to ${backup_file}..."

curl \
          --data-urlencode "file=${backup_file}" \
          --data-urlencode "compress=true" \
          --data-urlencode "block=true" \
          ${BLAZEGRAPH_URL}/backup

echo "Backup complete."