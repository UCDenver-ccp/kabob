#!/bin/bash

function print_usage {
    echo "Usage:"
    echo "$(basename $0) [OPTIONS]"
    echo "  [-l <backup label>]: A label to be appended to the backup file name that will uniquely identify this particular backup."
}

while getopts "l:h" OPTION; do
    case $OPTION in
        # the backup label
        l) BACKUP_LABEL=$OPTARG
           ;;
        # HELP!
        h) print_usage; exit 0
           ;;
    esac
done

if [[ -z ${BACKUP_LABEL} ]]; then
	echo "backup label: ${BACKUP_LABEL}"
    print_usage
    exit 1
fi

backup_file="/kabob_data/backups/bg_backup_${BACKUP_LABEL}.jnl.gz"

# ensure the backup file exists
if [[ -f "$backup_file" ]]; then
    echo "Restoring from Blazegraph backup: ${backup_file}..."
    # to restore a backup, stop the Blazegraph process, then copy and uncompress
    # the backed-up Blazegraph journal file to /blazegraph-data/bigdata.jnl
    # before restarting the Blazegraph process
    supervisorctl stop bg:blazegraph
    # this assumes that blazegraph is the only running java process
    kill $(ps aux | grep java | tr -s " " | cut -f 2 -d " " | head -n 1)
    gunzip -c ${backup_file} > /blazegraph-data/bigdata.jnl
    supervisorctl start bg:blazegraph
    echo "Restore complete."
else
    echo "Unable to restore from backup: ${backup_file}. File does not exist!"
    echo "Available backup files:"
    ls -l /kabob_data/backups
    echo
    echo "Please select a label for an existing backup file and retry the restore operation."
fi