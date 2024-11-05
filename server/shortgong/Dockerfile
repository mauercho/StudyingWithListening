# Gradle 버전을 8.x로 변경
FROM gradle:8.2-jdk17 AS build
WORKDIR /app

# 프로젝트 전체 복사
COPY . .

# Gradlew 파일에 실행 권한 추가
RUN chmod +x ./gradlew

# Gradle 빌드 실행
RUN ./gradlew clean build -x test

FROM bellsoft/liberica-openjdk-alpine:17
LABEL authors="SSAFY"

ARG JAR_FILE=build/libs/*.jar

# 빌드된 JAR 파일 복사
COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
