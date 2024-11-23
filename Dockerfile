#STAGE1
FROM gradle:jdk21 as builder

WORKDIR /app

COPY ./build.gradle .
COPY ./settings.gradle .

COPY src ./src

RUN gradle build --no-daemon

#STAGE2

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar discofrafia-1.jar

EXPOSE 443

CMD [ "java", "-jar", "discofrafia-1.jar" ]