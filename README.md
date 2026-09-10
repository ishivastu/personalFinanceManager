💰 Personal Finance Manager API

A RESTful Personal Finance Management API built with Spring Boot 3.x and Java 21.

The API allows users to securely manage their income, expenses, categories, savings goals, and financial reports with session-based authentication and user-level data isolation.

🌐 Live API: https://personalfinancemanager-y991.onrender.com/api

✨ Features
🔐 User registration and session-based login/logout
💳 Transaction management
Create transactions
View transactions
Update transactions
Delete transactions
Filter by date, category, and transaction type
🏷️ Category management
7 default categories
Custom user categories
🎯 Savings goals
Create and manage financial goals
Track savings progress
Set target amounts and dates
📊 Financial reports
Monthly reports
Yearly reports
🔒 User data isolation
🛡️ Spring Security integration
🗄️ Spring Data JPA
🐳 Docker deployment
☁️ Deployed on Render
🛠️ Tech Stack
Component	Technology
Language	Java 21
Framework	Spring Boot 3.x
Security	Spring Security
Authentication	Session-based authentication
Database	H2 In-Memory Database
ORM	Spring Data JPA / Hibernate
Build Tool	Maven
Deployment	Docker + Render
📁 Project Structure
syfe/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── syfe/
│       │           └── pfm/
│       │               ├── SyfeApplication.java
│       │               │
│       │               ├── config/
│       │               │   └── SecurityConfig.java
│       │               │
│       │               ├── controller/
│       │               │   ├── AuthController.java
│       │               │   ├── TransactionController.java
│       │               │   ├── CategoryController.java
│       │               │   ├── SavingsGoalController.java
│       │               │   └── ReportController.java
│       │               │
│       │               ├── dto/
│       │               │   ├── LoginRequest.java
│       │               │   ├── RegisterRequest.java
│       │               │   ├── TransactionRequest.java
│       │               │   ├── TransactionResponse.java
│       │               │   ├── SavingsGoalRequest.java
│       │               │   ├── SavingsGoalUpdateRequest.java
│       │               │   ├── SavingsGoalDTO.java
│       │               │   ├── ReportDTO.java
│       │               │   └── YearlyReportDTO.java
│       │               │
│       │               ├── exception/
│       │               │   └── GlobalExceptionHandler.java
│       │               │
│       │               ├── model/
│       │               │   ├── User.java
│       │               │   ├── Category.java
│       │               │   ├── Transaction.java
│       │               │   └── SavingsGoal.java
│       │               │
│       │               ├── repository/
│       │               │   ├── UserRepository.java
│       │               │   ├── CategoryRepository.java
│       │               │   ├── TransactionRepository.java
│       │               │   └── SavingsGoalRepository.java
│       │               │
│       │               └── service/
│       │                   ├── UserService.java
│       │                   ├── TransactionService.java
│       │                   ├── CategoryService.java
│       │                   ├── SavingsGoalService.java
│       │                   └── ReportService.java
│       │
│       └── resources/
│           ├── application.properties
│           └── data.sql
│
├── Dockerfile
├── pom.xml
└── README.md

🚀 Getting Started
Prerequisites

Make sure you have the following installed:

Java 21
Maven 3.9+
Git
Docker (optional, for containerized deployment)
📥 Clone the Repository
git clone https://github.com/your-username/syfe.git
cd syfe


Replace your-username with your GitHub username.

🔨 Build the Project
mvn clean install

▶️ Run the Application
mvn spring-boot:run


The API will be available at:

http://localhost:8080/api

🗄️ H2 Database Console

The application uses an H2 in-memory database.

Open:

http://localhost:8080/h2-console

H2 Configuration
Property	Value
JDBC URL	jdbc:h2:mem:pfm
Username	sa
Password	(empty)

⚠️ Because H2 is configured as an in-memory database, data is not persistent and will be lost when the application restarts.

📚 API Documentation

Base URL:

/api


For the deployed application:

https://personalfinancemanager-y991.onrender.com/api

🔐 Authentication
Register
POST /api/auth/register


Request body:

{
  "username": "user@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890"
}

Login
POST /api/auth/login


Request body:

{
  "username": "user@example.com",
  "password": "password123"
}


The API uses session-based authentication. After successful login, the session cookie should be included in subsequent authenticated requests.

Logout
POST /api/auth/logout


No request body is required.

💳 Transactions

Transactions can represent income or expenses and are associated with the authenticated user.

Create Transaction
POST /api/transactions


Request:

{
  "amount": 5000.00,
  "date": "2024-01-15",
  "category": "Salary",
  "description": "January Salary"
}

Get Transactions
GET /api/transactions


Optional query parameters:

startDate
endDate
categoryId
type


Example:

GET /api/transactions?startDate=2024-01-01&endDate=2024-01-31

Update Transaction
PUT /api/transactions/{id}


Request:

{
  "amount": 6000.00,
  "category": "Salary",
  "description": "Updated Salary"
}

Delete Transaction
DELETE /api/transactions/{id}

🏷️ Categories

The application provides default categories and allows users to create custom categories.

Get Categories
GET /api/categories

Create Category
POST /api/categories


Request:

{
  "name": "Freelance",
  "type": "INCOME"
}

Delete Category
DELETE /api/categories/{name}

🎯 Savings Goals

Savings goals allow users to define financial targets and track their progress.

Create Goal
POST /api/goals


Request:

{
  "goalName": "Emergency Fund",
  "targetAmount": 50000.00,
  "targetDate": "2026-12-31",
  "startDate": "2024-01-01"
}

Get All Goals
GET /api/goals

Get Goal by ID
GET /api/goals/{id}

Update Goal
PUT /api/goals/{id}


Request:

{
  "targetAmount": 60000.00,
  "targetDate": "2026-06-30"
}

Delete Goal
DELETE /api/goals/{id}

📊 Reports

The API provides financial summaries for individual users.

Monthly Report
GET /api/reports/monthly/{year}/{month}


Example:

GET /api/reports/monthly/2024/1

Yearly Report
GET /api/reports/yearly/{year}


Example:

GET /api/reports/yearly/2024

🧪 Testing

The project includes API testing through a test script.

Quick Test with cURL
1. Register
curl -X POST https://personalfinancemanager-y991.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test@example.com","password":"password123","fullName":"Test User","phoneNumber":"+1234567890"}'

2. Login
curl -X POST https://personalfinancemanager-y991.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test@example.com","password":"password123"}' \
  -c cookies.txt


The -c cookies.txt option stores the session cookie.

3. Create a Transaction
curl -X POST https://personalfinancemanager-y991.onrender.com/api/transactions \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"amount":5000.00,"date":"2024-01-15","category":"Salary"}'

4. Get Transactions
curl -X GET https://personalfinancemanager-y991.onrender.com/api/transactions \
  -b cookies.txt

5. Get Categories
curl -X GET https://personalfinancemanager-y991.onrender.com/api/categories \
  -b cookies.txt

6. Get Monthly Report
curl -X GET https://personalfinancemanager-y991.onrender.com/api/reports/monthly/2024/1 \
  -b cookies.txt

🧪 Full Test Script

If test_all.sh is included in the repository:

chmod +x test_all.sh
./test_all.sh

Test Results
Total Tests: 12
Passed: 12
Failed: 0

Success Rate: 100%

🐳 Docker

The application can be built and run using Docker.

Dockerfile
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

Build Docker Image
docker build -t personal-finance-manager .

Run Docker Container
docker run -p 8080:8080 personal-finance-manager


The API will be available at:

http://localhost:8080/api

☁️ Deployment

The application is deployed using:

Docker
Render
Production API
https://personalfinancemanager-y991.onrender.com/api


The Docker image uses a multi-stage build:

Maven + Java 21 image for compiling the application.
Lightweight Java 21 JRE Alpine image for running the final JAR.

This keeps the runtime image smaller and separates the build environment from production.

🔒 Security & Data Isolation

The API uses Spring Security with session-based authentication.

Authenticated users can only access their own financial data.

This applies to:

Transactions
Categories
Savings goals
Reports

Users cannot access another user's transactions or financial information by simply changing an entity ID in an API request.

🏗️ Architecture

The application follows a layered architecture:

Client
  │
  ▼
Controller Layer
  │
  ▼
Service Layer
  │
  ▼
Repository Layer
  │
  ▼
H2 Database

Controller Layer

Handles HTTP requests and responses.

Service Layer

Contains business logic and user-specific data handling.

Repository Layer

Provides database access through Spring Data JPA.

DTO Layer

Separates API request/response objects from persistent entities.

Model Layer

Contains JPA entities representing application data.

📌 API Endpoint Summary
Method	Endpoint	Description
POST	/api/auth/register	Register a new user
POST	/api/auth/login	Login
POST	/api/auth/logout	Logout
POST	/api/transactions	Create transaction
GET	/api/transactions	Get transactions
PUT	/api/transactions/{id}	Update transaction
DELETE	/api/transactions/{id}	Delete transaction
GET	/api/categories	Get categories
POST	/api/categories	Create category
DELETE	/api/categories/{name}	Delete category
POST	/api/goals	Create savings goal
GET	/api/goals	Get savings goals
GET	/api/goals/{id}	Get specific goal
PUT	/api/goals/{id}	Update savings goal
DELETE	/api/goals/{id}	Delete savings goal
GET	/api/reports/monthly/{year}/{month}	Monthly report
GET	/api/reports/yearly/{year}	Yearly report
🚧 Future Improvements

Potential future enhancements include:

 PostgreSQL/MySQL persistent database
 JWT authentication option
 Swagger/OpenAPI documentation
 Automated integration tests
 Pagination for transactions
 Budget management
 Recurring transactions
 Expense analytics and charts
 Email notifications for savings goals
 Password reset functionality
 Production database migrations with Flyway/Liquibase
 CI/CD using GitHub Actions
🤝 Contributing

Contributions are welcome.

Fork the repository.
Create a feature branch.
git checkout -b feature/your-feature

Commit your changes.
git commit -m "Add your feature"

Push the branch.
git push origin feature/your-feature

Open a Pull Request.
📄 License

This project is available under the MIT License.

👨‍💻 Author

Shivatu

Built with ❤️ using Java 21 + Spring Boot.

If you find this project useful, consider giving the repository a ⭐.
