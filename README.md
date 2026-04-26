# QueryHub

A scalable Q&A Backend System built with Spring Boot 3, Java 17, and JPA.
Implementing RESTful APIs for managing users, questions, answers, comments, and tags with pagination to ensure scalability and optimized performance.


## Table of Contents
- Overview
- Features
- Tech Stack
- Architecture & Design
- Data Model
- API Endpoints
- Getting Started (Local)
- Configuration
- Run & Test
- API Usage Examples (cURL)
- Known Issues & Notes
- Roadmap / Future Enhancements
- Project Structure


## Overview
QueryHub is a practice project showcasing a clean, layered Spring Boot application:
- Controllers expose REST endpoints
- Services encapsulate business logic
- Repositories use Spring Data JPA
- Entities and DTOs keep I/O decoupled from persistence models


## Features
- Users can register and follow Tags
- Create, fetch, and delete Questions
- Create, fetch (by ID/by Question), and delete Answers
- Create, fetch (by ID/by Answer/by Comment-thread), and delete Comments
- Tag management (create, list, get by ID, delete)
- Simple pagination on list endpoints that support it


## Tech Stack
- Language: Java 17
- Framework: Spring Boot 3.3.x (Web, Data JPA)
- Database: MySQL 8+ (via JDBC driver)
- Build: Gradle
- Utilities: Lombok, ModelMapper
- Testing: Spring Boot Starter Test, JUnit Platform

Gradle plugin versions and dependencies are defined in `build.gradle`.


## Architecture & Design
- Package root: `com.practice.queryHub`
- Layers:
  - `controller`: REST endpoints (request/response)
  - `services` & `services.impl`: business logic
  - `repositories`: Spring Data JPA interfaces
  - `model`: JPA entities
  - `dtos`: request/response payload objects
- JPA/Hibernate with UUID primary keys (via `BaseModel`)


## Data Model
- BaseModel
  - `id: UUID` (auto-generated)

- User
  - `username: String`
  - `password: String`
  - `followedTags: Set<Tag>` (Many-to-Many)

- Tag
  - `name: String`
  - `followers: Set<User>` (inverse side of Many-to-Many)

- Question
  - `title: String`
  - `content: String`
  - `tags: Set<Tag>` (Many-to-Many)
  - `user: User` (Many-to-One)

- Answer
  - `Content: String` (NOTE: capitalized field name in code)
  - `question: Question` (Many-to-One)
  - `user: User` (Many-to-One)
  - `comments: Set<Comment>` (One-to-Many)
  - `likedBy: Set<User>` (Many-to-Many)

- Comment
  - `content: String`
  - `answer: Answer` (Many-to-One)
  - `parentComment: Comment` (Many-to-One, for threaded replies)
  - `replies: Set<Comment>` (One-to-Many)
  - `likedBy: Set<User>` (Many-to-Many)

DTOs (request payloads)
- UserDTO: `username, password`
- TagDTO: `id?, name`
- QuestionDTO: `id?, title, content, userId, tagIds`
- AnswerDTO: `id?, content, userId, questionId`


## API Endpoints
Base URL: by default the app runs on `http://localhost:8080`

Users (`/api/v1/users`)
- POST `/` — Register user
- GET `/` — List all users
- GET `/{userId}` — Get user by ID
- DELETE `/{userId}` — Delete user by ID
- POST `/{userId}/followTag/{tagId}` — User follows a tag

Tags (`/api/v1/tags`)
- POST `/` — Create tag
- GET `/` — List all tags
- GET `/{tagId}` — Get tag by ID
- DELETE `/{tagId}` — Delete tag by ID

Questions (`/api/v1/questions`)
- POST `/` — Create question (body: `QuestionDTO`)
- GET `/?page={page}&size={size}` — List questions (paginated)
- GET `/{questionId}` — Get question by ID
- DELETE `/{questionId}` — Delete question by ID

Answers (NOTE: current base path uses `vi` not `v1`) (`/api/vi/answers`)
- POST `/` — Create answer (body: `AnswerDTO`)
- GET `/{answerId}` — Get answer by ID
- GET `/question/{questionId}?page={page}&size={size}` — List answers for a question (paginated)
- DELETE `/{answerId}` — Delete answer by ID

Comments (`/api/v1/comments`)
- POST `/` — Create comment (body: `CommentDTO`)
- GET `/{commentId}` — Get comment by ID
- GET `/answer/{answerId}?page={page}&size={size}` — List comments for an answer (paginated)
- GET `/comment/{commentId}?page={page}&size={size}` — List replies for a comment (paginated)
- DELETE `/{commentId}` — Delete comment by ID

Pagination Parameters
- `page` (int), `size` (int) — required for list endpoints that support pagination in controllers (Questions, Answers by Question, Comments by Answer, Replies by Comment)


## Getting Started (Local)
Prerequisites
- Java 17 (JDK)
- MySQL 8+
- Gradle (wrapper included)

Database
- Create a local database (defaults to `QUERY_DB_LOCAL`).
- The application currently uses `spring.jpa.hibernate.ddl-auto=create` which drops/creates schema on startup — suitable for local/dev only.

Clone & Build
```bash
# Windows PowerShell
cd D:\JavaProj\QueryHub\queryHub
.\u005cgradlew.bat clean build
```

Run the App
```bash
# Using Gradle Wrapper
.\u005cgradlew.bat bootRun
# or run the generated jar after build
java -jar .\build\libs\queryHub-0.0.1-SNAPSHOT.jar
```

The app starts at `http://localhost:8080`.


## Configuration
Location: `src/main/resources/application.properties`
```
spring.application.name=queryHub
spring.datasource.url=jdbc:mysql://localhost:3306/QUERY_DB_LOCAL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=*********
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```
Important
- Do NOT commit real credentials in production. Prefer environment variables or a secrets manager.
- Recommended: externalize sensitive config using environment variables or a `application-local.properties` and add it to `.gitignore`.
- Suggested replacements:
  - `spring.datasource.username=${DB_USERNAME}`
  - `spring.datasource.password=${DB_PASSWORD}`


## Run & Test
Run
```bash
.gradlew.bat bootRun
```

Tests
```bash
.\u005cgradlew.bat test
```
Sample test class: `src/test/java/com/practice/queryHub/QueryHubApplicationTests.java`


## API Usage Examples (cURL)
Create User
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "password": "secret"
  }'
```

Create Tag
```bash
curl -X POST http://localhost:8080/api/v1/tags \
  -H "Content-Type: application/json" \
  -d '{
    "name": "java"
  }'
```

Create Question
```bash
curl -X POST "http://localhost:8080/api/v1/questions" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "How do I configure Spring Boot with MySQL?",
    "content": "I need help connecting Spring Boot to MySQL.",
    "userId": "<USER_UUID>",
    "tagIds": ["<TAG_UUID>"]
  }'
```

Create Answer
```bash
curl -X POST "http://localhost:8080/api/vi/answers" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Use the MySQL driver and configure datasource properties.",
    "userId": "<USER_UUID>",
    "questionId": "<QUESTION_UUID>"
  }'
```

List Questions (paginated)
```bash
curl "http://localhost:8080/api/v1/questions?page=0&size=10"
```

List Answers for a Question (paginated)
```bash
curl "http://localhost:8080/api/vi/answers/question/<QUESTION_UUID>?page=0&size=10"
```

Create Comment on an Answer
```bash
curl -X POST "http://localhost:8080/api/v1/comments" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Great answer!",
    "answerId": "<ANSWER_UUID>",
    "parentCommentId": null
  }'
```


## Known Issues & Notes
- Endpoint prefix typo in `AnswerController`: base mapping is `/api/vi/answers` ("vi"), likely intended to be `/api/v1/answers`.
- In `Answer` entity, the field is named `Content` (capital C). Consider renaming to `content` for Java bean consistency and JSON mapping clarity.
- `spring.jpa.hibernate.ddl-auto=create` will drop/create schema at each start — fine for local dev but risky elsewhere.
- No authentication/authorization layer is present; all endpoints are open.
- Validation (e.g., Bean Validation) is minimal; consider adding request validations.


## Roadmap / Future Enhancements
- Fix AnswerController base path to `/api/v1/answers` and update clients/tests.
- Standardize field names (e.g., `Answer.content`).
- Add input validation and structured error responses.
- Add authentication/authorization (JWT or session-based) and password hashing.
- Introduce pagination defaults and sorting.
- Add OpenAPI/Swagger UI for auto-generated API docs.
- Use Flyway/Liquibase for schema migrations instead of `ddl-auto=create`.
- Add integration and repository tests.
- Containerization (Dockerfile, docker-compose for MySQL).


## Project Structure
```
queryHub/
├─ build.gradle
├─ settings.gradle
├─ src
│  ├─ main
│  │  ├─ java/com/practice/queryHub
│  │  │  ├─ QueryHubApplication.java
│  │  │  ├─ configuration/Config.java
│  │  │  ├─ controller/
│  │  │  ├─ dtos/
│  │  │  ├─ exception/
│  │  │  ├─ model/
│  │  │  ├─ repositories/
│  │  │  └─ services/ (and services/impl)
│  │  └─ resources/application.properties
│  └─ test/java/com/practice/queryHub/QueryHubApplicationTests.java
└─ README.md
```
