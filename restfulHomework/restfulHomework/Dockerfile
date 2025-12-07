FROM maven:3.8.5-openjdk-8 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk8-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/restfulHomework.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
# Start Tomcat
CMD ["catalina.sh", "run"]