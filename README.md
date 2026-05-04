# Contatos API

API REST para gerenciamento de contatos, desenvolvida com Java 21 e Spring Boot.

Projeto focado em boas práticas de arquitetura em camadas, Docker e persistência com MySQL.

### Tecnologias

![Java 21](https://img.shields.io/badge/Java_21-2D2D2D?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.3.4-2D2D2D?style=flat&logo=springboot&logoColor=6DB33F)
![Spring Web](https://img.shields.io/badge/Spring_Web-2D2D2D?style=flat&logo=spring&logoColor=6DB33F)
![Spring Validation](https://img.shields.io/badge/Spring_Validation-2D2D2D?style=flat&logo=spring&logoColor=6DB33F)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-2D2D2D?style=flat&logo=spring&logoColor=6DB33F)
![JUnit 5](https://img.shields.io/badge/JUnit_5-2D2D2D?style=flat&logo=junit5&logoColor=25A162)
![WebTestClient](https://img.shields.io/badge/WebTestClient-2D2D2D?style=flat&logo=spring&logoColor=6DB33F)
![MySQL](https://img.shields.io/badge/MySQL-2D2D2D?style=flat&logo=mysql&logoColor=4479A1)
![H2 Database](https://img.shields.io/badge/H2_Database-2D2D2D?style=flat&logo=sqlite&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2D2D2D?style=flat&logo=docker&logoColor=2496ED)
![Swagger](https://img.shields.io/badge/Swagger-2D2D2D?style=flat&logo=swagger&logoColor=85EA2D)
![Postman](https://img.shields.io/badge/Postman-2D2D2D?style=flat&logo=postman&logoColor=FF6C37)
---

### Endpoints

endpoints disponíveis nesta aplicação:

👤 Users
| Método | URL | Descrição |
| :--- | :--- | :--- |
| `POST` | `/v1/users` | Recurso de criação de usuários |
| `GET` | `/v1/users` | Recurso de listagem dos usuários |
| `PATCH` | `/v1/users/{id}` | Recurso de atualização do usuário |
| `GET` | `/v1/users/{id}` | Recurso para listar o usuário pelo ID |
| `DELETE` | `/v1/users/{id}` | Recurso para deletar um usuário pelo ID |

📞 Contatos
| Método | URL | Descrição |
| :--- | :--- | :--- |
| `POST` | `/v1/contacts` | Recurso de criação de contatos |
| `GET` | `/v1/contacts` | Recurso de listagem dos contatos |
| `PATCH` | `/v1/contacts/{id}` | Recurso de atualização de contatos |
| `GET` | `/v1/contacts/{id}` | Recurso para listar o contatos pelo ID |
| `GET` | `/v1/users/{phone}/{id}` | Recurso para buscar um contato pelo telefone e id do usuário |
| `DELETE` | `/v1/users/{id}` | Recurso para deletar um contato pelo ID |

## 🐳 Rodando com Docker

1. Crie a imagem:

```
docker build -t {image_name} .
```
2. Suba os containers: 

```
docker-compose up --build
```

3. Verifique os containers: 

```
docker ps
```



1. A API estará disponível em:

```
http://localhost:8080/v1/
```

2. O Swagger estará em:

```
http://localhost:8080/contacts-docs.html
```
