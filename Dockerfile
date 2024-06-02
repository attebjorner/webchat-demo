FROM maven:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install

FROM openjdk:17
COPY --from=build /usr/src/app/target/webchat-1.0.0.jar /usr/app/webchat-1.0.0.jar
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java","-jar","webchat-1.0.0.jar"]
