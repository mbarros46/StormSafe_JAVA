FROM eclipse-temurin:17-jdk
RUN useradd -ms /bin/bash stormsafe
WORKDIR /app
COPY target/stormsafe-0.0.1-SNAPSHOT.jar app.jar
RUN chown -R stormsafe:stormsafe /app
USER stormsafe
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
