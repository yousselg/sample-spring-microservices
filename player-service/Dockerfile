FROM openjdk:8-jdk-alpine
ADD target/player-service.jar player-service.jar
EXPOSE 2222
ENTRYPOINT ["java", "-jar", "/player-service.jar"]