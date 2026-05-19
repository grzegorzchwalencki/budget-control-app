## Budget Control App

$${\color{red}\Large\textbf{UNDER CONSTRUCTION}}$$

**Personal Budget Management REST API** — a deliberately over-engineered Spring Boot project built to strengthen my backend architecture, testing, and DevOps skills.

### Project Description

I developed a full-featured backend application for tracking personal expenses, managing budget categories, and generating financial reports. Instead of a simple CRUD application, I focused on creating a clean, maintainable, and production-ready solution using modern tools and best practices.

### Tech Stack

- **Language**: Java 25
- **Framework**: Spring Boot 4.0.2
- **Database**: PostgreSQL with **Flyway** migrations
- **Build Tool**: Gradle 9+
- **Key Technologies**:
  - Spring Data JPA, Validation, Springdoc OpenAPI
  - Lombok
  - Docker + Docker Compose
  - Testing: Spock (Groovy), RestAssured, Testcontainers, ArchUnit, DataFaker
  - JaCoCo for test coverage

### Architecture & Design Decisions

I designed the application using **Clean Architecture** principles with clear separation of concerns:

- Rich domain model with separate packages for `Expense`, `Category`, and `User`
- Immutable DTOs implemented as Java **Records**
- UUID-based entity identifiers
- Dedicated reporting module
- `ServicesOrchestrator` for coordinating complex operations
- Architecture rules enforced with **ArchUnit**

I paid special attention to proper domain modeling, entity relationships, and maintainable package structure.

### Testing Strategy

- **Unit tests** using Spock + Groovy (BDD style)
- **Functional/API tests** with RestAssured
- **Integration tests** with Testcontainers (real PostgreSQL)
- **Architecture tests** with ArchUnit
- Separate `unit` and `functional` test source sets
- High test coverage with JaCoCo reports

### DevOps & Development Experience

- Full **Docker** support with multi-container `docker-compose.yml`
- **Flyway** database versioning and migrations
- **GitHub Actions** CI pipeline
- Secure Dockerfile configuration
- OpenAPI/Swagger documentation

### Development Process

Throughout the project, I consistently:
- Performed multiple major refactors (Java version upgrade, DTO migration to Records, database switch to PostgreSQL, etc.)
- Improved architecture and code quality with every merge
- Maintained clean commit history and well-described Pull Requests
- Upgraded dependencies to use the latest stable versions

---
## Prerequisites

* Installed the latest version of Docker Compose
## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/grzegorzchwalencki/budget-control-app.git
cd budget-control-app
```

### 2. Build and Run the Application
The command below will build and start two containers (PostgreSQL database + Spring Boot application):
```bash
docker compose up
```

