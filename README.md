# Catalog Service: Modular Microservice Design

## 📌 Project Context

This microservice provides a foundational, RESTful API layer for product and category catalog management. It is designed following modern Spring Boot microservice practices, emphasizing clean architecture, automated schema management, and robust data integrity, all while being immediately runnable via Docker Compose.

---

## 💡 Implementation Decisions

We utilize a focused set of tools to ensure agility, stability, and maintainability:

| Component | Choice & Rationale                                                                           |
| :--- |:---------------------------------------------------------------------------------------------|
| **Framework** | **Spring Boot 3.x**                                                                          |
| **Database** | **MySQL**                                                                                    |
| **Migrations** | **Flyway**                                                                                   |
| **API Documentation** | **Springdoc OpenAPI (Swagger)**                      |
| **Data Loading** | Custom **XLSX Data Loader**           |
| **Containerization** | **Docker & Docker Compose** for local development parity and dependency orchestration.       |

---

## 🧪 Documentation and Access

After the application and database containers are healthy, access the interactive API documentation (Swagger UI) here:
http://localhost:8080/swagger-ui/index.html