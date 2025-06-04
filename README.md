# StormSafe API

API RESTful desenvolvida em Java com Spring Boot para monitoramento e prevenção de riscos de enchentes com sensores IoT, alertas e rotas seguras.

## Funcionalidades

- Cadastro e autenticação de usuários com roles (população, admin, defesa civil)
- Cadastro e monitoramento de sensores vinculados a rios e regiões
- Registro de leituras dos sensores em tempo real
- Geração e consulta de alertas automáticos por região
- Cadastro e consulta de rotas seguras para evacuação
- Segurança JWT e documentação Swagger

## Como rodar

1. Configure o banco de dados PostgreSQL e ajuste as propriedades em `application.properties`
2. Compile e rode o projeto:

```
mvn clean install
mvn spring-boot:run
```

3. Acesse o Swagger UI para testar os endpoints:

```
http://localhost:8080/swagger-ui.html
```

## Dependências

- Java 17
- Maven 3.8+
- PostgreSQL (ou outro banco relacional compatível)
