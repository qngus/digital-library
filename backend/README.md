# 📚 Book Service

Backend du projet **Library** développé avec **Spring Boot 3.5.5** et **Java 21**.  
Ce service gère les livres (CRUD, recherche par titre/auteur, pagination, validation).  
Il utilise **PostgreSQL** comme base de données et **Flyway** pour la gestion des migrations.

---

## 🚀 Technologies

- [Spring Boot](https://spring.io/projects/spring-boot) `3.5.5`
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL](https://www.postgresql.org/)
- [Flyway](https://flywaydb.org/) (migrations de DB)
- [Lombok](https://projectlombok.org/)
- [MapStruct](https://mapstruct.org/) (mapping DTO ↔ entity)
- [Testcontainers](https://www.testcontainers.org/) (tests d’intégration)
- [SpringDoc OpenAPI](`springdoc-openapi-starter-webmvc-ui`)

---

## 📦 Prérequis

- **Java 21**
- **Maven 3.9+**
- **Docker**

---

## ⚙️ Lancer en local

### 1. Lancer PostgreSQL en local (via Docker)
```bash
docker run --name postgres-books \
  -e POSTGRES_DB=books \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=passw0rd \
  -p 5432:5432 \
  -d postgres:16
```

### 2. Lancer Ollama en local (via Docker)
```bash
docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

#### Telecharger le modele mistral
```bash
docker exec -it ollama ollama pull llama3.2:1b
```

### 3. Lancer le backend
```bash
mvn spring-boot:run
```

L’API sera disponible sur http://localhost:8080

La documentation sera disponible sur http://localhost:8080/swagger-ui/index.html#/

### 4.🐳 Docker

Builder l’image

```bash
docker build -t book-service .
```

Lancer le conteneur

```bash
docker run -p 8080:8080 --name book-service book-service
```

### 5.🧪 Tests

Exécuter tous les tests :

```bash
mvn test
```

Les tests d’intégration utilisent Testcontainers pour PostgreSQL.