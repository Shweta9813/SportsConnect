# SportConnect Database Design

## Database Name

sportconnect_db

## Planned Tables

1. users
2. sports
3. games
4. game_participants
5. events
6. ratings
7. notifications

---

## 1. Users

Stores information about registered SportConnect users.

| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary key |
| name | VARCHAR | User name |
| email | VARCHAR | User email |
| password | VARCHAR | Hashed password |
| location | VARCHAR | User location |
| skill_level | VARCHAR | Playing skill |
| created_at | TIMESTAMP | Account creation date |

---

## 2. Sports

Stores the sports supported by the application.

| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary key |
| name | VARCHAR | Sport name |

---

## Relationship

A user can create multiple games.

A sport can have multiple games.

GAME will contain references to USER and SPORT.

---

## 4. Game Participants

Stores information about users who join games.

| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary key |
| game_id | BIGINT | Reference to the game |
| user_id | BIGINT | Reference to the user |
| joined_at | TIMESTAMP | Time when the user joined |

---

## Game Participant Relationships

A game can have multiple participants.

A user can participate in multiple games.

Therefore, `game_participants` connects users and games.

USER
  |
  | 1
  |
  | many
  ↓
GAME_PARTICIPANTS
  ↑
  | many
  |
  | 1
  |
GAME

---

## Join Game Rules

When a user tries to join a game:

1. The game must exist.
2. The user must exist.
3. The user must not already be a participant of the game.
4. The number of participants must not exceed `maxPlayers`.
5. The participant record is saved with the current date and time.

The combination of `game_id` and `user_id` is unique.

This prevents the same user from joining the same game more than once.

---

## View Game Participants

The application provides an API to view all users who have joined a particular game.

Endpoint:

GET /api/games/{gameId}/participants

The API first checks whether the game exists.

If the game exists, it retrieves all GameParticipant records
for that game and returns selected user information.

Password information is not returned in the response.

### Example Response

[
    {
        "userId": 2,
        "name": "Shweta",
        "email": "shweta2@example.com",
        "location": "Thane",
        "skillLevel": "INTERMEDIATE"
    }
]

If the game has no participants, the API returns:

[]

### Relationship Flow

Game
↓
GameParticipant
↓
User
↓
GameParticipantResponse

---

## Leave Game

The application allows an authenticated user to leave a game they have joined.

Endpoint:

DELETE /api/games/{gameId}/leave

### Leave Game Flow

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
Check Game
↓
Find Logged-in User
↓
Check GameParticipant
↓
Delete GameParticipant record
↓
Database

### Leave Game Rules

1. The game must exist.
2. The logged-in user must exist.
3. The user must have already joined the game.
4. Only the participation record is deleted.
5. The game and user records are not deleted.

### Example

Before leaving:

game_participants

| game_id | user_id |
|---|---|
| 2 | 2 |

After:

game_participants

| game_id | user_id |
|---|---|
| | |

The user and game remain in the database.
Only their participation relationship is removed.