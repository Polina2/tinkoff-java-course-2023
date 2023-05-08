FROM openjdk:19
COPY project/bot/target/bot-1.0-SNAPSHOT.jar bot-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/bot-1.0-SNAPSHOT.jar"]