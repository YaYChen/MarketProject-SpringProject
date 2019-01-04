# MarketProject-SpringProject
MarketProject-SpringProject

Docker file:
FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    APP_SLEEP=0 \
    JAVA_OPTS=""

# Add a user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh sbbp
WORKDIR /home/sbbp

USER sbbp

ADD ./build/libs/*.war app.war

CMD echo "The application will start in ${APP_SLEEP}s..." && \
    sleep ${APP_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.war" "$@"

EXPOSE 8080 5701/udp
