<h1 align="center"> :classical_building: Voting Session :balance_scale:</h1>

## :notebook: About

The project is a simulator of parking management, used for studies.

## :heavy_check_mark: Features

- Create a new agenda(developed);
- Starting a voting session on an agenda(developed);
- Receive votes from users on agendas(developed);
- Count the votes and give the result of the agenda vote(developed).

## :hammer_and_wrench: Technology

The following technologies were used in the construction of the project:

- Java
- Spring Boot
- JUnit
- PostgreSQL
- Swagger

### :scroll: Dependencies

- Spring Web
- Spring Data JPA
- Validation
- PostgreSQL Driver

## 	:leftwards_arrow_with_hook: Requirements to use the system

You must have installed on your machine the tool [Git](https://git-scm.com/) and a nice editor, to work on the code. I recomend the [IntelliJ IDEA](https://www.jetbrains.com/idea/).
To run the routes, this project has the endpoints documented in swagger(http://localhost:8080/swagger-ui/index.html#/).

#### :checkered_flag: To run the project

```
I) Clone this repository

$ git clone <https://github.com/Brms5/votingSessionApp.git>

II) Access the project folder in the terminal

$ cd ./voting-session

III) To download the project dependencies via maven

$ mvn clean install

IV) To start the project

$ mvn spring-boot:run

The server wil start on port:8080 - access <http://localhost:8080>
```
