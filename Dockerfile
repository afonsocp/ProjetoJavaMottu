# Multi-stage build para otimizar o tamanho da imagem
FROM gradle:8-jdk21 AS build
WORKDIR /app

# Copiar arquivos de build
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

# Build da aplicação
RUN gradle bootJar --no-daemon

# Imagem final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR do build
COPY --from=build /app/build/libs/*.jar app.jar

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/ || exit 1

# Executar aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

