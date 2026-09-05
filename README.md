# 💰 Personal Finance Manager API

A comprehensive RESTful API for managing personal finances built with **Spring Boot 3.x** and **Java 21**. This API provides secure user authentication, transaction management, savings goals tracking, and financial reporting.

---

## 🚀 Live Demo

| Service | URL |
|---------|-----|
| **API Base URL** | [https://personalfinancemanager-y991.onrender.com/api](https://personalfinancemanager-y991.onrender.com/api) |

---

## 📋 Features

### 🔐 User Management & Authentication
- ✅ User Registration with email, password, full name, phone number
- ✅ Session-based authentication with secure cookies
- ✅ Login with username/password
- ✅ Logout with session invalidation
- ✅ Complete data isolation between users

### 💰 Transaction Management
- ✅ Create income and expense transactions
- ✅ View all transactions sorted by newest first
- ✅ Filter transactions by date range, category, and type
- ✅ Update transaction details (amount, description, category)
- ✅ Delete transactions (won't reflect in reports)

### 📂 Category Management
- ✅ 7 default categories (Salary, Food, Rent, Transportation, Entertainment, Healthcare, Utilities)
- ✅ Create custom categories (INCOME/EXPENSE)
- ✅ Unique category names per user
- ✅ Delete custom categories (if not used in transactions)

### 🎯 Savings Goals
- ✅ Create savings goals with target amount and target date
- ✅ Automatic start date (defaults to creation date)
- ✅ Real-time progress tracking (Income - Expenses since start date)
- ✅ Progress percentage calculation
- ✅ Update and delete goals

### 📊 Reports & Analytics
- ✅ Monthly reports (income by category, expenses by category, net savings)
- ✅ Yearly reports with comprehensive financial overview

---

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Security | Spring Security (Session-based) |
| Database | H2 Database (In-memory) |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |
| Validation | Jakarta Validation |
| Testing | JUnit 5, Spring Boot Test |

---

## 📦 Installation & Setup

### Prerequisites
- Java 21 or higher
- Maven 3.8+
- Git

### Step 1: Clone the Repository
```bash
git clone https://github.com/your-username/syfe.git
cd syfe
