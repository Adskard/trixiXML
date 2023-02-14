FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8

COPY --from=build /home/app/target/*jar-with-dependencies.jar /usr/local/lib/app.jar

ENTRYPOINT [  "java",                                     \
              "-jar",                                     \
              "/usr/local/lib/app.jar"                        \
           ]