FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/question-*.jar app.jar
EXPOSE 0
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]