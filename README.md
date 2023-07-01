# Fetch Rewards Receipt Processor

This is a Java Spring Boot application that follows a three-layer architecture consisting of a Controller layer, a Service layer, and a Repository layer. Each layer has its respective directory, and the components within each layer are organized accordingly. The Controller communicates with the Service layer, which in turn interacts with the Repository layer. The Service layer implements the business logic. Service, Repository, and Database instances are injected through Spring dependency injection, and configurations are defined in the `AppConfig.java` file.

## Database
The app relies on an in-memory database, a simple HashMap. Therefore, initially, there will not be any data, and the data will not persist once the application is shut down.

## Flow
The ReceiptController implements the endpoints and receives requests. It uses the IReceiptService interface to calculate receipt points and save points to the database. The ReceiptService implementation uses the IReceiptRepository interface for data saving and retrieving, and the ReceiptRepository implementation is the only class communicating with the database. This architecture is scalable, maintainable, and testable. If we introduce a real database in the future, we can simply add a new repository class implementation that interacts with the database and use its instance in the dependency injection.

## Endpoints
Specific details regarding the endpoints can be found in the `api.yml` file in the root directory. The application provides the following endpoints:

1. **Process Receipt**: `POST /receipts/process`
   - This endpoint receives a Receipt object, calculates its points based on specific rules, and saves the points along with a dynamically generated unique ID for the receipt in the database.
   - The Receipt object is validated using Spring Boot's validation package to ensure the request payload satisfies the constraints. The validations include:
     - `retailer` cannot be null
     - `purchaseDate` cannot be null
     - `purchaseTime` cannot be null
     - `total` cannot be null
     - `items` cannot be null and must have at least one item
   - If the validation fails, appropriate error responses are returned.
   - Finally, it returns a `ReceiptId` object that contains the id of this receipt.

2. **Retrieve Points**: `GET /receipts/{id}/points`
   - This endpoint receives the ID of a receipt and returns the saved points associated with that ID in a `ReceiptPoints` object.
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
1. Make sure you have Docker and Git installed on your machine.
2. Clone the repository: git clone <https://github.com/vinodghai/FetchRewardsReceiptProcessor.git>
3. Navigate to the project directory: cd <FetchRewardsReceiptProcessor>
4. Build a docker image locally using `docker build -t fetch-rewards-receipt-processor .`
5. Run the created image using `docker run -p 8080:8080 fetch-rewards-receipt-processor .`

The application will start running on http://localhost:8080. You can now test the endpoints using a tool like cURL or API testing tools like Postman or Insomnia.
