FROM openjdk:19
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} usuf-bot.jar
ENTRYPOINT ["java", "-jar", "/usuf-bot.jar"]
