file="/var/tmp/.ready"

if [ -f "$file" ] ; then
    rm "$file"
fi

dockerize \
    -wait tcp://${ES_HOST}:${ES_PORT} \
    -wait tcp://${PG_HOST}:${PG_PORT} \
    -wait-retry-interval 900s \
    -timeout 10000s  && \
    java -jar /srv/api/app.jar \
    --spring.datasource.url=jdbc:postgresql://${PG_HOST}:${PG_PORT}/nominatim \
    --pg2es.config.amenity_mapping_csv=categories.csv \
    --pg2es.config.es_host=${ES_HOST} \
    --pg2es.config.es_port=${ES_PORT}
    # -wait file:///srv/api/imports/import-finished \



touch /var/tmp/.ready

while :; do :; done & kill -STOP $! && wait $!

# file://nominatim:/var/lib/postgresql/12/main/import-finished