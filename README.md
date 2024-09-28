# Hotel Booking Service

### Overview
This is a Spring Boot project that provides a hotel booking service. The application is containerized using Docker and runs on port 8080.

### Prerequisites
* Docker installed on your machine
* Java 17 or higher (for building the project)

### Building the Project
To build the project, navigate to the project root directory and run the following command:

``
mvn clean package
``

This will compile the code, run the tests, and package the application into a JAR file.


### Running the Application
To run the application using Docker, navigate to the project root directory and run the following command:

``docker build -t hotel-booking-service .``

``docker run -p 8080:8080 hotel-booking-service``

This will build a Docker image for the application and start a container from it, mapping port 8080 on the host machine to port 8080 in the container.

### Accessing the Application
Once the application is running, you can access it by visiting http://localhost:8080 in your web browser.

### Troubleshooting
If you encounter any issues while running the application, you can check the Docker container logs by running:

``docker logs -f hotel-booking-service``

This will display the latest logs from the container.
