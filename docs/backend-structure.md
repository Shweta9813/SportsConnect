# SportConnect Backend Structure

## Technology

Java 21
Spring Boot
Maven

## Current Structure

src/main/java
    Main Java application code

src/main/resources
    Application configuration and resources

src/test
    Automated tests

## Main Application

BackendApplication.java

This is the entry point of the Spring Boot application.

The main() method starts the Spring Boot application using
SpringApplication.run().

## Main Backend Packages

The backend is organized into different packages based on responsibility.

### entity

Contains Java classes that represent database tables.

Examples:

- User.java
- Sport.java
- Game.java
- GameParticipant.java

### repository

Contains interfaces used to communicate with the database using Spring Data JPA.

Examples:

- UserRepository.java
- SportRepository.java
- GameRepository.java
- GameParticipantRepository.java

### service

Contains the business logic of the application.

Examples:

- UserService.java
- SportService.java
- GameService.java
- GameParticipantService.java

### controller

Contains REST API endpoints.

Examples:

- UserController.java
- SportController.java
- GameController.java
- GameParticipantController.java

### security

Contains security and JWT-related functionality.

Examples:

- JwtService.java
- JwtAuthenticationFilter.java
- SecurityConfig.java

---

## Game Participant Feature

The Game Participant feature allows authenticated users to join games.

The flow is:

Controller
↓
Service
↓
Repository
↓
Database

For joining a game:

GameParticipantController
↓
GameParticipantService
↓
GameParticipantRepository
↓
game_participants table

The logged-in user's identity is obtained from the JWT authentication.

The user does not send their user ID while joining a game.