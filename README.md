# Medical Information System (MIS)

## Overview
The Medical Information System (MIS) is a microservices-based application designed to manage patient data, medical records, appointment scheduling, notifications, and analytics. The system follows enterprise-level practices and integrates with the HL7 FHIR standard for interoperability with external healthcare systems.

## Architecture
MIS follows a microservices architecture, with each service handling a specific domain. Communication is managed via REST API and Kafka for asynchronous messaging.

### Microservices
- **Patient Service** - Manages patient records (creation, update, search)
- **Appointments Service** - Handles appointment scheduling and management
- **Medical Records Service** - Stores and processes medical records
- **Notifications Service** - Sends notifications via email, SMS, or Telegram
- **FHIR Service** - Integrates with HL7/FHIR-compatible systems
- **Analytics Service** - Provides data analytics and reporting

## Technologies Used
- **Backend:** Java 17+, Spring Boot, Spring Security, Spring Cloud
- **Messaging:** Apache Kafka
- **Database:** PostgreSQL/MySQL
- **Security:** JWT authentication
- **API Documentation:** Swagger/OpenAPI
- **Containerization:** Docker
- **Testing:** JUnit, Mockito

## Installation & Setup
```sh
# Clone the repository
git clone https://github.com/akrecev/medicalinfo.git
cd medicalinfo
```
```sh
# Build and run the application using Docker Compose
docker-compose up --build
```

## API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html

## Contribution
Contributions are welcome! Feel free to submit issues and pull requests.

## License
This project is licensed under the MIT License.