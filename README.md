# Running

To run the project we can build the docker image with the command:

`docker build -t iopl/prices .`

You can then run on port 8080 with

`docker run -p 8080:8080 --name prices iopl/prices`

The server will respond to the request:

`GET http://localhost:8080/api/prices`

It has mandatory input parameters.
- applicationDate
- productId
- brandId

Request example: 

`http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1`

# Open API

You can download the Open API file from:

`http://localhost:8080/api-docs.yaml`

# Architecture

The project is made with hexagonal architecture, with the primary adapters in the `inbound` package, the secondary adapters in the `outbound` package and the domain, ports and use cases in the `application` package. No application services were needed but they could also be within the `application` package.

The `inbound` and `outbound` packages depend on application, but `application` does not depend on anything, therefore you could make 3 separate maven modules to ensure that `application` never depends on the other two, and that `inbound` and `outbound` do not have direct communication either.

In the project it has been used:
- Spring Data: It is used for access to the database and management of the entities
- H2: H2 in-memory database
- Flyway: It is used for database versioning
- Spring Web: It is used to managing HTTP requests and creating controllers
- Spring Validation: It is used to validate HTTP requests, it could also be used to validate entities when you need to create them in the database from the code
- Spingdoc OpenAPI: It is used to generate the OpenAPI yaml file
- Mapstruct: It is used to generate the object conversion mappers between the different application packages
- Spring Test: It is used to generate the unit tests (junit) and the tests loading the Spring context
- Cucumber: It is used to generate contract test with gherkins

# Questions
- What happens if there are two prices at the same time with the same priority? Right now the first one in the list is returned (randomly)
- How will queries evolve? We could use Criteria or something like that if we foresee it being more complex, but at this point it could be over-engineering.
