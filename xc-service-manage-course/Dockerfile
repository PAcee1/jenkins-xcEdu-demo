FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} course.jar
EXPOSE 33333
ENTRYPOINT ["java","-jar","/course.jar"]