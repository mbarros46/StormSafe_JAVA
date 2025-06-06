# Use uma imagem base do JDK 17
FROM eclipse-temurin:17-jdk

# Defina o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR para o diretório de trabalho no container
COPY target/stormsafe-1.0.0.jar app.jar

# Criação do usuário 'stormsafe'
RUN useradd -ms /bin/bash stormsafe

# Modificar as permissões para o usuário 'stormsafe'
RUN chown -R stormsafe:stormsafe /app

# Defina o usuário para executar a aplicação
USER stormsafe

# Exponha a porta 8080 (padrão para Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicação com o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
