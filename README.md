# Ebac Microsservice Modulo 59

Projeto prático do curso de Java da [Ebac](https://ebaconline.com.br).

Arquitetura de microsserviços com Spring Boot, Eureka, PostgreSQL e Flyway.

---

## Serviços

| Serviço | Porta | Banco | Porta DB |
|---------|-------|-------|----------|
| `discovery-server` | `8761` | — | — |
| `users-service` | `8080` | `users_service` | `5432` |
| `book-service` | `8081` | `books_service` | `5433` |
| `loan-service` | `8082` | `loans_service` | `5434` |

---

## Rotas

### discovery-server

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `http://localhost:8761/eureka/apps` | Lista serviços registrados (Eureka) |

---

### users-service

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/users` | Criar usuário |
| `GET` | `/api/users` | Listar todos os usuários |
| `GET` | `/api/users/{id}` | Buscar usuário por ID |
| `PUT` | `/api/users/{id}` | Atualizar usuário |
| `DELETE` | `/api/users/{id}` | Remover usuário |

**Request body (POST/PUT)**:
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```

---

### book-service

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/books` | Criar livro |
| `GET` | `/api/books` | Listar livros (`?titulo=dom` para filtrar) |
| `GET` | `/api/books/{id}` | Buscar livro por ID |
| `PUT` | `/api/books/{id}` | Atualizar livro |

**Request body (POST/PUT)**:
```json
{
  "title": "Dom Casmurro",
  "author": "Machado de Assis",
  "isbn": "978-85-325-0001-9",
  "availableQuantity": 5
}
```

---

### loan-service

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/loans` | Criar empréstimo |
| `PUT` | `/api/loans/{id}/return` | Registrar devolução |
| `GET` | `/api/loans` | Listar empréstimos (`?status=ACTIVE&userId=uuid`) |
| `GET` | `/api/loans/{id}` | Buscar empréstimo por ID |
| `GET` | `/api/loans/active` | Listar empréstimos ativos |
| `GET` | `/api/loans/overdue` | Listar empréstimos em atraso |

**Request body (POST)**:
```json
{
  "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "bookId": "d4e5f6a7-b8c9-0123-defa-234567890abc",
  "expectedReturnDate": "2026-07-20",
  "loanDate": "2026-07-07"
}
```

> `loanDate` é opcional (padrão: data atual). `expectedReturnDate` é obrigatório e deve ser futura.

---

## Como executar

1. Iniciar o discovery-server:
   ```bash
   cd discovery-server && mvn spring-boot:run
   ```
2. Iniciar os serviços em terminais separados:
   ```bash
   cd users-service && mvn spring-boot:run
   cd book-service && mvn spring-boot:run
   cd loan-service && mvn spring-boot:run
   ```

> Os bancos PostgreSQL podem ser iniciados via `docker-compose.yml` de cada serviço.
