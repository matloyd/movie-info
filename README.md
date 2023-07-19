# Movie Info Service

The Movie Info Service is a Spring Boot project that provides information about movies using the Open Movie Database (OMDB) API. It allows users to retrieve detailed movie information by title or IMDB ID and also provides information about whether a movie has won the Best Picture award.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:

    `git clone https://github.com/matloyd/movie-info.git`  
    `cd movie-info-service`

2. Run the application using your preferred IDE or using Maven:

    `mvn spring-boot:run`

## Usage

The Movie Info Service provides two main endpoints to retrieve movie information:

1. **Get Movie Info by Title:**  
    `GET /api?title={movie-title}`
 

This endpoint retrieves movie information based on the provided title.

2. **Get Movie Info by IMDB ID:**  
    `GET /api?imdbID={imdb-id}`

This endpoint retrieves movie information based on the provided IMDb ID.

## Dependencies

- Spring Boot Starter Data JPA: For data access and database operations.
- Spring Boot Starter Web: For creating RESTful APIs.
- H2 Database: In-memory database for development and testing.
- MapStruct: For object mapping between DTOs and entities.
- Lombok: For reducing boilerplate code in entities and DTOs.
- Spring Boot Starter Web Services: For web service testing.
- Spring Boot Starter Test: For unit and integration testing.
- Mockito Core: For mocking objects during testing.
- JUnit: For writing unit tests.

## Contributing

Contributions to the Movie Info Service are welcome! If you find a bug or want to add a new feature, please open an issue or submit a pull request.
