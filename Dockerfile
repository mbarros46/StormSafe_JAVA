# Usar imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para o diretório de trabalho no contêiner
COPY target/StormSafe-1.0.0.jar /app/StormSafe.jar

# Expor a porta para acesso externo
EXPOSE 8080

# Rodar a aplicação
ENTRYPOINT ["java", "-jar", "StormSafe.jar"]
s