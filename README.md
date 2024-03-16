## A Blog Application Using Spring Boot and Angular

### Overview

This application is a combination of a Java Spring Boot backend and an Angular frontend, styled with Tailwind CSS. It leverages PostgreSQL for database operations, incorporates Spring Security for robust JWT authentication, and ensures secure cross-origin requests with CORS. The backend is designed for scalability and security, offering features like pagination and sorting, along with an efficient way to handle blog posts and comments. 

For performance, when blog posts are being fetched, their associated comments are not fetched alongside, comments can be retrieved with seperate api calls, this requires more sate management in the frontend but combined with pagination, fetching blog posts is more efficient.

### Getting Started

#### Prerequisites

- **For the API**: Java JDK and Maven.
- **For the Web Client**: Node.js (LTS version) and Angular CLI.
- **Database**: PostgreSQL.

#### Initial Setup

1. **Clone the Repository**: Begin by cloning the project repository to your local machine.

#### Database Configuration

1. **Set Up PostgreSQL**: Ensure you have PostgreSQL installed and running.
2. **Configure Application**: Update the `api/src/main/resources/application.properties` file with your database configuration details to connect the Spring Boot application to your PostgreSQL database.

#### API Setup (Spring Boot)

1. **Navigate to API Directory**: 
   ```bash
   cd api
   ```
2. **Start PostgreSQL with Docker** (Optional):
   If using Docker, you can start a PostgreSQL instance using:
   ```bash
   docker-compose up --build
   ```
3. **Run the Spring Boot Application**:
   - Ensure you are in the `api` directory.
   - Start the application with Maven:
     ```bash
     ./mvnw spring-boot:run
     ```
   - The API will be available, and you can test it using the Swagger UI at `http://localhost:8080/swagger-ui/index.html`.
   - Starting the api also calls the run method of the seeder class, which updates the database with some dummy data.

#### Web Client Setup (Angular)

1. **Navigate to Web Directory**:
   ```bash
   cd web
   ```
2. **Install Dependencies**:
   Run `npm install` to install all required dependencies.
3. **Start the Development Server**:
   Launch the server with:
   ```bash
   npm run start
   ```
   - Alternatively, use `ng serve`.
   - Access the application at `http://localhost:4200/`.
