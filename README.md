# calculator-manager-api

This project goal is to provide an REST API to allow calculations. Each calculation requested by the user has a cost, and will be executed or not based on user's credit. The API is written in Clojure, and it uses a relational DB (PostgreSQL) to store relevant information. Also, it can be run locally with Docker. More information on how to run is provided below.

## Usage

In order to run the application, you neeed to have installed Docker along with Docker Compose. Once you make sure they are installed, run the following commands:

```sh
git clone git@github.com:monteiroflavio/calculator-manager-api.git
cd calculator-manager-api
cp .env.example .env
docker compose up --build
```

to run both API and database locally. Application will be available by default at `127.0.0.1:8080`. Another port can be set by changing the environment `PORT` at `.env` file and running docker again.

In order to use private (authenticated) routes, a default user has been provided. Its username and password are as follow:

- `username`: `"foo@bar.com"`
- `password`: `"AYag$vzzM/zXtSBs=$mI+9+Q3/yjkzvxEb2u1X9Sc3LNM="`

In case you want to run the tests, you need to have Leiningen inistalled. If you do, inside project's folder, run:

```
lein test
```

## API routes

In this section, it will be described the routes provided by the application.

### Public routes

#### `POST /v1/authenticate`

This route's goal is to authenticate the user, returning a token which will be used to allow user requests in private routes. 

#### Request Body
```json
{ 
    "username": "foo@bar.com",
    "password": "someEncryptedPassword"
}
```

- `username`: User's registered email to be checked.
- `password`: Encrypted password related to user.

#### Responses

##### Success response
- **Status code**: 200
- **Response body**:
```json
{
    "token": "jwsToken"
}
```

##### Bad request response
- **Status code**: 400
- **Response body**:
```json
{
    "message": "Bad request: {:username missing-required-key}"
}
```

### Private routes

All routes below need to have the parameter `Authorization` set on request headers. Its value should be `Bearer <token>`, where `<token>` is returned by `POST /v1/authenticate` success request. All requests where authorization is not set, will receive the following as response:

- **Status code**: 401
- **Response body**:
```json
{
    "message": "Unauthorized"
}
```

#### `GET /v1/users/:user-id/operations`

This route provides a list of all available operations on the application. It does not expect any parameters, since the total of operations exiting in application counts to seven.

#### Request parameters

`None`

#### Responses

##### Success response
- **Status code**: 200
- **Response body**:
```json
[
    {
        "id": 1,
        "type": "credit",
        "cost": 0.0
    },
    ...
    {
        "id": 7,
        "type": "random-string",
        "cost": 500.0
    }
]
```

#### `GET /v1/users/:user-id/records`

This route returns a list of operation records requested by a user. It returns only records whose `status = "A"`, i.e. active records.

#### Request parameters

- `q`: String which filters records by users' `username` or operations' `type` which contains `q` value in it.
- `limit`: Max number of records which can be retrieved.
- `offset`: Number of registers filtered out from query response. It can be affected by `sorting` and `sorting-field`.
- `sorting`: Either `asc` or `desc`. Sorts `sorting-field` in ascending or descending order.
- `sorting-field`: Field which should be used to sorting.

#### Responses

##### Success response
- **Status code**: 200
- **Response body**:
```json
[
    {
        "id": 2,
        "user": {
            "id": 1,
            "username": "foo@bar.com",
            "status": "A"
        },
        "operation": {
            "id": 5,
            "type": "division",
            "cost": 250.0
        },
        "user-balance": 1000.0,
        "amount": 750.0,
        "operation-response": "-1",
        "date": "2023-10-16 19:38:35.365854",
        "status": "A"
    }
]
```

##### Not found response
- **Status code**: 404
- **Response body**:
```json
{
    "message": "Record not found."
}
```

#### `POST /v1/users/:user-id/records`

Endpoint responsible for attempting to perform a new calculation. Checks if user has enough credit, performs the required calculation, and stores it to database.

#### Request Body
```json
{ 
    "operation-id": 2,
    "a": 1,
    "b": 2
}
```

- `operation-id`: ID of operation to be performed.
- `a`: Numerical field to be used on calculation. Must exist to all operations, except for `random-string` operation. As for `square-root`, it must be positive.
- `b`: Numerical field to be used on calculation. Must exist for `addition`, `subtraction`, `multiplication` and `division`. For `division`, it must be different of zero.

##### Success response
- **Status code**: 201
- **Response body**:

`None`

##### Not enough credit response
- **Status code**: 400
- **Response body**:
```json
{
    "message": "Not enough credit - user balance: 0.0, operation cost: 50.0"
}
```

#### `DELETE /v1/users/:user-id/records/:record-id`

Soft deletes record. This deletion does not affect the credit calculation, so every commited record potentially will be checked in order to check credit availability.

##### Success response
- **Status code**: 204
- **Response body**:

`None`

##### Not found response
- **Status code**: 404
- **Response body**:
```json
{
    "message": "Record not found."
}
```

## Project structure

Below, it is presented a tree view of relevant files and folders of the project. The project structure reflects the design applied during the development. Here, it will be explained better why this structure was used and the utility of some files.

```sh
.
├── docker-compose.yml
├── Dockerfile
├── resources
├── src
│   └── calculator_manager_api
│       ├── adapters
│       │   ├── clients
│       │   ├── commons
│       │   ├── config.clj
│       │   ├── controllers
│       │   ├── migrations.clj
│       │   └── routes.clj
│       ├── core.clj
│       ├── logics
│       ├── mappers
│       ├── models
│       ├── ports
│       │   ├── repositories
│       │   └── services
│       └── wires
│           ├── db
│           └── in
└── test
    └── calculator_manager_api
        ├── logics
        ├── mappers
        └── services
```

### `Dockefile` and `docker-compose.yaml`

Those files are responsible for allowing this project to be ran agnostic to platform and/or development tools installed. Also, they allow a simpler deployment in efemeral machines, such as ECS from AWS.

### `resources`

This is where all external resources should be placed. Here, deployment files relevant to cloud platform such AWS will take place.

### `src/calculator_manager_api`

All the source code needed to execute the application lies within this folder. This is the default files separation in several languages.

### `core.clj`

*"Main"* file. It contains all necessary dependancy with higher level components which are responsible for running the application.

### `adapters` and `ports`

Those two folders will be explained together since they directly reflect the software architecture pattern which guided the development of this project, which is the Hexagonal Architecture.

#### `adapters`

Within `adapters` folders, all code which depends of some external source is placed, being it a database or an encryption library, for instance. Also, it includes all code that, from the point of view of the application, is external, such a HTTP library abstraction or a manager of configurations. All code placed here is not directly linked to the application domain, and could be replaced without any loss to the application, since all signatures are kept.

#### `ports`

Inside `ports` folder is all code that is responsible for interfacing internal and external application bondaries. `services` and `repositories` both depend on `adapters` components, and orchestrate calls to more inner pure functions, such as the ones placed on `logics` or `mappers` folders.

### `logics` and `mappers`

Those are the highest level and more independent pieces of code. They are completely independent of other layers, and are used to build the application knowledge on the `ports` layers. `logics` contain pieces of code that carry some businesss rule or are used to remove complexity from other layers, also allowing reuse as a collateral. `mappers` are responsible for converting data from one layer to another, such as database data to domain data, and so on.

### `models` and `wires`

`models` and `wires` reflect the data at different layers. `models` are data from domain layer and `wires` are from more external layers, such as HTTP requests (`wires/in`) or database (`wires/db`).

## Project decisions

Here, some decisions made throughout the development will be described.

- DynamoDB was the first choice for the job. Some libraries (e.g. faraday) were tested, but as they seemed too complex to do simple things, and many abstraction would need to be developed in order to reduce coupling, a relational approach was choose with PostgreSQL's honeysql library.
- For the sake of simplicity, and since there are only two routes that depends on it, routes that expose data are exposing `models` instead of an output layer data (e.g. `wires/out`).
- `adapters/commons` are pieces of code that could be extracted to a library, but, as they will not be required to any other project for now, they will be kept there.
- Users authentication is done through JWS token, which is not the most safe. It could be replaced for other strategy such as JWT or encrypted JWT in the future.
- Initial database data is inserted through `migrations.clj`. A better approach to manage migrations is to be decided.