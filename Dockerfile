# Importing JDK and copying required files
FROM maven:3.9.11-openjdk-18 AS build
COPY . .
run mvn clean package -DskipTests

FROM openjdk:18-jdk-slim
copy --from=build /target/my-app-name-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]