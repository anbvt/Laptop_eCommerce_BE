FROM openjdk:17
LABEL maintainer="cinema"
ADD target/Movie-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-jar","/app.jar" ]