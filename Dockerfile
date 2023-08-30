FROM gradle:8.3.0-jdk17-alpine AS builder
WORKDIR /app
COPY ./ .

RUN gradle --no-daemon build --info


FROM amazoncorretto:17-alpine3.15
VOLUME /tmp

ARG VERSION=0.0.1-SNAPSHOT
ARG SERVICE_NAME="video-chat-backend"

EXPOSE 8080
COPY --from=builder /app/build/libs/$SERVICE_NAME-$VERSION.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
