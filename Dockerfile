FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/hotel-booking-service-0.0.1-SNAPSHOT.jar hotel-booking-service.jar
ENTRYPOINT ["java","-jar","/hotel-booking-service.jar"]