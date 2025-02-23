# 🛒 API - Loja Online

API para gerenciamento de uma loja online, permitindo que usuários realizem pedidos, adicionem produtos aos pedidos e acompanhem o status das compras. Inclui autenticação, controle de estoque e administração de produtos.

📌 **A documentação completa da API está disponível no Swagger** em `/swagger-ui.html`.

## 🔑 Autenticação

### 📌 Registro de Usuário
**`POST /auth/cadastro`**  
Cria um novo usuário.  

### 📌 Login  
**`POST /auth/login`**  
Autentica o usuário e retorna um token JWT.  

---

## 👤 Usuário

### 📌 Obter Dados do Usuário  
**`GET /usuarios`**  
Retorna informações dos usuários.  

---

## 🛍️ Produtos

### 📌 Criar Produto  
**`POST /produtos`** (Admin)  
Cria um novo produto.  

### 📌 Listar Produtos  
**`GET /produtos`**  
Retorna todos os produtos disponíveis.  

### 📌 Atualizar Produto  
**`PUT /produtos`** (Admin)  
Edita um produto existente.  

### 📌 Deletar Produto  
**`DELETE /produtos`** (Admin)  
Desativa um produto da loja.  

---

## 🛒 Pedidos

### 📌 Criar Pedido  
**`POST /pedidos`**  
Cria um novo pedido com produtos e quantidades.  

### 📌 Listar Pedidos do Usuário  
**`GET /pedidos`**  
Retorna todos os pedidos do usuário autenticado.   

### 📌 Atualizar Status do Pedido  
**`PATCH /orders`** (Admin)  
Altera o status do pedido (`PENDENTE`, `PROCESSANDO`, `ENTREGUE`, etc.).  

---

## 📦 Itens do Pedido 

### 📌 Listar Itens de um Pedido  
**`GET /itens`**  
Retorna todos os itens de um pedido.  
  
---

## 🛠 Tecnologias  
- **Spring Boot** (Backend)  
- **JWT** (Autenticação)  
- **JPA/Hibernate** (ORM)  
- **Postgres** (Banco de Dados)  

---

📌 **Observação:** Endpoints protegidos requerem **autenticação JWT**.  
