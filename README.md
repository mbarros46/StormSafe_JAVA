
# 🌪️ StormSafe - API REST Java Spring Boot

StormSafe é uma solução inteligente para evacuação em situações de enchentes, integrando sensores IoT, inteligência artificial e geolocalização para orientar a população em tempo real por rotas seguras.

## 📦 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- Bean Validation
- OpenAPI / Swagger
- Oracle Database (via Docker)
- Docker & Docker Compose

## 📁 Estrutura do Projeto

```
src/main/java/br/com/fiap/stormsafe
├── config         # Configurações de segurança, CORS, Swagger
├── controller     # Endpoints REST
├── dto            # Objetos de transferência de dados
├── exception      # Tratamento global de exceções
├── model          # Entidades JPA
├── repository     # Interfaces de persistência
├── service        # Lógica de negócio
└── util           # Utilitários e mapeadores
```

## 🚀 Como Executar Localmente

1. Gere o `.jar` do projeto:
```bash
./mvnw clean package
```

2. Execute via Docker:
```bash
docker-compose up --build
```

3. Acesse:
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## 🔐 Autenticação

- JWT obrigatório nos endpoints protegidos.
- Adicione o token no Swagger em **Authorize** com `Bearer <token>`.

## 📄 Variáveis de Ambiente

Use o `.env.example` como base:

```
JWT_SECRET=segredoUltraSeguro123
```

## 📦 Endpoints Principais

| Entidade         | Endpoint base       |
|------------------|---------------------|
| Usuários         | `/usuarios`         |
| Regiões          | `/regioes`          |
| Sensores         | `/sensores`         |
| Leituras         | `/leituras`         |
| Alertas          | `/alertas`          |
| Rotas Evacuação  | `/rotas`            |
| Logs Evacuação   | `/evacuacoes`       |

## ✅ Funcionalidades

- CRUD completo com validação
- Paginação, ordenação e filtros
- Documentação Swagger
- Autenticação com JWT
- Pronto para deploy com Docker

## 📽️ Entregáveis

- ✅ Repositório no GitHub
- ✅ Vídeo de demonstração (máx. 10 min)
- ✅ Pitch do projeto (máx. 3 min)

## 🧠 Projeto acadêmico

Desenvolvido como parte da Global Solution FIAP — disciplina Java Advanced.

## Membros

- Miguel Barros Ramos
- Pedro Valentim Merise
