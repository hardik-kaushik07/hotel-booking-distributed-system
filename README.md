# Spring Boot Microservices Project

A **Spring Boot Microservices-based application** built to understand and implement real-world distributed system concepts. This project focuses on breaking down a monolithic application into independent services and connecting them using Spring Cloud, REST APIs, and event-driven communication with Apache Kafka.

It includes service discovery, centralized configuration, API gateway security with Okta, asynchronous communication, distributed tracing, and Docker-based deployment.

## Architecture & Components

The project consists of the following microservices and infrastructure components:

### Infrastructure Services

- **Eureka Server:** Maintains a registry of available microservices and enables service discovery without hardcoding service URLs.
- **Config Server:** Provides centralized configuration management using a remote GitHub repository.
- **API Gateway:** Acts as the single entry point for client requests. Handles request routing, OAuth2 authentication through Okta, and JWT token relay to downstream services.

### Business Microservices

- **User Service:** Manages user information and profiles.
- **Hotel Service:** Handles hotel-related information and room details.
- **Rating Service:** Manages hotel ratings and customer reviews.
- **Booking Service:** Creates bookings, maintains booking records, and tracks booking status.
- **Payment Service:** Processes payment requests received through Kafka events.
- **Notification Service:** Consumes Kafka events and handles customer notifications.

### Supporting Components

- **Apache Kafka (KRaft Mode):** Enables asynchronous, event-driven communication between Booking, Payment, and Notification services.
- **MySQL 8:** Stores persistent data for the individual microservices.
- **OpenZipkin:** Provides distributed tracing to monitor requests across multiple services.
- **Kafka UI:** Provides a visual interface to monitor Kafka brokers, topics, and messages.

## How the Application Works

The booking process demonstrates how different microservices communicate and coordinate using both synchronous REST calls and asynchronous Kafka messaging.

1. **Authentication:** The client accesses the API Gateway and authenticates through Okta OAuth2. After successful authentication, an access token is used for subsequent API requests.

2. **Booking Creation:** The client sends a `POST /bookings/create` request with a valid Bearer token. The API Gateway validates the request and routes it to the Booking Service.

3. **Booking Event:** The Booking Service saves the booking in MySQL with an initial status of `PENDING` and publishes a booking event to the `booking-events` Kafka topic.

4. **Payment Processing:** The Payment Service consumes the booking event, processes the payment, and publishes the payment result to the `payment-events` topic.

5. **Booking Status Update:** The Booking Service consumes the payment event, identifies the corresponding booking, and updates its status to `CONFIRMED` after successful payment and `FAILED` on unsuccessful payment.

6. **Notifications:** The Notification Service consumes the relevant payment events and handles customer notifications.

7. **Distributed Tracing:** Micrometer Tracing and OpenZipkin help track requests and service interactions across the application, making it easier to identify failures and monitor execution flow.

## Tech Stack

| Category | Technologies |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3, Spring Cloud |
| API Gateway | Spring Cloud Gateway |
| Service Discovery | Netflix Eureka |
| Configuration | Spring Cloud Config Server |
| Security | Spring Security, Okta OAuth2, JWT |
| Messaging | Apache Kafka (KRaft Mode) |
| Database | MySQL 8 |
| ORM | Spring Data JPA, Hibernate |
| Inter-service Communication | OpenFeign, RestTemplate, REST APIs |
| Observability | Micrometer Tracing, OpenZipkin |
| Containerization | Docker, Docker Compose |
| Kafka Monitoring | Kafka UI |

## Running the Project with Docker Compose

All services and supporting infrastructure are containerized, so the complete application can be started using Docker Compose instead of running each service individually through an IDE.

### Prerequisites

- Java 17
- Docker Desktop
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/hardik-kaushik07/spring-boot-microservices-project.git

cd spring-boot-microservices-project
```

### 2. Configure Environment Variables

Configure the required environment variables for database credentials, Okta configuration, and other service-specific settings.

Make sure the required environment variables are available before starting the containers.

### 3. Start the Application

```bash
docker compose up --build
```

To run the containers in detached mode:

```bash
docker compose up --build -d
```

### 4. Stop the Application

```bash
docker compose down
```

## Key Concepts Implemented

Through this project, I worked with several important concepts used in distributed backend systems:

- Microservices architecture and service discovery.
- Centralized configuration management.
- API Gateway and request routing.
- OAuth2 authentication and JWT-based resource server security.
- Synchronous communication using REST APIs.
- Asynchronous communication using Apache Kafka.
- Event-driven architecture and consumer groups.
- Distributed tracing using Micrometer and Zipkin.
- Fault tolerance and resilience using Resilience4j.
- Containerization and multi-service deployment using Docker Compose.

## Project Repository

**GitHub:** [Spring Boot Microservices Project](https://github.com/hardik-kaushik07/spring-boot-microservices-project)

This project is an ongoing learning experience focused on understanding how independent backend services communicate, handle failures, process events, and work together as a distributed application.
