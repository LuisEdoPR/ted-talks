***

# Test - Ted Talks Project - Luis Eduardo Patiño

***

## Technical Document

## ![Documentation](documentation/pdf.png) [Documentation (click here)](documentation/Java_programming_intake.pdf)

## Requirements from the Assessments included in the Solution

- CRUD
- Additional APIs:
  - Get Talk by ID
  - Upload talks from CSV file
- Kotlin (as a language)
- Unit Test (not full coverage due to the short time)
- SpringBoot
- Documentation: OpenApi
- Database: Postgres SQL
- Design patterns
- Docker: To create instance of Postgres SQL
- Test: Postman and Unit test
- Caching: Caffeine
- API documentation: OpenApi (Swagger)
- RESTful API: use many of the HttpMethods and RESTful principles
- Architecture: Hexagonal Architecture

***

## Starting

You need to do next steps to start with develop or run to this project.

## Pre-Requirements

#### Java configuration:

- Download  ***version 17*** of the JDk according to the operating
  system ([JDK list](https://www.oracle.com/java/technologies/downloads/#java17))


- To verify the version of java, we need to execute the following command:
    ```sh
    $ java -version
    ```

***

## Run the API

Steps to run the API:

- We must install or update the necessary dependencies for
  the API.

- run the project using your IDE or console.


***

## OpenAPI Documentation (Swagger)

In order to see the endpoints of the services, you must go to
the browser and open the following url:

[swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

***

## Testing API

Run the App fro the IDE and used the information provided in the Postman Folder.

![Postman](documentation/postman.png) [Testing using Postman](https://www.postman.com/)

- Install postman app
- Open Postman and Import the Collection from the `/Postman/TalksCollection.postman_collection.json`
- Open All the request and Execute. Suggested Order:
  - Create a Talk
  - Upload Talks
  - Update Talks
  - GetTalks (paginated)
  - getTalkById
  - SearchTalk By Filters (title, author, Likes or Views)

`Note:` For Upload Talk, you can use the `data.csv` added into the folder Postman, you can use the same structure 
to create your own example. This API allow only `.csv` files.

***

## Use Postman as DB

Please, you can use postgres as a DB. If you want you can use docker to create postman as a service using the definition 
in the `docker-compose.yml`.
To run it you can run this command in the root path from the terminal:

```
docker-compose up -d  
```

After, you can run the app from the IDE, and it will create the Table with all the columns required using liquibase. 
You can see the initial changes for the database in the `/src/main/resources/db/changelog/`.
***

## Used Technology

![Kotlin](documentation/Kotlin.png) [Kotlin](https://kotlinlang.org/)

![Spring Boot](documentation/spring-boot.png) [Spring Boot](https://spring.io/projects/spring-boot)

![Gradle](documentation/gradle.png) [Gradle](https://gradle.org/)

![Rest](documentation/rest.png) [Rest](https://es.wikipedia.org/wiki/Transferencia_de_Estado_Representacional)

![OpenApi](documentation/openapi.png) [OpenApi](https://www.openapis.org/)

Other Tools included:
- Postgres SQL
- Docker
- Junit5
- Liquibase
- Caffeine (cache)
- H2 (DB in memory)

***
***