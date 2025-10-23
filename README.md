🧑‍💻 User CRUD API — Kotlin + Spring Boot



Simple REST API that manages users with Create, Read, Update, and Delete operations.
Built in Spring Boot 3.5.6 and Kotlin, with H2 in-memory database, MockK unit tests, and Swagger UI for testing.

🚀 Run the project
Using Gradle
./gradlew bootRun

Or from IntelliJ

Run the Application.kt file.

🌐 Test endpoints (Swagger UI)

After running, open your browser:

👉 http://localhost:8080/swagger-ui.html


You can explore and execute all endpoints directly there.

🧩 API Endpoints
Method	Endpoint	Description
GET	/api/users	List all users
GET	/api/users/{id}	Get one user by ID
POST	/api/users	Create a new user
PUT	/api/users/{id}	Update a user
DELETE	/api/users/{id}	Delete a user
Example request (POST /api/users)
{
  "name": "Alice",
  "email": "alice@mail.com",
  "password": "apassword"
}

⚙️ Tech Stack
Kotlin
Spring Boot 3.5.6
Spring Web + JPA + Validation
H2 Database (in-memory)
Spring Security (disabled via SecurityConfig)
Swagger UI (springdoc-openapi)
Unit tests with JUnit5 + MockK
🧪 Run Unit Tests
./gradlew test

🧱 Project Structure
src/main/kotlin/com/example/users
 ├── Application.kt
 ├── config/SecurityConfig.kt
 ├── controller/UserController.kt
 ├── dto/UserRequest.kt, UserResponse.kt
 ├── model/User.kt
 ├── repository/UserRepository.kt
 └── service/UserService.kt

💡 Next Steps
Add authentication (JWT)
Replace H2 with a DB
Add integration tests
Deploy to Docker / AWS
