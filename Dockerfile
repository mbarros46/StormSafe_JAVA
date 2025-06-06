# Usar imagem base do OpenJDK
FROM eclipse-temurin:17-jdk

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para o diretório de trabalho no contêiner
COPY target/stormsafe-1.0.0.jar app.jar

# Expor a porta para acesso externo
EXPOSE 8080

# Rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
