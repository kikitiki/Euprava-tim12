#base docker image
FROM openjdk:17
LABEL maintainer="javaguides.net"
ADD target/demo-0.0.1-SNAPSHOT.jar zdravstvo.jar
ENTRYPOINT ["java","-jar", "zdravstvo.jar"]

