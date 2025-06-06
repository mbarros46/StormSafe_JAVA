# StormSafe API - Monitoramento e Prevenção de Enchentes

A **StormSafe** é uma aplicação backend construída com **Spring Boot**, projetada para monitorar e prevenir enchentes com base em dados de sensores e eventos climáticos. A API expõe endpoints para interagir com os dados relacionados aos alertas, sensores, regiões, entre outros.

## Requisitos

Antes de rodar a aplicação, é necessário ter os seguintes pré-requisitos instalados:

- **JDK 11 ou superior**: A aplicação foi desenvolvida utilizando o Java 11.
- **Maven**: Para gerenciamento de dependências e construção do projeto.
- **Banco de dados**: O projeto utiliza **PostgreSQL** (ou outro banco relacional configurado) para persistência de dados.
- **Docker** (opcional): Para execução em containers.

## Configuração do Ambiente

### 1. Clonar o Repositório

Clone o repositório para o seu ambiente local:

```bash
git clone https://github.com/seu-usuario/StormSafe.git
cd StormSafe
2. Configurar Banco de Dados
O projeto usa PostgreSQL como banco de dados relacional. Você pode configurar um banco local ou usar um banco de dados em nuvem. Se for usar o banco local, crie um banco de dados chamado stormsafe.

Exemplo de configuração application.properties:
properties
Copiar
spring.datasource.url=jdbc:postgresql://localhost:5432/stormsafe
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
jwt.secret=StormSafeJWTSecretKeyExample1234567890
3. Configuração do Swagger
A documentação da API pode ser acessada através do Swagger, uma vez que o projeto esteja rodando. A documentação estará disponível no endpoint:

bash
Copiar
http://localhost:8080/swagger-ui/index.html
4. Configuração de Autenticação
O projeto utiliza autenticação baseada em JWT. Para acessar os endpoints que exigem autenticação, você precisa obter um token JWT no endpoint de login.

Exemplo de login:
Endpoint: POST /api/auth/login

Corpo da Requisição:

json
Copiar
{
  "username": "usuario_exemplo",
  "password": "senha_exemplo"
}
Resposta:

json
Copiar
{
  "token": "seu_token_jwt_aqui"
}
Utilize o token recebido no cabeçalho de autorização para fazer requisições autenticadas. Exemplo:

bash
Copiar
curl -X GET "http://localhost:8080/api/alerta" -H "Authorization: Bearer seu_token_jwt_aqui"
Rodando o Projeto Localmente
Construir o Projeto com Maven:

Se o Maven estiver instalado corretamente, execute o seguinte comando para compilar o projeto:


mvn clean install

Rodar o Projeto com Maven:

Para iniciar a aplicação localmente, execute o seguinte comando:


mvn spring-boot:run
A aplicação estará disponível em http://localhost:8080/swagger-ui/index.html#/