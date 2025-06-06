# 🌧️ StormSafe API - Monitoramento e Prevenção de Enchentes

A **StormSafe** é uma aplicação backend construída com **Spring Boot**, projetada para monitorar e prevenir enchentes com base em dados de sensores e eventos climáticos. A API expõe endpoints para interagir com os dados relacionados aos alertas, sensores, regiões, entre outros.

---

## ✅ Requisitos

Antes de rodar a aplicação, é necessário ter os seguintes pré-requisitos instalados:

- ☕ **JDK 11 ou superior**: A aplicação foi desenvolvida utilizando o Java 11.  
- 📦 **Maven**: Para gerenciamento de dependências e construção do projeto.  
- 🛢️ **Banco de Dados**: Utiliza **PostgreSQL** (ou outro banco relacional configurado) para persistência de dados.  
- 🐳 **Docker** (opcional): Para execução em containers.

---

## ⚙️ Configuração do Ambiente

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/StormSafe.git
cd StormSafe
```

---

### 2. Configurar Banco de Dados

O projeto usa PostgreSQL como banco de dados relacional. Você pode configurar um banco local ou usar um banco em nuvem.  
Se for usar o banco local, crie um banco de dados chamado `stormsafe`.

**Exemplo de configuração `application.properties`:**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/stormsafe
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
jwt.secret=StormSafeJWTSecretKeyExample1234567890
```

---

### 3. Configuração do Swagger

A documentação da API pode ser acessada através do Swagger, uma vez que o projeto esteja rodando:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### 4. Configuração de Autenticação

A aplicação utiliza autenticação baseada em **JWT**.  
Para acessar os endpoints protegidos, obtenha um token JWT no endpoint de login:

**Endpoint:**  
`POST /api/auth/login`

**Corpo da Requisição:**

```json
{
  "username": "usuario_exemplo",
  "password": "senha_exemplo"
}
```

**Resposta:**

```json
{
  "token": "seu_token_jwt_aqui"
}
```

**Exemplo de uso do token em uma requisição autenticada:**

```bash
curl -X GET "http://localhost:8080/api/alerta" -H "Authorization: Bearer seu_token_jwt_aqui"
```

---

## 🚀 Rodando o Projeto Localmente

### 1. Construir o Projeto com Maven

```bash
mvn clean install
```

### 2. Rodar o Projeto

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:  
🔗 [http://localhost:8080](http://localhost:8080)

---

## 🌐 Deploy em Nuvem

A aplicação também está disponível via Render:  
🔗 [https://dashboard.render.com/web/srv-d11ifqe3jp1c73f1j3l0/deploys/dep-d11ifqm3jp1c73f1j3p0?r=2025-06-06%4017%3A38%3A54%7E2025-06-06%4017%3A41%3A01](https://dashboard.render.com/web/srv-d11ifqe3jp1c73f1j3l0/deploys/dep-d11ifqm3jp1c73f1j3p0?r=2025-06-06%4017%3A38%3A54%7E2025-06-06%4017%3A41%3A01)