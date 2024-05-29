FROM maven:3.8.4-openjdk-11 AS build

WORKDIR /usr/src/app

COPY ./pom.xml .
COPY ./src ./src

RUN mvn clean install

FROM openjdk:11-jre-slim

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/devpod-operator-0.1.0.jar ./devpod-operator.jar

CMD ["java", "-jar", "devpod-operator.jar"]