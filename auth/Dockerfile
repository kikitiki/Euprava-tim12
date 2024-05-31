# Base docker image
FROM openjdk:17

WORKDIR /usr/src/app

COPY target/auth-service-0.0.1-SNAPSHOT.jar auth.jar

ENTRYPOINT ["java", "-jar", "auth.jar"]

EXPOSE 8080


