# Etapa 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar dependencias primero para cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Compilar proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar JAR
COPY --from=build /app/target/*.jar app.jar

# Puerto del gateway
EXPOSE 9000

# Ejecutar
CMD ["java", "-jar", "app.jar"]