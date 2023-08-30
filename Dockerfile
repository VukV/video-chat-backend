FROM openjdk:17-jdk-alpine AS builder

ARG VERSION=0.0.1-SNAPSHOT

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

ADD . $HOME
RUN --mount=type=cache,target=/root/.gradle ./gradlew -Drevision=$VERSION build

FROM openjdk:17-jdk-alpine

ARG VERSION=0.0.1-SNAPSHOT
ARG SERVICE_NAME="video-chat-backend"

COPY --from=builder /usr/app/build/libs/$SERVICE_NAME-$VERSION.jar /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
