FROM maven:3.9.6-eclipse-temurin-21-alpine

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY . .

CMD ["sh", "-c", "mvn clean test -DsuiteXmlFile=$SUITE_FILE"]