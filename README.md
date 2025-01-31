# DailyWeather API

## Overview

DailyWeather is a RESTful API that provides weather information for cities worldwide. It integrates with a third-party weather API to fetch real-time weather data and stores it in a database for quick access. The API supports user authentication, data retrieval, and automatic weather updates.

## Features

- **User Authentication**: Secure login and token-based authentication.
- **Weather Data Fetching**: Retrieve weather information for a specific city.
- **Bulk Data Retrieval**: Fetch weather details for multiple cities at once.
- **Scheduled Updates**: Automatically updates weather data daily at midnight.
- **User Management**: Register, update, and delete user accounts.

## Technologies Used

- **Spring Boot** - Backend framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database management
- **MySQL/PostgreSQL** - Database storage
- **Feign Client** - HTTP client for third-party API integration
- **JWT (JSON Web Token)** - Secure authentication
- **Lombok** - Reduces boilerplate code
- **Scheduler** - Handles automatic weather updates

## Installation & Setup

### Prerequisites

- JDK 17+
- Maven 3.6+
- MySQL/PostgreSQL database

### Steps to Run Locally

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/dailyweather.git
   cd dailyweather
   ```
2. Configure the database in `application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/dailyweather
       username: root
       password: yourpassword
   ```
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

- **Login**
    - **Endpoint:** `POST /api/auth/login`
    - **Request Body:**
      ```json
      {
        "username": "user1",
        "password": "password123"
      }
      ```
    - **Response:**
      ```json
      {
        "accessToken": "your-access-token",
        "refreshToken": "your-refresh-token"
      }
      ```

- **Refresh Token**
    - **Endpoint:** `POST /api/auth/refresh`
    - **Headers:**
      ```
      Authorization: Bearer your-refresh-token
      ```
    - **Response:**
      ```json
      {
        "accessToken": "new-access-token",
        "refreshToken": "new-refresh-token"
      }
      ```

### User Management

- **Register a User**
    - **Endpoint:** `POST /api/users/register`
    - **Request Body:**
      ```json
      {
        "username": "user1",
        "password": "password123"
      }
      ```
    - **Response:**
      ```json
      {
        "id": "user-id",
        "username": "user1"
      }
      ```

- **Update User**
    - **Endpoint:** `PUT /api/users/{userId}`
    - **Request Body:**
      ```json
      {
        "username": "updatedUser"
      }
      ```
    - **Response:**
      ```json
      {
        "id": "user-id",
        "username": "updatedUser"
      }
      ```

- **Delete User**
    - **Endpoint:** `DELETE /api/users/{userId}`
    - **Response:**
      ```json
      {
        "message": "User deleted successfully"
      }
      ```

- **Get User Details**
    - **Endpoint:** `GET /api/users/{userId}`
    - **Response:**
      ```json
      {
        "id": "user-id",
        "username": "user1"
      }
      ```

- **Get All Users**
    - **Endpoint:** `GET /api/users?page=0&size=10`
    - **Response:**
      ```json
      {
        "data": [
          {
            "id": "user-id",
            "username": "user1"
          }
        ],
        "totalPages": 1,
        "total": 1
      }
      ```

### Weather Data

- **Get Weather for a City**
    - **Endpoint:** `GET /api/weather/{cityName}`
    - **Response:**
      ```json
      {
        "name": "Tashkent",
        "country": "Uzbekistan",
        "tempC": 25.0,
        "windKph": 10.0,
        "cloud": 20
      }
      ```

- **Get All Cities' Weather**
    - **Endpoint:** `GET /api/weather?page=0&size=10`
    - **Response:**
      ```json
      {
        "data": [
          {
            "name": "Tashkent",
            "country": "Uzbekistan",
            "tempC": 25.0,
            "windKph": 10.0,
            "cloud": 20
          }
        ],
        "totalPages": 1,
        "total": 1
      }
      ```

- **Get Weather for Multiple Cities**
    - **Endpoint:** `POST /api/weather/bulk`
    - **Request Body:**
      ```json
      {
        "cities": ["Tashkent", "London"]
      }
      ```
    - **Response:**
      ```json
      [
        {
          "name": "Tashkent",
          "country": "Uzbekistan",
          "tempC": 25.0,
          "windKph": 10.0,
          "cloud": 20
        },
        {
          "name": "London",
          "country": "UK",
          "tempC": 15.0,
          "windKph": 20.0,
          "cloud": 60
        }
      ]
      ```

## Cron Jobs

- **Weather update job** runs daily at **00:00** (midnight) to update stored weather data.

## Security

- Uses **JWT-based authentication**.
- Secure endpoints with **role-based access control**.

## Contribution

1. Fork the repository.
2. Create a new feature branch: `git checkout -b feature-name`
3. Commit changes and push to your branch.
4. Open a pull request.

## License

This project is licensed under the MIT License.

