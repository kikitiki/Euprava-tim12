#base docker image
FROM openjdk:17

ADD target/auth-service-0.0.1-SNAPSHOT.jar auth.jar
ENTRYPOINT ["java","-jar", "auth.jar"]