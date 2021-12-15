FROM eclipse-temurin@sha256:32b5c3ab0a254b9fcfdd6fce9ac968c7d1f8b2785cfbd39643eaf1c08774d7aa
RUN apk add --no-cache wget dpkg-dev  zip unzip

USER root
RUN adduser -h /srv/api -D api
ENV USERNAME api
ENV USERHOME /srv/api
RUN chmod a+x ${USERHOME}

WORKDIR ${USERHOME}

ENV DOCKERIZE_VERSION v0.6.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz


COPY start.sh .
RUN chmod +x start.sh


CMD ./start.sh