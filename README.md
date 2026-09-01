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

* Customer management
* Vehicle management
* Mechanic management
* Service order management
* Workshop task management
* Parts and stock management
* Appointment management
* Assignment of mechanics to service orders
* Prevention of assigning inactive mechanics
* Association of parts with workshop tasks
* Automatic stock reduction when parts are used
* Automatic stock restoration when TaskPart quantities are reduced or removed
* Protection against insufficient stock
* Labor cost calculation based on labor hours and hourly rate
* Parts cost calculation through TaskPart quantity and unit price
* Total service order cost calculation
* Service order estimate approval and rejection
* Service order priority management
* Service order mileage tracking
* Service order lifecycle dates
* Customer and internal notes
* Vehicle service order history
* Appointment lifecycle and status management
* Protection against invalid appointment dates
* Business rules for closed service orders and appointments
* Protection against deleting entities with existing relationships
* Search and filtering endpoints
* REST endpoints for CRUD operations
* Input validation and global exception handling
* Database persistence with MySQL


## Project Status

✅ **Backend v1.0 completed.**

The REST API is functionally complete and has been tested through a full end-to-end workflow using Postman.

The current version includes customer and vehicle management, service orders, mechanics, workshop tasks, parts and stock management, cost calculation, estimates, vehicle history and workshop appointments.

Frontend development is planned as the next major phase of the project.


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
- `Appointment` - represents a workshop appointment associated with a customer and vehicle

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


Customer
   ↓
Appointment

Vehicle
   ↓
Appointment
```

## Business Logic Highlights

In addition to standard CRUD operations, the API implements business rules such as:

* Preventing inactive mechanics from being assigned to service orders
* Preventing changes to closed service orders (`COMPLETED` or `CANCELLED`)
* Allowing customer and internal notes to remain editable after a service order is closed for documentation purposes
* Calculating labor cost based on `laborHours × hourlyRate`
* Calculating parts cost exclusively through `TaskPart` using `quantity × unitPrice`
* Calculating the total cost of a service order from labor and parts
* Automatically reducing stock when parts are added to a task
* Restoring stock when TaskPart quantities are reduced or removed
* Preventing the use of quantities greater than the available stock
* Preventing direct modification of part stock through normal part updates
* Supporting estimate approval and rejection
* Managing service order priority, mileage and lifecycle dates
* Protecting deletion of entities that are still referenced by other entities
* Enforcing valid appointment state transitions
* Preventing completed or cancelled appointments from being edited
* Allowing only cancelled appointments to be deleted
* Preventing appointments from being created or rescheduled to dates in the past


## How to Run

### Pre-requisites

Make sure you have installed:

- Java 21
- MySQL
- Maven

### Setup

1. Clone the repository: git clone https://github.com/dfcr2712/workshop-manager.git
2. Open the project in your IDE.
3. Create a MySQL database named `workshop_manager`.
4. Configure the following environment variables in your IDE or operating system:

```text
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

The application uses these variables in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/workshop_manager
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

5. Run the Spring Boot application.
6. Use Postman or another API client to test the available endpoints.



## API Endpoints

The API provides REST endpoints for managing customers, vehicles, mechanics, service orders, workshop tasks, parts, stock, task-part associations and appointments.

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
| GET | `/vehicles/{vehicleId}/history` | Get the service order history of a vehicle |

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
| GET | `/service-orders/{id}/costs` | Calculate the complete costs of a service order |
| PUT | `/service-orders/{id}/estimate/approve` | Approve the service order estimate |
| PUT | `/service-orders/{id}/estimate/reject` | Reject the service order estimate |
| GET | `/service-orders/priority/{priority}` | Get service orders by priority |
| PUT | `/service-orders/{id}/priority/{priority}` | Update the priority of a service order |
| PUT | `/service-orders/{id}/expected-completion` | Update the expected completion date |
| PUT | `/service-orders/{id}/status/{status}` | Update the status of a service order |
| PUT | `/service-orders/{id}/customer-notes` | Update customer notes |
| PUT | `/service-orders/{id}/internal-notes` | Update internal notes |

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
| PUT | `/parts/{id}/stock/add/{quantity}` | Add stock to a part |
| GET | `/parts/low-stock` | Get parts with low stock |

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

### Appointments

Appointment endpoints manage workshop scheduling, customer and vehicle appointments, and appointment lifecycle states.

| Method | Endpoint                                                  | Description                                      |
| ------ | --------------------------------------------------------- | ------------------------------------------------ |
| POST   | `/appointments/customer/{customerId}/vehicle/{vehicleId}` | Create an appointment for a customer and vehicle |
| GET    | `/appointments`                                           | Get all appointments                             |
| GET    | `/appointments/{id}`                                      | Get an appointment by ID                         |
| GET    | `/appointments/status/{status}`                           | Get appointments by status                       |
| GET    | `/appointments/vehicle/{vehicleId}`                       | Get appointments for a vehicle                   |
| GET    | `/appointments/customer/{customerId}`                     | Get appointments for a customer                  |
| GET    | `/appointments/date-range`                                | Get appointments between two dates               |
| PUT    | `/appointments/{id}`                                      | Update an appointment                            |
| PUT    | `/appointments/{id}/confirm`                              | Confirm an appointment                           |
| PUT    | `/appointments/{id}/complete`                             | Complete an appointment                          |
| PUT    | `/appointments/{id}/cancel`                               | Cancel an appointment                            |
| DELETE | `/appointments/{id}`                                      | Delete a cancelled appointment                   |


## Next Steps

Planned improvements for future versions include:

- Develop a frontend using HTML, CSS and JavaScript
- Add automated tests
- Add API documentation with Swagger / OpenAPI
- Add authentication and authorization
- Dockerize the application
- Deploy the application