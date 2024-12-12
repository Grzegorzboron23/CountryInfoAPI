# **Country Info API**

A Spring Boot application that provides RESTful APIs to fetch and manage country information using the ISO 3166-1 alpha-3 code. The application interacts with a free REST Countries API and stores the data in an H2 database.

## **Features**
- Fetch country information by alpha-3 code.
- Store country details in an H2 database.
- Paginate through all stored country records.
- Exception handling for invalid requests and unexpected errors.
- Logging using Log4j2.

## **Endpoints**
### **1. Fetch Country by Alpha-3 Code**
- **GET** `/api/country/{alpha3Code}`
  - Fetch details of a country by its ISO alpha-3 code.
  - Example: `/api/country/USA`

### **2. Get All Countries**
- **GET** `/api/country`
  - Fetch all stored countries with pagination.
  - Query Params:
    - `page` (default: 0)
    - `size` (default: 10)
  - Example: `/api/country?page=1&size=5`

## **Technologies Used**
- **Java 21**
- **Spring Boot 3.4.0**
- **H2 Database** (In-memory)
- **WebClient** (for external API calls)
- **Log4j2** (for logging)
- **JUnit 5** and **Mockito** (for testing)

## **Setup and Run**

### **1. Prerequisites**
- Java 21 or higher
- Maven 3.8.7 or higher

### **2. Clone the Repository**
```bash
git clone https://github.com/<your-username>/CountryInfoAPI.git
cd CountryInfoAPI
