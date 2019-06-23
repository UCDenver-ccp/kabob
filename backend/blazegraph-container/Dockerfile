FROM openkbs/blazegraph:latest

USER root
RUN apt-get update && apt-get install -y \
    supervisor \
    inotify-tools

# the following supplants the existing RWStore.properties file
COPY scripts/RWStore.properties /home/developer/blazegraph/conf
COPY scripts/log4j.properties /home/developer/blazegraph/conf
COPY scripts/supervisord-config.sh /
COPY scripts/supervisord.conf /etc/
COPY scripts/init-blazegraph.sh scripts/check-for-load.sh scripts/restore-from-backup.sh /usr/bin/
COPY scripts/run-loader.sh /
COPY scripts/pom-blazegraph-loader.xml /

RUN chmod 600 /etc/supervisord.conf && \
    chmod 755 /usr/bin/*.sh && \
    chmod 755 /supervisord-config.sh && \
    #usermod -a -G 9001 developer && \
    mkdir -p /var/log/supervisor && \
    mkdir /blazegraph-data && \
    chown developer:developer /blazegraph-data && \
    mkdir /kabob-load-requests && \
    chown -R :9001 /kabob-load-requests && \
    chmod g+s /kabob-load-requests && \
    ln -s /usr/jdk1.8.0_191/bin/java /usr/bin/java

CMD ["/usr/bin/supervisord","-c","/etc/supervisord.conf"]