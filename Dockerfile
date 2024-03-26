FROM eclipse-temurin:17-jdk-alpine

COPY app.jar /

ENTRYPOINT ["java","-jar","/app.jar"]