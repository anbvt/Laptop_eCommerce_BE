FROM openjdk:17
LABEL maintainer="LAPTOP_ECOMMERCE"
ADD target/LAPTOP_ECOMMERCE-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-jar","/app.jar" ]