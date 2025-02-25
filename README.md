# 🛍️ Sistema de Loja Online (Backend)

Este projeto é um **backend REST** para um sistema de loja online, onde usuários podem realizar pedidos, gerenciar produtos e acessar diferentes funcionalidades dependendo de suas permissões.

## 📌 Funcionalidades

✅ **Autenticação e autorização com JWT**  
✅ **CRUD de produtos**  
✅ **Gerenciamento de pedidos**  
✅ **Regras de negócio para estoque e status de pedidos**  

---

## 📂 Entidades e Regras de Negócio

### **1️⃣ Usuário (`User`)**
- **Atributos**: ID, nome, email, cpf, senha (hash), role (`ADMIN` ou `CLIENTE`) e ativo (`true` ou `false`).
- **Permissões**:
  - O **ADMIN** pode gerenciar produtos e pedidos de qualquer usuário.
  - O **CLIENTE** pode apenas **fazer pedidos, visualizar seus próprios pedidos**.

### **2️⃣ Produto (`Product`)**
- **Atributos**: ID, nome, descrição, preço, data de cadastro e quantidade em estoque e ativo.
- **Regras**:
  - O **ADMIN** pode adicionar, editar e desativar produtos.
  - O **CLIENTE** pode apenas visualizar produtos disponíveis.
  - Um produto só pode ser vendido se houver estoque suficiente e estiver diponível.

### **3️⃣ Pedido (`Order`)**
- **Atributos**: ID, usuário (cliente), data do pedido, status, valor total e itens do pedido.
- **Regras**:
  - O **pedido deve ter pelo menos um produto**.
  - O **status do pedido pode ser:**  
    - `PENDENTE`: quando criado.  
    - `PROCESSANDO`: após confirmação de pagamento.  
    - `ENVIADO`: quando despachado.  
    - `ENTREGUE`: quando o cliente recebe.  
    - `CANCELADO`: se houver algum problema.  
  - Apenas o **ADMIN** pode atualizar o status do pedido.
  - O **CLIENTE pode cancelar pedidos apenas se estiverem "PENDENTES"**.

---

## 📊 Consultas JPQL

1️⃣ **Buscar pedidos por usuário logado** (Cliente pode ver apenas seus pedidos).  
2️⃣ **Buscar pedidos por status** (Ex.: listar todos os pedidos "ENVIADOS").  
3️⃣ **Verificar estoque antes de confirmar pedido**. 

---

## 🔐 Segurança com Spring Security + JWT

- **Usuários precisam estar autenticados para acessar qualquer rota protegida**.
- **ADMIN pode acessar todas as rotas**.
- **CLIENTE só pode acessar suas próprias informações**.
- **As senhas são armazenadas com hash (BCrypt)**.

---

## 🚀 Tecnologias Utilizadas

- **Java + Spring Boot**
- **Spring Security + JWT**
- **JPA (Hibernate) + Banco de Dados (PostgreSQL)**
- **Swagger para documentação da API**  

---

## 📦 Como Rodar o Projeto

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git

# Acesse a pasta do projeto
cd seu-repositorio

# Configure o banco de dados no application.properties

# Execute o projeto
mvn spring-boot:run
```

---

## 📮 API Endpoints (Principais)

### 🔑 **Autenticação**
- `POST /auth/login` → Retorna token JWT
- `POST /auth/cadastro` → Cria um novo usuário

### 🛍 **Produtos**
- `GET /produtos` → Lista produtos disponíveis
- `POST /produtos` → (ADMIN) Adiciona um novo produto
- `PUT /produtos` → (ADMIN) Atualiza um produto
- `DELETE /produtos/{id}` → (ADMIN) Remove um produto

### 📦 **Pedidos**
- `POST /pedidos` → Cria um novo pedido
- `GET /pedidos` → Lista pedidos do usuário logado
- `GET /pedidos/{status}` → Lista pedidos pelo status
- `PUT /pedidos` → (ADMIN) Atualiza status do pedido
- `DELETE /pedidos/{id}` → Cancela um pedido (somente se "PENDENTE")
