FROM eclipse-temurin:17-jdk-alpine
RUN cd BPS
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]