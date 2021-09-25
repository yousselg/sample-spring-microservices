FROM openjdk:8-jdk-alpine
ADD target/game-service.jar game-service.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar", "/game-service.jar"]
