# Spring Boot Testing Demo

A comprehensive demonstration of testing strategies for Spring Boot 3.2.5 applications, showcasing unit tests, integration tests, and best practices for testing all layers of a modern Spring Boot application.

📘 Blog Post: [Testing a Spring Boot 3.2.5 App from Top to Bottom: MySQL, Docker, Unit Tests, Integration Tests](https://jarmx.blogspot.com/2024/06/testing-spring-boot-325-app-from-top-to.html)

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Contributing](#contributing)
- [Additional Resources](#additional-resources)

## 🚀 Overview

This project demonstrates comprehensive testing strategies for a Spring Boot 3.2.5 application with MySQL database integration. It covers testing at different layers including repository, service, and controller layers, along with integration testing.

The application implements a complete Employee Management System with CRUD operations, serving as a practical example for testing Spring Boot applications from top to bottom.

## ✨ Features

- **Complete CRUD Operations** for Employee management
- **Multi-layer Architecture** (Controller → Service → Repository → Database)
- **Comprehensive Testing Suite**:
  - Unit Tests for Repository layer
  - Unit Tests for Service layer
  - Unit Tests for Controller layer
  - Integration Tests
- **Custom Query Methods** with JPQL and Native SQL
- **Docker Compose** setup for MySQL database
- **Exception Handling** with custom exceptions
- **RESTful API** endpoints

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **MySQL 8.0**
- **Docker & Docker Compose**
- **JUnit 5**
- **Mockito**
- **AssertJ**
- **Lombok**
- **Maven**

## 📁 Project Structure

```
demo-testing/
├── src/
│   ├── main/
│   │   └── java/com/henry/demotesting/
│   │       ├── controller/
│   │       │   └── EmployeeController.java
│   │       ├── service/
│   │       │   ├── EmployeeService.java
│   │       │   └── impl/EmployeeServiceImpl.java
│   │       ├── repository/
│   │       │   └── EmployeeRepository.java
│   │       ├── model/
│   │       │   └── Employee.java
│   │       └── exception/
│   │           └── ResourceNotFoundException.java
│   └── test/
│       └── java/com/henry/demotesting/
│           ├── repository/
│           │   └── EmployeeRepositoryTests.java
│           ├── service/
│           │   └── EmployeeServiceTests.java
│           ├── controller/
│           │   └── EmployeeControllerTests.java
│           └── integration/
│               └── EmployeeControllerITests.java
├── docker-compose-mysql.yml
├── pom.xml
└── README.md
```

## 🔧 Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose
- Git

## 🚀 Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/HenryXiloj/demo-testing-spring-boot-3.2.5-java21.git
   cd demo-testing-spring-boot-3.2.5-java21
   ```

2. **Start MySQL database with Docker:**
   ```bash
   docker-compose -f docker-compose-mysql.yml up -d
   ```

3. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## 🏃‍♂️ Running the Application

1. **Start the Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Access the application:**
   - Base URL: `http://localhost:8080`
   - API Base Path: `http://localhost:8080/api/employees`

## 🧪 Testing

This project includes comprehensive testing at multiple levels:

### Running All Tests
```bash
mvn test
```

### Test Categories

#### 1. Repository Layer Tests (`EmployeeRepositoryTests`)
- **Purpose**: Test data access layer with `@DataJpaTest`
- **Coverage**: 
  - Basic CRUD operations
  - Custom query methods (JPQL and Native SQL)
  - Query parameter binding (indexed and named parameters)

#### 2. Service Layer Tests (`EmployeeServiceTests`)
- **Purpose**: Test business logic with mocked dependencies
- **Coverage**:
  - Employee creation and validation
  - Business rule enforcement
  - Exception handling
  - CRUD operations

#### 3. Controller Layer Tests (`EmployeeControllerTests`)
- **Purpose**: Test REST API endpoints with `@WebMvcTest`
- **Coverage**:
  - HTTP request/response handling
  - JSON serialization/deserialization
  - Status code validation
  - Error scenarios

#### 4. Integration Tests (`EmployeeControllerITests`)
- **Purpose**: Test complete application flow
- **Coverage**:
  - End-to-end API testing
  - Database integration
  - Full Spring context loading

### Test Execution Examples

```bash
# Run only unit tests
mvn test -Dtest="*Tests"

# Run only integration tests
mvn test -Dtest="*ITests"

# Run tests with coverage
mvn test jacoco:report
```

## 🔗 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/employees` | Create a new employee |
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

### Example API Usage

**Create Employee:**
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
  }'
```

**Get All Employees:**
```bash
curl http://localhost:8080/api/employees
```

## 🗄️ Database Configuration

The application uses MySQL with the following configuration:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test_db?allowPublicKeyRetrieval=true
    username: test
    password: test_pass
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

**Database Schema:**
```sql
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📚 Additional Resources

### Detailed Tutorial
For a comprehensive step-by-step guide, visit the detailed blog post:
[Testing a Spring Boot 3.2.5 App from Top to Bottom: MySQL, Docker, Unit Tests, Integration Tests](https://jarmx.blogspot.com/2024/06/testing-spring-boot-325-app-from-top-to.html)

### Key Learning Points
- **Repository Testing**: Learn to test JPA repositories with `@DataJpaTest`
- **Service Testing**: Master unit testing with Mockito
- **Controller Testing**: Understand `@WebMvcTest` for REST API testing
- **Integration Testing**: Implement full-stack testing with `@SpringBootTest`
- **Docker Integration**: Use Docker Compose for database setup
- **Test Data Management**: Handle test data lifecycle and cleanup

### Testing Best Practices Demonstrated
- **Arrange-Act-Assert (AAA)** pattern
- **Given-When-Then** BDD style
- **Mocking vs Integration** testing strategies
- **Test isolation** and cleanup
- **Comprehensive error scenario** testing
