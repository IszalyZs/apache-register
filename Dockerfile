FROM maven:3.8.1-jdk-11-slim
WORKDIR /app
COPY . ./
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests
CMD "java" "-jar" "/app/webapp/target/register-webapp-1.0.0-SNAPSHOT.jar"
