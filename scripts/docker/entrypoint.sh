#!/usr/bin/env bash
echo "running entrypoint.sh..."

# modify paths in connected volumes to be writable by the kabob user
if [ -d /kabob-load-requests ]; then
    chown -R :9001 /kabob-load-requests
    chmod 775 /kabob-load-requests
    chmod g+s /kabob-load-requests
fi

if [ -d /kabob_data ]; then
    chown -R kabob:kabob /kabob_data
    chmod g+s /kabob_data
fi

# if there are other arguments, treat them as a command to execute
if [[ -n "$@" ]];
then
    set -- gosu kabob "$@"
    echo "executing as $(whoami): $@"
    exec "$@"
fi
