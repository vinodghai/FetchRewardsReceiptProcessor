FROM openjdk:18

# Set the working directory
WORKDIR /app

# Copy the application code to the container
COPY . /app

# Run Maven Wrapper commands
RUN ./mvnw clean install

# Set the entry point to run the application
ENTRYPOINT ./mvnw spring-boot:run
