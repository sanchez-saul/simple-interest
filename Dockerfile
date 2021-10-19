FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER APLAZO
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} simple-credit-1.0.jar
ENTRYPOINT ["java", "-jar", "simple-credit-1.0.jar"]