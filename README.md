# Spring Boot Microservices Project:

This is my microservices project built using Spring Boot. I created it to understand how different services communicate with each other and how security works in a microservices architecture.
The project has separate services for Users, Hotels, and Ratings. It also includes an API Gateway, Eureka Server, and Config Server.

## Services:

- **User Service** – Handles user-related operations.
- **Hotel Service** – Handles hotel-related operations.
- **Rating Service** – Manages ratings.
- **API Gateway** – Acts as the entry point for client requests and routes them to the required services.
- **Service Registry (Eureka)** – Keeps track of all registered services.
- **Config Server** – Manages configuration for different services.

## Technologies Used:

- Java
- Spring Boot
- Spring Cloud
- Spring Cloud Gateway
- Netflix Eureka
- Spring Cloud Config
- Spring Security
- Okta (OAuth 2.0)
- JWT
- MySQL
- Maven
- Resilience4j
- RestTemplate

## Security:

I have used Okta for authentication and authorization. The services are secured using OAuth 2.0 and JWT-based token validation.

## How to Run:

1. Clone this repository.
2. Open the individual services in your IDE.
3. Configure the required database and Okta credentials in the environment variables.
4. Start the Config Server and Eureka Server.
5. Start the User, Hotel, and Rating services.
6. Finally, start the API Gateway.

## Note: This project is still a learning project, and I will keep adding new features and improving it as I learn more about microservices.
