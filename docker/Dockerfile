FROM maven:3.9.5-amazoncorretto-17-al2023
WORKDIR /app
COPY . .
EXPOSE 8081
RUN mvn install --no-transfer-progress -DskipTests=true
ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]