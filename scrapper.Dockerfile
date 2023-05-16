FROM openjdk:19
COPY project/scrapper/target/scrapper-1.0-SNAPSHOT.jar scrapper-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/scrapper-1.0-SNAPSHOT.jar"]