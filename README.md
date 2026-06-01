# Smart Task Management System

A production-style REST API built with Spring Boot, featuring JWT authentication,
task management, team collaboration, and dashboard analytics.

## Tech Stack
- Java 17, Spring Boot 3.2
- Spring Security + JWT
- MySQL + JPA/Hibernate
- Maven, Lombok

## Features
- JWT Authentication (Register/Login)
- Task CRUD with Priority, Status, Deadlines
- Team Collaboration (Task Assignment)
- Dashboard Analytics (Completion Rate, Overdue, Priority Breakdown)
- Global Exception Handling
- Swagger UI Documentation

## Quick Start

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### Setup
```bash
# Clone
git clone https://github.com/yourusername/smart-task-manager.git

# Configure DB in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# Run
mvn spring-boot:run
```

### API Documentation
Visit `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/auth/register | No | Register user |
| POST | /api/auth/login | No | Login, get JWT |
| GET | /api/tasks | Yes | Get all tasks |
| POST | /api/tasks | Yes | Create task |
| PUT | /api/tasks/{id} | Yes | Update task |
| DELETE | /api/tasks/{id} | Yes | Delete task |
| PUT | /api/tasks/{id}/assign | Yes | Assign to user |
| GET | /api/tasks/assigned-to-me | Yes | My assignments |
| GET | /api/dashboard | Yes | Analytics |

## Architecture
```
controller/   → REST endpoints
service/      → Business logic (interface + impl)
repository/   → JPA data access
entity/       → JPA entities
dto/          → Request/Response objects
security/     → JWT filter and utils
exception/    → Global exception handling
config/       → Security and OpenAPI config
```