# Multi-stage build para otimizar o tamanho da imagem
FROM gradle:8-jdk21 AS build
WORKDIR /app

# Copiar arquivos de build (inclui wrapper)
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# Build da aplicação usando o wrapper do projeto
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# Imagem final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR do build
COPY --from=build /app/build/libs/mottu-fleet-0.0.1-SNAPSHOT.jar app.jar

# Expor porta dinâmica
EXPOSE 8080
ENV PORT=8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT}/ || exit 1

# Executar aplicação com a porta dinâmica
ENTRYPOINT ["sh", "-c", "echo 'Iniciando aplicação...' && ls -l && java -jar app.jar --server.port=${PORT}"]

