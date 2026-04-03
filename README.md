# Backend Paula Nuss Brand

> Esse projeto foi desenvolvido com o propósito educacional, de reforçar meus conhecimentos de construção de API's seguindo o padrão rest Rest. - Vale ressaltar que por se tratar de um projeto de estudo pessoal, não há integração com GateWay de pagamento (Até porque *AINDA* não estou nesse nível)

## Tecnologias

- Spring boot: **3.4.0**
- Java: **21**
- Persistencia de dados: **Spring Data JPA com Hibernate**
- Segurança: **Spring Security**
- Autenticação: **JWT**
- Banco de dados: **MySQL 8**
- Store image: [**Cloudinary**](https://cloudinary.com)
- Deploy (Backend): [**Render**](https://render.com)

---

# Estrutura
Para a construção da api, optei pela Arquitetura em Camadas, visto que é o padrão para o tipo de projeto
- **Controller**: Exposição dos endpoints REST e manipulação de DTOs.
- **Service**: Concentração da regra de negócio e integrações externas.
- **Repository**: Camada de abstração para consultas ao banco de dados.
- **Entity**: Mapeamento objeto-relacional (ORM).
- **Config**: Definições de segurança, CORS e Beans globais.
- **DTO (Data Transfer Object)**: Padronização da entrada e saída de dados da API.



