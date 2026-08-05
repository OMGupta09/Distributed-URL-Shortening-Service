🚀 Distributed URL Shortening Service

A production-inspired, scalable URL shortening platform built with Spring Boot, Redis, Kafka, MySQL, JWT Authentication, and Docker.



📖 Overview

This project is a backend-focused distributed URL shortening service that demonstrates modern Java backend engineering practices. It provides secure authentication, high-performance URL redirection, caching with Redis, asynchronous analytics using Kafka, and persistent storage in MySQL.

✨ Features

🔐 JWT Authentication & Authorization

👤 User Registration & Login

🔗 Generate Short URLs

✍️ Custom URL Aliases

⏰ URL Expiration

📊 Click Analytics

⚡ Redis Caching

📨 Kafka Event Streaming

🐳 Dockerized Development

🛡 Global Exception Handling

🏗 Clean Layered Architecture

🛠 Tech Stack

Category

Technologies

Language

Java 21

Framework

Spring Boot

Security

Spring Security, JWT

Database

MySQL

Cache

Redis

Messaging

Apache Kafka

Build Tool

Maven

Containerization

Docker & Docker Compose

🏛 Architecture

                Client
                   │
                   ▼
        ┌────────────────────┐
        │  Spring Boot API   │
        └───────┬────────────┘
                │
     ┌──────────┼──────────┐
     ▼          ▼          ▼
  MySQL      Redis      Kafka
(Database)   (Cache)   (Events)
                             │
                             ▼
                     Analytics Consumer

📂 Project Structure

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

🔄 Typical Request Flow

Client
   │
POST /shorten
   │
JWT Authentication
   │
Validation
   │
Generate Alias
   │
Check Redis
   │
Persist in MySQL
   │
Publish Analytics Event
   │
Return Short URL

🚀 Getting Started

Prerequisites

Java 21

Maven

Docker Desktop

MySQL

Redis

Apache Kafka

Clone

git clone <repository-url>
cd Distributed-URL-Shortening-Service

Run

docker compose up -d
mvn spring-boot:run

📡 Example API Endpoints

Method

Endpoint

Description

POST

/auth/register

Register user

POST

/auth/login

Login

POST

/urls

Create short URL

GET

/{shortCode}

Redirect

GET

/urls/{id}/analytics

URL analytics

PUT

/urls/{id}

Update URL

DELETE

/urls/{id}

Delete URL

📈 Future Improvements

Rate Limiting

Kubernetes Deployment

CI/CD Pipeline

Prometheus & Grafana

Distributed Tracing

AWS Deployment

Custom Domains

Bulk URL Import

👨‍💻 Author

OM GUPTA

Backend Java Developer passionate about building scalable, production-ready backend systems.

⭐ Support

If you found this project useful, consider giving it a ⭐ Star on GitHub.
