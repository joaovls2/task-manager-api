# 📝 Task Manager API

API REST para gerenciamento de tarefas, desenvolvida com **Java + Spring Boot**, com autenticação, 
controle de acesso por usuário e validações de regras de negócio.

---

## 🛠️ Tecnologias

- Java 21  
- Spring Boot  
- Spring Data JPA  
- H2 Database  

## 🔧 Ferramentas

- Postman  
- Maven  
- Git  

---

## 🌐 Deploy

A aplicação está disponível em ambiente de produção:

🔗 https://task-manager-api-ugzf.onrender.com

> Obs: Pode haver um pequeno delay na primeira requisição devido ao cold start do Render.

---

## 🔐 Funcionalidades

- Cadastro de usuários com senha criptografada  
- Autenticação via Basic Auth  
- Criação de tarefas vinculadas ao usuário autenticado  
- Listagem de tarefas por usuário  
- Atualização parcial de tarefas  
- Validação de regras de negócio:
  - Datas não podem estar no passado  
  - Data de início deve ser anterior à data de término  
- Controle de acesso:
  - Usuário só pode visualizar e alterar suas próprias tarefas  

---

## 📌 Endpoints

### 👤 Usuários

- POST /users/ → Criar usuário  

---

### 📝 Tarefas (requer autenticação)

- POST /tasks/ → Criar tarefa  
- GET /tasks/ → Listar tarefas do usuário  
- PUT /tasks/{id} → Atualizar tarefa (parcial)  

---

## 🔐 Autenticação

A API utiliza Basic Auth:

- Username: username do usuário  
- Password: password cadastrado  

As senhas são armazenadas de forma segura utilizando BCrypt.

---

## ⚠️ Regras importantes

- Não é possível criar tarefas com datas no passado  
- A data de início deve ser anterior à data de término  
- Um usuário não pode acessar ou modificar tarefas de outro usuário  

---

## 🧪 Banco de dados

O projeto utiliza banco em memória H2.

Acesse:  
http://localhost:8080/h2-console  

Configuração:  
JDBC URL: jdbc:h2:mem:todolist  
User: admin  
Password: admin  

---

## 📈 Melhorias futuras

- Implementação de autenticação com JWT  
- Criação de DTOs para controle de dados expostos  
- Paginação e ordenação de tarefas  
- Validação com Bean Validation (@Valid)  
- Testes automatizados  
