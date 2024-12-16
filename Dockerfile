FROM eclipse-temurin:11-jre

WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar
EXPOSE 1010
CMD ["java", "-XX:+UseG1GC", "-jar", "demo-0.0.1-SNAPSHOT.jar"]