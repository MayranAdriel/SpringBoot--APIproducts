# API de Produtos

Esta é uma API RESTful para gerenciamento de produtos, desenvolvida utilizando **Spring Boot** com suporte a operações
CRUD completas. A aplicação também inclui autenticação via **Spring Security**, validação de dados com **Validation**, e
utiliza o **PostgreSQL** como banco de dados.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
    - **Spring Web**: Para desenvolvimento de endpoints RESTful.
    - **Spring Security**: Para autenticação e autorização.
    - **Validation**: Para validação de dados.
- **PostgreSQL**: Banco de dados relacional.
- **Maven**: Gerenciamento de dependências.

## ⚙️ Funcionalidades

- **Autenticação e Autorização**:
- **Operações CRUD**:
    - **C**reate: Adicionar um novo produto.
    - **R**ead: Listar produtos ou obter detalhes de um produto específico.
    - **U**pdate: Atualizar informações de um produto existente.
    - **D**elete: Remover um produto.
- **Validação de Dados**:
    - Validações customizadas para entradas de dados, como nome do produto e preço.
- **Persistência**:
    - Utilização de JPA/Hibernate para interação com o PostgreSQL.

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior.
- Maven.
- PostgreSQL.

### Configuração

1. Clone este repositório:
   ```bash
  
