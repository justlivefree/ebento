FROM maven:3.9.11-amazoncorretto-24-alpine AS build
WORKDIR app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:24-alpine3.22-jdk
WORKDIR app
RUN mkdir -p storage
COPY --from=build /app/target/app-build.jar app-build.jar
COPY . .
EXPOSE 8080
CMD ["java","-jar","app-build.jar"]