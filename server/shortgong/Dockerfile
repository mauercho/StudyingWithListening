FROM gradle:7.4.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

FROM bellsoft/liberica-openjdk-alpine:17
LABEL authors="SSAFY"

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]