FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} eureka.jar
EXPOSE 11111
ENTRYPOINT ["java","-jar","/eureka.jar"]