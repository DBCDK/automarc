FROM docker.dbc.dk/dbc-payara-micro-logback:4

ENV USER gfish
USER $USER

COPY target/automarc.war wars

EXPOSE 8080

LABEL INFOMEDIA_FETCHER_URL="URL to the infomedia fetcher service"