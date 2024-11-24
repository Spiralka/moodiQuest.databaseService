FROM openjdk:22
WORKDIR /app
COPY build/libs/databaseService-0.0.1-SNAPSHOT.jar /app/databaseService.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8081
CMD ["java", "-jar", "databaseService.jar"]