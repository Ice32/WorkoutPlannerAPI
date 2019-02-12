FROM openjdk:12-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
#ENV DB_HOST
COPY target/WorkoutPlanner-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--server.port=80"]
