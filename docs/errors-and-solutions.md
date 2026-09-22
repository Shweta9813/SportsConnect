# Errors and Solutions

## Error 1 — Spring Boot DataSource Configuration

### Problem

Application failed to start with:

Failed to configure a DataSource:
'url' attribute is not specified

### Cause

Spring Data JPA and PostgreSQL Driver were added,
but PostgreSQL connection details were not configured.

### Solution

Added PostgreSQL configuration to application.properties:

- Database URL
- Username
- Password
- Hibernate configuration

### Learning

Spring Boot needs database connection information
when Spring Data JPA is used with PostgreSQL.

## JWT Authentication

### Problem
After adding Spring Security, requests to protected APIs returned:

403 Forbidden

### Cause
Spring Security was rejecting unauthenticated requests with 403.

### Solution
Configured an authentication entry point to return:

401 Unauthorized

for unauthenticated requests.

### JWT Flow

POST /api/users/login
        ↓
UserController
        ↓
UserService
        ↓
BCrypt password verification
        ↓
JwtService
        ↓
JWT token generated
        ↓
Client sends:
Authorization: Bearer <JWT>
        ↓
JwtAuthenticationFilter
        ↓
JWT verified
        ↓
Protected API allowed

### Testing

Without JWT:
GET /api/hello
→ 401 Unauthorized

With valid JWT:
GET /api/hello
→ 200 OK
→ Welcome to SportConnect!

---

## Game Participant — Join Game

### Feature

Implemented the Join Game functionality.

Endpoint:

POST /api/games/{gameId}/join

### Flow

Client
↓
POST /api/games/{gameId}/join
↓
JWT Authentication
↓
GameParticipantController
↓
GameParticipantService
↓
GameParticipantRepository
↓
game_participants table

### Rules Implemented

#### 1. Game must exist

If the requested game does not exist:

Game not found

#### 2. User must exist

The logged-in user's email is obtained from JWT authentication
and used to find the user.

#### 3. Duplicate Join Prevention

Before saving a participant, the application checks whether
the user has already joined the game.

If the user already joined:

You have already joined this game

#### 4. Maximum Players

The application counts the existing participants and compares
the count with the game's maxPlayers value.

If the game is full:

Game is full

#### 5. Successful Join

If all validations pass, a GameParticipant record is created
and saved.

Response:

Game joined successfully

### Testing

Valid user joining a game:
→ Game joined successfully

Same user joining the same game again:
→ You have already joined this game

Joining a full game:
→ Game is full

All three conditions were tested successfully.

---

## View Game Participants

### Feature

Implemented the functionality to view users who have joined a game.

Endpoint:

GET /api/games/{gameId}/participants

### Flow

Client
↓
GET /api/games/{gameId}/participants
↓
GameParticipantController
↓
GameParticipantService
↓
GameParticipantRepository
↓
game_participants table
↓
User information
↓
GameParticipantResponse
↓
JSON response

### Response

The API returns selected information about each participant:

- User ID
- Name
- Email
- Location
- Skill level

Password information is not returned.

### Testing

Game with participants:
→ Participant information returned successfully

Game with no participants:
→ []

Non-existing game:
→ Game not found

---

## Leave Game

### Feature

Implemented the functionality that allows an authenticated user
to leave a game they have joined.

Endpoint:

DELETE /api/games/{gameId}/leave

### Flow

Client
↓
DELETE /api/games/{gameId}/leave
↓
JWT Authentication
↓
GameParticipantController
↓
GameParticipantService
↓
GameParticipantRepository
↓
Delete GameParticipant record

### Rules

If the user has joined the game:

→ You have left the game successfully

If the user has not joined the game:

→ You have not joined this game

The game itself is not deleted.
The user itself is not deleted.
Only the GameParticipant record is removed.

### Testing

User who joined the game:
→ You have left the game successfully

User who already left / never joined:
→ You have not joined this game

After leaving:

GET /api/games/{gameId}/participants
→ []

---

## Error — No EntityManager With Actual Transaction

### Problem

When testing the Leave Game API, the application returned:

No EntityManager with actual transaction available for current thread
- cannot reliably process 'remove' call

### Cause

The repository was performing a DELETE operation:

deleteByGameIdAndUserId()

but the operation was not running inside an active database transaction.

### Solution

Added @Transactional to the leaveGame() service method.

Example:

@Transactional
public String leaveGame(Long gameId, String userEmail) {
    ...
}

### Learning

Database modification operations such as DELETE should run
inside a transaction.

@Transactional tells Spring to execute the database operation
within a transaction and commit the changes successfully.

### Result

After adding @Transactional:

DELETE /api/games/{gameId}/leave
→ Game participant successfully deleted
→ No EntityManager transaction error