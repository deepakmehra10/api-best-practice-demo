FROM openjdk:8-jdk-alpine

# Version to be fetched from POM.XML
ARG JAR_FILE=/target/api-best-practice-demo-1.0.0-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]