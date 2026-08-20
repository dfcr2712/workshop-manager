# Workshop Manager

REST API for managing automotive repair workshops, developed with Java and Spring Boot.

This is a personal project created to practice backend development concepts such as REST APIs, database persistence, entity relationships and layered architecture.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman

## Current Features

- Customer management
- Vehicle management
- Service order management
- Mechanic management
- Workshop task management
- Parts management
- Assignment of mechanics to service orders
- Association of parts with workshop tasks
- Quantity management for parts used in each task
- Material cost calculation for tasks
- Search and filtering endpoints
- REST endpoints for CRUD operations
- Input validation and exception handling
- Database persistence with MySQL

## Project Status

🚧 Backend development in progress.

## Project Structure

The project follows a layered architecture:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

### Main Entities

- `Customer` - represents a workshop customer
- `Vehicle` - represents a vehicle associated with a customer
- `ServiceOrder` - represents a workshop service order associated with a vehicle
- `Mechanic` - represents a workshop mechanic who can be assigned to service orders
- `Task` - represents a repair or maintenance task belonging to a service order
- `Part` - represents a workshop part
- `TaskPart` - represents the association between a task and a part, including the quantity used

### Entity Relationships

```text
Customer
   ↓
Vehicle
   ↓
ServiceOrder
   ↓
Task
   ↓
TaskPart
   ↓
Part

Mechanic
   ↓
ServiceOrder
```

## Business Logic Highlights

In addition to standard CRUD operations, the API includes business logic such as:

- Assigning mechanics to service orders
- Filtering service orders by status
- Filtering service orders by creation date range
- Searching vehicles by license plate
- Searching parts by reference or name
- Associating parts with workshop tasks
- Managing the quantity of each part used in a task
- Calculating the total material cost of a task

## How to Run

### Pre-requisites

Make sure you have installed:

- Java 21
- MySQL
- Maven

### Setup

1. Clone the repository: git clone https://github.com/dfcr2712/workshop-manager.git
2. Open the project in your IDE.
3. Create a MySQL database for the application.
4. Configure the database connection in application.yml.

```text
Example:

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/workshop_manager
    username: your_username
    password: your_password
```

5. Run the Spring Boot application.
6. Use Postman or another API client to test the available endpoints.


## API Endpoints

The API provides REST endpoints for managing customers, vehicles, workshop tasks, parts and task-part associations.

### Customers

Customer endpoints allow the creation, retrieval, update, deletion and search of workshop customers.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/customers` | Create a new customer |
| GET | `/customers` | Get all customers |
| GET | `/customers/{id}` | Get a customer by ID |
| PUT | `/customers/{id}` | Update an existing customer |
| DELETE | `/customers/{id}` | Delete a customer |
| GET | `/customers/name/{name}` | Search customers by name |
| GET | `/customers/email/{email}` | Get a customer by email |

### Vehicles

Vehicle endpoints allow the creation, retrieval, update, deletion and search of vehicles associated with workshop customers.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/vehicles/customer/{customerId}` | Create a new vehicle for a customer |
| GET | `/vehicles` | Get all vehicles |
| GET | `/vehicles/{id}` | Get a vehicle by ID |
| PUT | `/vehicles/{id}` | Update an existing vehicle |
| DELETE | `/vehicles/{id}` | Delete a vehicle |
| GET | `/vehicles/customer/{customerId}` | Get all vehicles belonging to a customer |
| GET | `/vehicles/license-plate/{licensePlate}` | Get a vehicle by license plate |

### Tasks

Task endpoints allow the creation, retrieval, update and deletion of workshop tasks associated with service orders.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/tasks/serviceOrder/{serviceOrderId}` | Create a new task for a service order |
| GET | `/tasks` | Get all tasks |
| GET | `/tasks/{id}` | Get a task by ID |
| GET | `/tasks/serviceOrder/{serviceOrderId}` | Get all tasks belonging to a service order |
| PUT | `/tasks/{id}` | Update an existing task |
| DELETE | `/tasks/{id}` | Delete a task |

### Service Orders

Service order endpoints allow the creation, retrieval, update, deletion and filtering of workshop service orders.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/service-orders/vehicle/{vehicleId}` | Create a new service order for a vehicle |
| GET | `/service-orders` | Get all service orders |
| GET | `/service-orders/{id}` | Get a service order by ID |
| PUT | `/service-orders/{id}` | Update an existing service order |
| DELETE | `/service-orders/{id}` | Delete a service order |
| GET | `/service-orders/vehicle/{vehicleId}` | Get all service orders for a vehicle |
| GET | `/service-orders/status/{status}` | Get service orders by status |
| GET | `/service-orders/dates/{startDate}/{endDate}` | Get service orders created between two dates |
| PUT | `/service-orders/{orderId}/mechanic/{mechanicId}` | Assign a mechanic to a service order |
| GET | `/service-orders/mechanic/{mechanicId}` | Get all service orders assigned to a mechanic |

### Mechanics

Mechanic endpoints allow the creation, retrieval, update, deletion and filtering of workshop mechanics.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/mechanics` | Create a new mechanic |
| GET | `/mechanics` | Get all mechanics |
| GET | `/mechanics/{id}` | Get a mechanic by ID |
| GET | `/mechanics/name/{name}` | Search mechanics by name |
| GET | `/mechanics/speciality/{speciality}` | Get mechanics by speciality |
| GET | `/mechanics/active/{active}` | Get mechanics by active status |
| PUT | `/mechanics/{id}` | Update an existing mechanic |
| DELETE | `/mechanics/{id}` | Delete a mechanic |

### Parts

Part endpoints allow the creation, retrieval, update, deletion and search of workshop parts.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/parts` | Create a new part |
| GET | `/parts` | Get all parts |
| GET | `/parts/{id}` | Get a part by ID |
| GET | `/parts/reference/{reference}` | Get a part by reference |
| GET | `/parts/name/{name}` | Search parts by name |
| PUT | `/parts/{id}` | Update an existing part |
| DELETE | `/parts/{id}` | Delete a part |

### Task Parts

Task part endpoints manage the association between workshop tasks and the parts used in each task.

| Method | Endpoint | Description |
|---|---|---|
| POST | `/task-parts/task/{taskId}/part/{partId}?quantity={quantity}` | Add a part to a task with a specified quantity |
| GET | `/task-parts/{id}` | Get a task-part association by ID |
| GET | `/task-parts/task/{taskId}` | Get all parts associated with a task |
| PUT | `/task-parts/{id}/quantity` | Update the quantity of a part used in a task |
| DELETE | `/task-parts/{id}` | Remove a part from a task |
| GET | `/task-parts/task/{taskId}/material-cost` | Calculate the total material cost of a task |

## Next Steps

Planned improvements for the project include:

- Improve API validation and error responses
- Add automated tests
- Add authentication and authorization
- Improve stock management for workshop parts
- Add labour cost calculations
- Calculate complete service order costs
- Add API documentation with Swagger / OpenAPI
- Dockerize the application
- Develop a frontend for the system