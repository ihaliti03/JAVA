This project is a RESTful API developed for a Distributed Systems course, enabling CRUD operations and relationships between `Students`, `Courses`, and `Enrollments`. Built using **Java (Jersey)**, **MySQL**, and **HikariCP**, the application is structured following clean architecture principles and is Docker-ready.

---
## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── io.distributedsystems/
│   │       ├── models/          # POJOs: Student, Course, Enrollment
│   │       ├── services/        # Interfaces for service layers
│   │       ├── servicesImpl/    # Implementations of service interfaces
│   │       └── resources/       # REST endpoints (JAX-RS)
├── resources/
│   └── application.properties
Dockerfile
```

---

## 🚀 How to Run the Project

### 🔧 Prerequisites

- Java 8+
    
- Maven 3.x
    
- MySQL server
    
- Docker (optional)
    

### 💻 Run Locally

1. **Create MySQL Database and tables based on POJOs**
2. **Configure** `**application.properties**`:
    
    ```
    db.url=jdbc:mysql://localhost:3306/distributed_api
    db.username=root
    db.password=your_password
    ```
    
3. **Build and Run**:
    
    ```
    mvn clean install
    mvn jetty:run
    ```
    

### 🐳 Run with Docker

1. **Build Docker Image**:
    
    ```
    docker build -t restfulHomework .
    ```
    
2. **Run the Container**:
    
    ```
    docker run -d -p 8080:8080 --env-file .env restfulHomework
    ```
    

---

## 🌐 API Overview

### 👤 Student Endpoints

|Method|Endpoint|Description|
|---|---|---|
|GET|`/students`|Get list of all students|
|GET|`/students/{id}`|Get a specific student|
|POST|`/students`|Create a new student|
|PUT|`/students/{id}`|Update a student|
|DELETE|`/students/{id}`|Delete a student|

#### 📥 Sample POST /students

```
{
  "name": "Alice Smith",
  "email": "alice@example.com"
}
```

#### 📤 Sample GET /students/1

```
{
  "id": 1,
  "name": "Alice Smith",
  "email": "alice@example.com"
}
```

### 📚 Course Endpoints

|   |   |   |
|---|---|---|
|Method|Endpoint|Description|
|GET|`/courses`|Get list of all courses|
|GET|`/courses/{id}`|Get a specific course|
|POST|`/courses`|Create a new course|
|PUT|`/courses/{id}`|Update a course|
|DELETE|`/courses/{id}`|Delete a course|

#### 📥 Sample POST /courses

```
{
  "title": "Distributed Systems",
  "credits": 6
}
```

### 📝 Enrollment Endpoints

|   |   |   |
|---|---|---|
|Method|Endpoint|Description|
|POST|`/students/{id}/courses/{cid}`|Enroll a student in a course|
|DELETE|`/students/{id}/courses/{cid}`|Remove a student from a course|
|GET|`/students/{id}/courses`|List all courses a student is enrolled in|
|GET|`/courses/{id}/students`|List all students enrolled in a course|

#### 📤 Sample GET /students/1/courses

```
[
  {
    "id": 2,
    "title": "Distributed Systems",
    "credits": 6
  }
]
```

---

## 🛠 Features

### ✅ Input Validation

- Validates required fields like `name`, `email`

### 🔁 Pagination Support

- `GET /students?page=1&size=10`    

### 🧯 Robust Error Handling

- Try-catch-finally used in service logic
    
- Resources (`ResultSet`, `PreparedStatement`, `Connection`) are closed in `finally`
    
### 🐳 Dockerized

- Production-ready Dockerfile to containerize the API
    

---

## 📚 Method Naming Summary

- `get()` — List all records (supports pagination for student)
    
- `getById()` — Fetch one by ID
    
- `create()` — Insert new record
    
- `update()` — Modify existing record
    
- `delete()` — Delete by ID
    
- `getStudentsEnrolledCourses()` — Courses by student
    
- `getCoursesEnrolledStudents()` — Students by course
    

---
## 🧪 Testing
- All endpoints are tested thoroughly with a variety of requests using Postman.