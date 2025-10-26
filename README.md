# Catalog Service: Modular Microservice Design

## 📌 Project Context

This microservice provides a foundational, RESTful API layer for product and category catalog management. It is designed following modern Spring Boot microservice practices, emphasizing clean architecture, automated schema management, and robust data integrity, all while being immediately runnable via Docker Compose.

---

## 💡 Implementation Decisions

We utilize a focused set of tools to ensure agility, stability, and maintainability:

| Component | Choice                                                                                    |
| :--- |:------------------------------------------------------------------------------------------|
| **Framework** | **Spring Boot 3.x**                                                                       |
| **Database** | **MySQL**                                                                                 |
| **Migrations** | **Flyway**                                                                                |
| **API Documentation** | **Springdoc OpenAPI (Swagger)**                                                           |
| **Data Loading** | Custom **XLSX Data Loader**                                                               |
| **Containerization** | **Docker & Docker Compose** for local development parity and dependency orchestration.    |

---

## 🧪 Documentation and Access

After the application and database containers are healthy, access the interactive API documentation (Swagger UI) here:
http://localhost:8080/swagger-ui/index.html

## 🧪 Setup and Execution
Prerequisites
You'll need Gradle (or IntelliJ) and Docker/Docker Compose installed.

Execution Steps: This approach automatically builds the JAR file and launches the complete environment.
1. Build the project using Gradle (or your IntelliJ build option):
   **./gradlew clean build**
2. Build and Run the containers:
   **docker compose up --build -d**
3. The service (catalog-service-container) will be accessible at http://localhost:8080.


🐳 Docker Compose Environment Details

| Service Name | Container Name  | Role  | Access  |
| :--- |:----------------|---|---| 
|   app   |        catalog-service-container         | Runs the Spring Boot application. Reads environment variables for database connection.  |  http://localhost:8080 |
|    mysql-db     |       catalog-service-mysql-db                                   |          The MySQL 8.0 database instance. It uses a persistent volume (catalog_mysql_data) for data.                                                                               |        Exposed on local port 3306.                |


🔑 Connecting to the Local Database

When running via docker compose up, the database is mapped to your local machine's port 3306. You can connect using any SQL client (DBeaver, MySQL Workbench, etc.) with the credentials defined in the docker-compose.yml:

Parameter** | **Value** | 
 | ----- | ----- | 
| Host | `localhost` (or `127.0.0.1`) | 
| Port | `3306` | 
| Database Name | `catalogservice` | 
| User | `cataloguser` | 
| Password | `mycatalog123`
