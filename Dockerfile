# base image
FROM openjdk:11 as base

WORKDIR /app

#stage - 1 - Download all the project dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw && ./mvnw -B dependency:go-offline

## copy the source code and build the project

COPY src src

RUN ./mvnw clean package -DskipTests && mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


FROM openjdk:11.0.13-jre-slim-buster

# create an variable
ARG DEPENDENCY=/app/target/dependency

COPY --from=base ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=base ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=base ${DEPENDENCY}/META-INF /app/META-INF

EXPOSE 8222

ENTRYPOINT ["java" , "-cp" , "app:app/lib/*", "com.classpath.ordersapi.OrdersApiApplication"]


