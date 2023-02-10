FROM openjdk:19
ARG JAR_FILE=build/libs/*.jar
RUN mkdir /opt/usuf-bot
COPY ${JAR_FILE} /opt/usuf-bot/usuf-bot.jar
ENTRYPOINT ["java","-jar","/opt/usuf-bot/usuf-bot.jar"]
