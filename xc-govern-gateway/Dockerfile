FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} gateway.jar
EXPOSE 22222
ENTRYPOINT ["java","-jar","/gateway.jar"]