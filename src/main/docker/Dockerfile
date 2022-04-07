FROM adoptopenjdk:11-jdk-hotspot
ARG JAR_FILE=*.jar
EXPOSE 8080
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]