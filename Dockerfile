FROM eclipse-temurin:17-jdk-alpine

ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""

COPY BPS/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]