<h1 align="center"> :classical_building: Voting Session :balance_scale:</h1>

## :notebook: Sobre

O projeto é uma solução para gerenciar sessões de votação. No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, o projeto será a solução para gerenciar essas sessões de votação.

## :heavy_check_mark: Funcionalidades

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:
- Cadastrar uma nova pauta(desenvolvido);
- Abrir uma sessão de votação em uma pauta(desenvolvido);
- Receber votos dos associados em pautas(desenvolvido);
- Contabilizar os votos e dar o resultado da votação na pauta(desenvolvido).

## :hammer_and_wrench: Tecnologias

As seguintes tecnologias foram usadas na construção do projeto:

- Java
- Spring Boot

### :scroll: Dependencias

- Spring Web
- Spring Data JPA
- Validation
- PostgreSQL Driver

## 	:leftwards_arrow_with_hook: Requisitos para utilizar o sistema

É necessário ter instalado em sua máquina a ferramenta [Git](https://git-scm.com/) e um bom editor, que ajuda bastante para trabalhar com o código. Eu recomendo o [IntelliJ IDEA](https://www.jetbrains.com/idea/).
Para executar as rotas, enquanto não houver interface, recomendo a ferramenta [Postman](https://www.postman.com/).

#### :checkered_flag: Rodando o Projeto

```
I) Clone este repositório

$ git clone <https://github.com/Brms5/votingSessionApp.git>

II) Acesse a pasta do projeto no terminal

$ cd ./voting-session

III) Para baixar as dependências do projeto através do maven

$ mvn clean install

IV) Para iniciar o projeto

$ mvn spring-boot:run

O servidor inciará na porta:8080 - acesse <http://localhost:8080>
```
