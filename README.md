# ShortnerURL

![ShortnerURL](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

## 📖 Sobre o Projeto

**ShortnerURL** é uma aplicação backend desenvolvida em **Kotlin** com **Spring Boot**, que permite criar URLs encurtadas a partir de URLs longas e realizar redirecionamentos.  
O projeto foca em **simplicidade, desempenho e segurança**, fornecendo uma API RESTful para gerenciamento de links e testes unitários bem estruturados.

### Funcionalidades
- Criar URLs encurtadas.
- Redirecionar para a URL original a partir do código encurtado.
- Validação de URLs para garantir formatos válidos (`http` ou `https`).
- Logging detalhado de operações.
- Testes unitários com **JUnit 5** e **Mockito**.

---

## 🏗 Tecnologias Utilizadas

- **Kotlin** – Linguagem principal.
- **JDK 17** - JDK utilizada.
- **Spring Boot 3.5.7** – Framework backend.
- **Spring Data JPA** – Acesso a banco de dados.
- **PostgreSQL** – Banco de dados relacional.
- **JUnit 5** – Framework de testes.
- **Mockito** – Mocks e injeção de dependências em testes unitários.
- **Maven** – Gerenciamento de dependências.
- **MockMvc** – Testes de endpoints REST.

---

## ⚡ Exemplos de Uso

### Criar uma URL encurtada

```http
POST /shortner/create
Content-Type: application/x-www-form-urlencoded
url=https://www.google.com
```

Resposta:

```
{
  "message": "The shortcut was successfully created.",
  "url": "https://www.google.com",
  "code": "abc123"
}

```
