FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/project-allocation.jar project-allocation.jar
ENTRYPOINT ["java", "-jar", "project-allocation.jar"]