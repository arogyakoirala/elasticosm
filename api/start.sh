dockerize \
    -wait tcp://${ES_HOST}:${ES_PORT} \
    -wait file:///srv/api/status/.ready \
    -wait-retry-interval 10s \
    -timeout 10000s  && \
    java -jar /srv/api/app.jar \
    --config.es_host=${ES_HOST} \
    --config.es_port=${ES_PORT} \
    --config.amenity_mapping_csv=categories.csv 

  

