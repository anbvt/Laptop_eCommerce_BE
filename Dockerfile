FROM openjdk:17
LABEL maintainer="LAPTOP_ECOMMERCE"
ENTRYPOINT [ "java","-jar","/app.jar" ]