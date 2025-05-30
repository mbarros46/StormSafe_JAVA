# 🌪️ StormSafe API

API REST desenvolvida em Java com Spring Boot como parte da Global Solution FIAP 2025. A proposta do projeto é oferecer uma solução tecnológica inovadora para mitigar os impactos de desastres naturais e eventos climáticos extremos, facilitando o monitoramento, alerta e evacuação de áreas de risco.

## 🚀 Visão Geral

StormSafe é uma API centralizadora de dados relacionados a sensores ambientais, alertas de calor extremo, regiões monitoradas e rotas de evacuação. A solução visa:

- Coletar dados de sensores em tempo real.
- Emitir alertas automáticos para áreas de risco.
- Propor rotas de evacuação seguras.
- Garantir acesso via sistema web ou mobile.

---

## 📦 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Bean Validation
- Oracle Database (via JDBC)
- Swagger OpenAPI
- HikariCP
- Docker (para futuro deploy)
- Railway (ou Render) para deploy na nuvem

---

## 🛠️ Funcionalidades da API

- Cadastro e consulta de **usuários**
- Registro de **sensores IoT**
- Leitura periódica dos sensores
- Geração e consulta de **alertas**
- Cadastro de **regiões** e **rotas de evacuação**
- Registro de **logs de evacuação**

---

## 🔐 Autenticação (Em Desenvolvimento)

> 🔴 A autenticação JWT foi removida temporariamente. A versão final da API será protegida com `Bearer Token JWT` nos endpoints privados.

---

## 📑 Documentação Swagger

Acesse a documentação completa e interativa:

👉 [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

---

## 🧪 Exemplo de Endpoints

- `GET /usuarios` – Lista todos os usuários (paginado)
- `POST /sensores` – Cadastra novo sensor
- `GET /alertas` – Consulta alertas emitidos
- `GET /regioes` – Lista regiões monitoradas
- `POST /rotas` – Cria nova rota de evacuação

---

## 📂 Estrutura do Projeto

br.com.fiap.stormsafe
├── config # Swagger, Security
├── controller # REST Controllers
├── dto # Data Transfer Objects
├── model # Entidades JPA
├── repository # Interfaces JPA
├── service # Lógica de negócio
└── StormsafeApplication.java

yaml
Copiar
Editar

---

## 🧪 Como Executar Localmente

### Pré-requisitos

- Java 21+
- Maven
- Oracle Database (instância local ou remota)

### Passos

```bash
# Clone o projeto
git clone https://github.com/seu-usuario/StormSafe_JAVA.git
cd StormSafe_JAVA

# Compile e execute
mvn spring-boot:run
A aplicação rodará em: http://localhost:8080

☁️ Deploy em Nuvem
🚧 O deploy será realizado na plataforma Railway com banco Oracle integrado. A URL de produção será adicionada abaixo:

🔗 Produção: em breve

👨‍💻 Equipe
Pedro Merise (RM556826)

Miguel Barros Ramos (RM556652)

📄 Licença
Este projeto é acadêmico e não possui licença comercial.

