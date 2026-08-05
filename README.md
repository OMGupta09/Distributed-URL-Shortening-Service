# 🚀 Distributed URL Shortening Service

> A production-inspired, scalable URL shortening platform built with **Spring Boot**, **Redis**, **Kafka**, **MySQL**, **JWT Authentication**, and **Docker**.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![Kafka](https://img.shields.io/badge/Apache_Kafka-Event_Streaming-black)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 📖 Overview

This project is a **production-inspired URL shortening platform** built using modern backend technologies. It provides secure authentication, fast URL redirection, Redis-powered caching, Kafka-based event streaming for analytics, and persistent storage using MySQL.

Designed with scalability and maintainability in mind, the application demonstrates industry-standard backend architecture, clean code practices, and asynchronous processing.

---

## ✨ Features

- 🔐 JWT Authentication & Authorization
- 👤 User Registration & Login
- 🔗 URL Shortening
- ✍️ Custom Short Aliases
- ⏰ URL Expiration Support
- 📊 Click Analytics
- ⚡ Redis Caching
- 📨 Kafka Event Streaming
- 🐳 Dockerized Deployment
- 🛡 Global Exception Handling
- 🏗 Layered Architecture
- 📦 RESTful APIs

---

## 🛠 Tech Stack

| Category | Technologies |
|----------|--------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot |
| **Security** | Spring Security, JWT |
| **Database** | MySQL |
| **Cache** | Redis |
| **Messaging** | Apache Kafka |
| **Build Tool** | Maven |
| **Containerization** | Docker & Docker Compose |

---

## 🏛 System Architecture

```text
                      Client
                         │
                         ▼
                Spring Boot REST API
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
     MySQL            Redis Cache      Kafka
 (Persistent DB)   (Fast Retrieval) (Event Queue)
                                           │
                                           ▼
                                  Analytics Consumer
```

---

## 📂 Project Structure

```text
src
├── config
├── controller
├── dto
├── entity
├── exception
├── kafka
├── mapper
├── repository
├── security
├── service
└── util
```

---

## 🔄 Request Flow

```text
Client
   │
   ▼
POST /urls
   │
JWT Authentication
   │
Request Validation
   │
Generate / Validate Alias
   │
Check Redis Cache
   │
Persist URL in MySQL
   │
Publish Analytics Event to Kafka
   │
Return Short URL
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven
- Docker Desktop
- MySQL
- Redis
- Apache Kafka

### Clone the Repository

```bash
git clone <repository-url>
cd Distributed-URL-Shortening-Service
```

### Run the Application

```bash
docker compose up -d
mvn spring-boot:run
```

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|:------:|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Authenticate user |
| POST | `/urls` | Create a short URL |
| GET | `/{shortCode}` | Redirect to original URL |
| GET | `/urls/{id}/analytics` | Retrieve analytics |
| PUT | `/urls/{id}` | Update URL |
| DELETE | `/urls/{id}` | Delete URL |

---

## 📈 Future Improvements

- 🚦 Rate Limiting
- ☸ Kubernetes Deployment
- 🔄 CI/CD Pipeline
- 📊 Prometheus & Grafana Monitoring
- 🔍 Distributed Tracing
- ☁ AWS Deployment
- 🌐 Custom Domains
- 📦 Bulk URL Import

---

## 👨‍💻 Author

**OM GUPTA**

Backend Java Developer passionate about building scalable, secure, and production-ready backend systems.

---

## ⭐ Support

If you found this project useful, consider giving it a **⭐ Star** on GitHub.
