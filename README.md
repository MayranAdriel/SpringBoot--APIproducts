# API de Gerenciamento de Produtos com HATEOAS

Esta é uma API RESTful desenvolvida em **Java 17** utilizando **Spring Boot**, implementando um CRUD completo para a gestão de produtos. A API segue o **Modelo de Maturidade de Richardson**, atingindo o nível 3 com a implementação de **HATEOAS** para navegabilidade.

## Tecnologias Utilizadas

- **Java 17** (Amazon Corretto 17.0.11)
- **Spring Boot**
  - **Spring Web**: para a construção de endpoints RESTful.
  - **Spring Validation**: para validação de dados de entrada.
  - **Spring HATEOAS**: para adição de links de navegabilidade nos recursos.
- **PostgreSQL**: banco de dados relacional utilizado para persistência dos dados.
- **Swagger**: para documentação interativa da API.

## Funcionalidades

- **Buscar Produto por ID**: recupera os detalhes de um produto passando seu ID na URL. Inclui um link para a listagem de todos os produtos.
- **Listar Todos os Produtos**: retorna todos os produtos cadastrados, onde cada produto possui um link para acessar seus detalhes.
- **Criar Novo Produto**: adiciona um novo produto ao sistema.
- **Deletar Produto por ID**: remove um produto especificado pelo seu ID.

## Estrutura dos Endpoints

### Base URL

`http://localhost:8080/products`

### Endpoints com HATEOAS

#### 1. Buscar Produto por ID

- **GET** `http://localhost:8080/products/{id}`
- **Descrição**: Retorna os detalhes de um produto específico e um link para a listagem de todos os produtos.
- **Exemplo de Resposta (200 - OK)**:
  ```json
  {
    "id": "e6cc9cfb-4994-4565-b627-c05eb026aa9",
    "name": "Monitor Dell",
    "value": 750,
    "_links": {
      "allProducts": {
        "href": "http://localhost:8080/products"
      }
    }
  }
  ```

#### 2. Listar Todos os Produtos

- **GET** `http://localhost:8080/products`
- **Descrição**: Retorna uma lista de todos os produtos cadastrados, com cada produto contendo um link para acessar seus detalhes.
- **Exemplo de Resposta (200 - OK)**:

```json
[
  {
    "idProduct": "e6cc9cfb-4994-4565-b627-c05eb026aa9e",
    "name": "Cerveja",
    "value": 6.5,
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/products/e6cc9cfb-4994-4565-b627-c05eb026aa9e"
      }
    ]
  },
  {
    "idProduct": "c1dc4bb2-498d-4bf8-8c72-d8ef32aae4fe",
    "name": "Fralda",
    "value": 35.9,
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/products/c1dc4bb2-498d-4bf8-8c72-d8ef32aae4fe"
      }
    ]
  }
]
```

#### 3. Criar Novo Produto

- _POST_ `http://localhost:8080/products/{id}`
- _Descrição_: Adiciona um novo produto ao sistema.
- _Corpo da Requisição_:

```json
{
  "name": "Fralda",
  "value": 35.9
}
```

Exemplo de Resposta (201 - Created):

```json
{
  "idProduct": "c1dc4bb2-498d-4bf8-8c72-d8ef32aae4fe",
  "name": "Fralda",
  "value": 35.9
}
```

#### 4. Atualizar um produto

- _PUT_ `http://localhost:8080/products/{id}`
- _Descrição_: Atualiza um produto do sistema.
- _Corpo da Requisição_:

```json
{
  "name": "Fralda",
  "value": 29.9
}
```

Exemplo de Resposta (200 - OK):

```json
{
  "idProduct": "c1dc4bb2-498d-4bf8-8c72-d8ef32aae4fe",
  "name": "Fralda",
  "value": 29.9
}
```

#### 5. Deletar Produto por ID

- _DELETE_ `http://localhost:8080/products/{id}`
- _Descrição_: Remove um produto específico do sistema.
- _Exemplo de Resposta (404 - Not Found)_:`Product Not Found`
- _Exemplo de Resposta (200 - OK)_:`Deleted Successfully`

## Configuração do Ambiente

### Pré-requisitos

- _Java 17_ (Amazon Corretto recomendado).
- _Maven_ para gerenciamento de dependências.
- _Postgresql_ com um banco de dados configurado.

### Variáveis de Ambiente

Certifique-se de configurar as seguintes variáveis no arquivo `application.properties` ou no ambiente:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/API_PRODUCTS
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

## Documentação Interativa

A API possui uma interface interativa gerada pelo Swagger. Para acessá-la, basta acessar:

```bash
http://localhost:8080/swagger-ui/index.html
```

## Execução

1. Clone o repositório:

Usando SSH:

```bash
git clone git@github.com:MayranAdriel/SpringBoot--APIproducts.git
```

Usando HTTPS:

```bash
git clone https://github.com/MayranAdriel/SpringBoot--APIproducts.git
```

2. Navegue até o diretório do projeto:

```bash
cd nome-do-projeto
```

3. Execute o projeto com Maven:

```bash
mvn spring-boot:run
```

## Contribuições

Sinta-se à vontade para contribuir com melhorias, novos endpoints ou correções de bugs.

1. Faça um fork do repositório.
2. Crie uma nova branch:

```bash
git checkout -b minha-nova-feature
```

3. Envie suas alterações:

```bash
git push origin minha-nova-feature
```

4. Abra um Pull Request.
