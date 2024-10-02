FROM openjdk:22
EXPOSE 25566
ADD build/libs/MinesServer-1.0-SNAPSHOT.jar server.jar
ENTRYPOINT ["java", "-jar","server.jar","25566", "HrnAoPkQycCa"]