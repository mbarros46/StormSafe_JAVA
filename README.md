# 🌧️ StormSafe API - Monitoramento e Prevenção de Enchentes


![StormSafe Banner](https://via.placeholder.com/1200x300/2C3E50/FFFFFF?text=StormSafe+API)

A **StormSafe API** é um sistema backend robusto construído com **Spring Boot**, dedicado ao monitoramento e prevenção de enchentes. Utilizando dados de sensores e informações climáticas, a aplicação oferece uma série de endpoints RESTful para gerenciar alertas, sensores, regiões, rotas de evacuação e muito mais, fornecendo dados cruciais para a segurança de áreas de risco.

---

## ✨ Funcionalidades Principais

*   **Gerenciamento de Usuários**: Cadastro e autenticação de diferentes tipos de usuários (ADMIN, CIVIL).
*   **Monitoramento de Sensores**: Registro e consulta de dados de sensores (nível da água, precipitação).
*   **Alertas Inteligentes**: Criação e gestão de alertas de risco baseados em dados de sensores e regras de negócio.
*   **Rotas de Evacuação**: Definição e consulta de rotas seguras para evacuação.
*   **Registro de Eventos**: Log de evacuações e outras ocorrências importantes.
*   **Gerenciamento de Regiões**: Cadastro e controle de áreas geográficas de interesse.

---

## ✅ Requisitos do Sistema

Certifique-se de ter os seguintes pré-requisitos instalados em sua máquina para rodar a aplicação:

*   ☕ **Java Development Kit (JDK) 17 ou superior**: A aplicação é compilada e executada com Java 17.
*   📦 **Apache Maven 3.x**: Ferramenta essencial para gerenciamento de dependências e construção do projeto.
*   🛢️ **PostgreSQL**: O banco de dados relacional utilizado. Certifique-se de ter uma instância rodando.
*   🐳 **Docker** (Opcional): Para uma configuração de ambiente mais rápida e isolada.

---

## ⚙️ Configuração do Ambiente

### 1. Clonar o Repositório

Comece clonando o projeto para o seu ambiente de desenvolvimento:

```bash
git clone https://github.com/seu-usuario/StormSafe.git
cd StormSafe
```

### 2. Configurar o Banco de Dados (PostgreSQL)

O projeto utiliza PostgreSQL. Você pode configurar um banco de dados localmente ou utilizar um serviço em nuvem. Certifique-se de criar um banco de dados com o nome `stormsafe`.

Exemplo de configuração no arquivo `src/main/resources/application.properties`:

```properties
# Configurações do Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/stormsafe
spring.datasource.username=postgres
spring.datasource.password=sua_senha_do_banco
spring.jpa.hibernate.ddl-auto=update # Cria/atualiza o schema do DB automaticamente
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Chave secreta para JWT (MUITO IMPORTANTE: Altere em produção!)
jwt.secret=StormSafeJWTSecretKeyExample1234567890
```
**Importante**: Substitua `sua_senha_do_banco` pela sua senha do PostgreSQL e considere usar uma chave JWT mais segura em ambientes de produção.

---

## 🚀 Rodando o Projeto Localmente

### 1. Construir o Projeto

Navegue até o diretório raiz do projeto e utilize o Maven para construir a aplicação:

```bash
./mvnw clean install
```

### 2. Iniciar a Aplicação

Após a construção bem-sucedida, você pode iniciar a API:

```bash
./mvnw spring-boot:run
```
A aplicação estará disponível em `http://localhost:8080`.

---

## 🧪 Acesso e Testes da API

### 1. Documentação Interativa (Swagger UI)

A forma mais fácil de explorar e testar a API é através do Swagger UI. Uma vez que a aplicação esteja rodando, acesse:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 2. Autenticação (Obtendo um Token JWT)

Para acessar a maioria dos endpoints protegidos, você precisará de um token JWT.

**Endpoint de Login:**
`POST http://localhost:8080/login`

**Corpo da Requisição (Exemplo):**
```json
{
  "email": "seu_email@example.com",
  "password": "sua_senha"
}
```
**Resposta (Exemplo):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "type": "Bearer"
}
```
*Guarde este `token` para as próximas requisições.*

### 3. Exemplo de Teste de Endpoint (Público)

Para o endpoint de listagem de usuários, que agora é público:

**Requisição GET /api/usuarios:**
```bash
curl -X 'GET' \
  'http://localhost:8080/api/usuarios?page=0&size=10&sort=id%2CDESC' \
  -H 'accept: */*'
```
Você deverá receber uma resposta JSON com a lista de usuários, sem erro 403.

### 4. Exemplo de Teste de Endpoint (Protegido)

Para endpoints que exigem autenticação (ex: criar um alerta), inclua o token JWT no cabeçalho `Authorization`:

**Requisição POST /api/alertas:**
```bash
curl -X 'POST' \
  'http://localhost:8080/api/alertas' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer SEU_TOKEN_JWT_AQUI' \
  -d '{
    "regiao": { "id": 1 },
    "mensagem": "Alerta de teste: Nível do rio subindo rapidamente.",
    "nivelCriticidade": "ALTO",
    "dataHora": "2024-06-07T14:30:00",
    "status": "ATIVO"
  }'
```
**Observação**: Certifique-se de substituir `SEU_TOKEN_JWT_AQUI` pelo token que você obteve no passo de autenticação. Os dados do corpo da requisição (`-d`) devem estar de acordo com o DTO esperado pelo endpoint.

---

☁️ Deploy em Nuvem (Render)
A API está publicada na plataforma Render e pode ser acessada online:

🔗 Swagger (Documentação Interativa):
https://stormsafe-java.onrender.com/swagger-ui/index.html

⚠️ Importante: como o Render está em plano gratuito, a primeira requisição pode demorar ~50 segundos. Após esse tempo, o servidor permanece ativo por alguns minutos.

🔗 O projeto esta sendo utilizado neste link:
 [https://dashboard.render.com/web/srv-d11ifqe3jp1c73f1j3l0/deploys/dep-d12eoap5pdvs73cltff0](https://dashboard.render.com/web/srv-d11ifqe3jp1c73f1j3l0/deploys/dep-d12eoap5pdvs73cltff0)

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues, enviar pull requests ou sugerir melhorias.


---

Feito com ❤️ por **Miguel Barros/StormSafe**
