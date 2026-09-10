<div align="center">

# 💰 Personal Finance Manager API

### A secure, RESTful Personal Finance Management API built with Spring Boot 3.x and Java 21

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9%2B-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Deployed-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](#-license)

**[🌐 Live API](https://personalfinancemanager-y991.onrender.com/api)** · **[📚 API Docs](#-api-documentation)** · **[🚀 Getting Started](#-getting-started)** · **[🐳 Docker](#-docker)**

</div>

---

## 📖 Overview

The **Personal Finance Manager API** allows users to securely manage their income, expenses, categories, savings goals, and financial reports — with session-based authentication and strict user-level data isolation.

> ⚠️ **Live Demo Notice:** The hosted instance uses an in-memory H2 database. Data resets on every restart — this is a live demo, not a persistent production environment.

---

## ✨ Features

<table>
<tr>
<td width="50%" valign="top">

**🔐 Authentication & Security**
- User registration & session-based login/logout
- Spring Security integration
- Strict user-level data isolation

**💳 Transaction Management**
- Create, view, update, and delete transactions
- Filter by date, category, and transaction type

</td>
<td width="50%" valign="top">

**🏷️ Categories**
- 7 built-in default categories
- Custom user-defined categories

**🎯 Savings Goals**
- Create and manage financial goals
- Track progress toward target amounts & dates

**📊 Reports**
- Monthly financial summaries
- Yearly financial summaries

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security (session-based auth) |
| **Database** | H2 (in-memory) |
| **ORM** | Spring Data JPA / Hibernate |
| **Build Tool** | Maven |
| **Deployment** | Docker + Render |

---

## 🏗️ Architecture

The application follows a clean, layered architecture:

```
Client
  │
  ▼
Controller Layer   →  Handles HTTP requests & responses
  │
  ▼
Service Layer      →  Business logic & user-specific data handling
  │
  ▼
Repository Layer   →  Database access via Spring Data JPA
  │
  ▼
H2 Database
```

| Layer | Responsibility |
|---|---|
| **Controller** | Handles HTTP requests and responses |
| **Service** | Contains business logic and user-specific data handling |
| **Repository** | Provides database access through Spring Data JPA |
| **DTO** | Separates API request/response objects from persistent entities |
| **Model** | Contains JPA entities representing application data |

---

## 📁 Project Structure

```
syfe/
├── src/
│   └── main/
│       ├── java/com/syfe/pfm/
│       │   ├── SyfeApplication.java
│       │   ├── config/
│       │   │   └── SecurityConfig.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   ├── TransactionController.java
│       │   │   ├── CategoryController.java
│       │   │   ├── SavingsGoalController.java
│       │   │   └── ReportController.java
│       │   ├── dto/
│       │   │   ├── LoginRequest.java
│       │   │   ├── RegisterRequest.java
│       │   │   ├── TransactionRequest.java
│       │   │   ├── TransactionResponse.java
│       │   │   ├── SavingsGoalRequest.java
│       │   │   ├── SavingsGoalUpdateRequest.java
│       │   │   ├── SavingsGoalDTO.java
│       │   │   ├── ReportDTO.java
│       │   │   └── YearlyReportDTO.java
│       │   ├── exception/
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── model/
│       │   │   ├── User.java
│       │   │   ├── Category.java
│       │   │   ├── Transaction.java
│       │   │   └── SavingsGoal.java
│       │   ├── repository/
│       │   │   ├── UserRepository.java
│       │   │   ├── CategoryRepository.java
│       │   │   ├── TransactionRepository.java
│       │   │   └── SavingsGoalRepository.java
│       │   └── service/
│       │       ├── UserService.java
│       │       ├── TransactionService.java
│       │       ├── CategoryService.java
│       │       ├── SavingsGoalService.java
│       │       └── ReportService.java
│       └── resources/
│           ├── application.properties
│           └── data.sql
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- ☕ Java 21
- 📦 Maven 3.9+
- 🔧 Git
- 🐳 Docker *(optional, for containerized deployment)*

### 📥 Clone the Repository

```bash
git clone https://github.com/your-username/syfe.git
cd syfe
```

> Replace `your-username` with your GitHub username.

### 🔨 Build the Project

```bash
mvn clean install
```

### ▶️ Run the Application

```bash
mvn spring-boot:run
```

The API will be available at:

```
http://localhost:8080/api
```

### 🗄️ H2 Database Console

Open the H2 console at:

```
http://localhost:8080/h2-console
```

| Property | Value |
|---|---|
| **JDBC URL** | `jdbc:h2:mem:pfm` |
| **Username** | `sa` |
| **Password** | *(empty)* |

> ⚠️ Because H2 runs in-memory, all data is lost when the application restarts.

---

## 📚 API Documentation

**Base URL:** `/api`
**Deployed URL:** `https://personalfinancemanager-y991.onrender.com/api`

### 🔐 Authentication

<details>
<summary><strong>POST /api/auth/register</strong> — Register a new user</summary>

```json
{
  "username": "user@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890"
}
```
</details>

<details>
<summary><strong>POST /api/auth/login</strong> — Login</summary>

```json
{
  "username": "user@example.com",
  "password": "password123"
}
```

Uses session-based authentication. The session cookie returned on login must be included in subsequent authenticated requests.
</details>

<details>
<summary><strong>POST /api/auth/logout</strong> — Logout</summary>

No request body required.
</details>

### 💳 Transactions

<details>
<summary><strong>POST /api/transactions</strong> — Create a transaction</summary>

```json
{
  "amount": 5000.00,
  "date": "2024-01-15",
  "category": "Salary",
  "description": "January Salary"
}
```
</details>

<details>
<summary><strong>GET /api/transactions</strong> — Get transactions</summary>

Optional query parameters: `startDate`, `endDate`, `categoryId`, `type`

```
GET /api/transactions?startDate=2024-01-01&endDate=2024-01-31
```
</details>

<details>
<summary><strong>PUT /api/transactions/{id}</strong> — Update a transaction</summary>

```json
{
  "amount": 6000.00,
  "category": "Salary",
  "description": "Updated Salary"
}
```
</details>

<details>
<summary><strong>DELETE /api/transactions/{id}</strong> — Delete a transaction</summary>
</details>

### 🏷️ Categories

<details>
<summary><strong>GET /api/categories</strong> — Get all categories</summary>
</details>

<details>
<summary><strong>POST /api/categories</strong> — Create a category</summary>

```json
{
  "name": "Freelance",
  "type": "INCOME"
}
```
</details>

<details>
<summary><strong>DELETE /api/categories/{name}</strong> — Delete a category</summary>
</details>

### 🎯 Savings Goals

<details>
<summary><strong>POST /api/goals</strong> — Create a goal</summary>

```json
{
  "goalName": "Emergency Fund",
  "targetAmount": 50000.00,
  "targetDate": "2026-12-31",
  "startDate": "2024-01-01"
}
```
</details>

<details>
<summary><strong>GET /api/goals</strong> — Get all goals</summary>
</details>

<details>
<summary><strong>GET /api/goals/{id}</strong> — Get a specific goal</summary>
</details>

<details>
<summary><strong>PUT /api/goals/{id}</strong> — Update a goal</summary>

```json
{
  "targetAmount": 60000.00,
  "targetDate": "2026-06-30"
}
```
</details>

<details>
<summary><strong>DELETE /api/goals/{id}</strong> — Delete a goal</summary>
</details>

### 📊 Reports

<details>
<summary><strong>GET /api/reports/monthly/{year}/{month}</strong> — Monthly report</summary>

```
GET /api/reports/monthly/2024/1
```
</details>

<details>
<summary><strong>GET /api/reports/yearly/{year}</strong> — Yearly report</summary>

```
GET /api/reports/yearly/2024
```
</details>

### 📌 Endpoint Summary

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login |
| `POST` | `/api/auth/logout` | Logout |
| `POST` | `/api/transactions` | Create transaction |
| `GET` | `/api/transactions` | Get transactions |
| `PUT` | `/api/transactions/{id}` | Update transaction |
| `DELETE` | `/api/transactions/{id}` | Delete transaction |
| `GET` | `/api/categories` | Get categories |
| `POST` | `/api/categories` | Create category |
| `DELETE` | `/api/categories/{name}` | Delete category |
| `POST` | `/api/goals` | Create savings goal |
| `GET` | `/api/goals` | Get savings goals |
| `GET` | `/api/goals/{id}` | Get specific goal |
| `PUT` | `/api/goals/{id}` | Update savings goal |
| `DELETE` | `/api/goals/{id}` | Delete savings goal |
| `GET` | `/api/reports/monthly/{year}/{month}` | Monthly report |
| `GET` | `/api/reports/yearly/{year}` | Yearly report |

---

## 🧪 Testing

### Quick Test with cURL

**1. Register**
```bash
curl -X POST https://personalfinancemanager-y991.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test@example.com","password":"password123","fullName":"Test User","phoneNumber":"+1234567890"}'
```

**2. Login**
```bash
curl -X POST https://personalfinancemanager-y991.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test@example.com","password":"password123"}' \
  -c cookies.txt
```
> The `-c cookies.txt` flag stores the session cookie for subsequent requests.

**3. Create a Transaction**
```bash
curl -X POST https://personalfinancemanager-y991.onrender.com/api/transactions \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"amount":5000.00,"date":"2024-01-15","category":"Salary"}'
```

**4. Get Transactions**
```bash
curl -X GET https://personalfinancemanager-y991.onrender.com/api/transactions \
  -b cookies.txt
```

**5. Get Categories**
```bash
curl -X GET https://personalfinancemanager-y991.onrender.com/api/categories \
  -b cookies.txt
```

**6. Get Monthly Report**
```bash
curl -X GET https://personalfinancemanager-y991.onrender.com/api/reports/monthly/2024/1 \
  -b cookies.txt
```

### 🧪 Full Test Script

If `test_all.sh` is included in the repository:

```bash
chmod +x test_all.sh
./test_all.sh
```

<div align="center">

| Total Tests | Passed | Failed | Success Rate |
|:---:|:---:|:---:|:---:|
| **12** | ✅ **12** | ❌ **0** | 🟢 **100%** |

</div>

---

## 🐳 Docker

### Dockerfile

```dockerfile
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
```

### Build the Image

```bash
docker build -t personal-finance-manager .
```

### Run the Container

```bash
docker run -p 8080:8080 personal-finance-manager
```

The API will be available at:

```
http://localhost:8080/api
```

---

## ☁️ Deployment

The application is deployed using **Docker** + **Render**.

**Production API:** [`https://personalfinancemanager-y991.onrender.com/api`](https://personalfinancemanager-y991.onrender.com/api)

The Docker image uses a **multi-stage build**:

1. 🏗️ **Build stage** — Maven + Java 21 image compiles the application
2. 🪶 **Runtime stage** — Lightweight Java 21 JRE Alpine image runs the final JAR

This keeps the runtime image small and cleanly separates the build environment from production.

---

## 🔒 Security & Data Isolation

The API uses **Spring Security** with session-based authentication. Authenticated users can only access their own financial data — this applies to:

- ✅ Transactions
- ✅ Categories
- ✅ Savings goals
- ✅ Reports

> Users **cannot** access another user's data by simply changing an entity ID in an API request.
---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

1. **Fork** the repository
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m "Add your feature"
   ```
4. **Push the branch**
   ```bash
   git push origin feature/your-feature
   ```
5. **Open a Pull Request** 🎉

---

## 📄 License

This project is licensed under the **MIT License**.

---

<div align="center">

## 👨‍💻 Author

**Shivatu**

Built with ❤️ using Java 21 + Spring Boot

⭐ If you find this project useful, consider giving the repository a star!

</div>
