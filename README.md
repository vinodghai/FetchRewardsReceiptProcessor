# Fetch Rewards Receipt Processor

This is a Java Spring Boot application that follows a three-layer architecture consisting of a Controller layer, a Service layer, and a Repository layer. Each layer has its respective directory, and the components within each layer are organized accordingly. The Controller communicates with the Service layer, which in turn interacts with the Repository layer. The Service layer implements the business logic.

## Database
The app relies on an in-memory database, a simple HashMap. Therefore, initially, there will not be any data, and the data will not persist once the application is shut down.

## Endpoints

Specific details regarding the endpoints can be found in the api.yml file in the root directory. The application provides the following endpoints:

1. **Process Receipt**: `POST /receipts/process`
   - This endpoint receives a Receipt object, calculates its points based on specific rules, and saves the points along with a dynamically generated unique ID for the receipt in the database.
   - The Receipt object is validated using Spring Boot's validation package to ensure the request payload satisfies the constraints. The validations include:
     - `retailer` cannot be null
     - `purchaseDate` cannot be null
     - `purchaseTime` cannot be null
     - `total` cannot be null
     - `items` cannot be null and must have at least one item
   - If the validation fails, appropriate error responses are returned.

2. **Retrieve Points**: `GET /receipts/{id}/points`
   - This endpoint receives the ID of a receipt and returns the saved points associated with that ID.
   - If there are no points associated with the provided ID, a `PointsNotFoundException` is thrown. This exception is caught by an exception handler, which returns an error response.

## Models

The application uses the following models:

### Receipt Model

```java
public record Receipt(
    @NotNull(message = "retailer is required") String retailer,
    @NotNull(message = "purchaseDate is required") LocalDate purchaseDate,
    @NotNull(message = "purchaseTime is required") LocalTime purchaseTime,
    @NotNull(message = "total is required") Double total,
    @Valid
    @NotNull(message = "Items are required")
    @Size(min = 1, message = "there must be at least one item in items") List<Item> items
) {
}
```
### Item Model
```java
public record Item(
    @NotNull(message = "shortDescription is required") String shortDescription,
    @NotNull(message = "price is required") Double price
) {
}
```
### Dependencies
The application relies on the following dependencies:
1. Java JDK
2. Spring Boot Web
3. Spring Boot Validation
4. Spring Boot test

## Installation
### Docker
1. Make sure you have Docker installed on your machine.
2. Build a docker image locally using `docker build -t fetch-rewards-receipt-processor .`
3. Run the created image using `docker run -p 8080:8080 fetch-rewards-receipt-processor .`

The application will start running on http://localhost:8080. You can now test the endpoints using a tool like cURL or API testing tools like Postman or Insomnia.
