# Database

The Workshop Manager backend uses MySQL as its relational database and Spring Data JPA / Hibernate for persistence and entity mapping.

## Main Entities

The current data model includes the following entities:

* `Customer`
* `Vehicle`
* `Mechanic`
* `ServiceOrder`
* `Task`
* `Part`
* `TaskPart`
* `Appointment`

## Main Relationships

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

## Relationship Overview

* A customer can have multiple vehicles.
* A vehicle belongs to one customer.
* A vehicle can have multiple service orders.
* A service order belongs to one vehicle.
* A mechanic can be assigned to multiple service orders.
* A service order can contain multiple tasks.
* A task can use multiple parts through `TaskPart`.
* `TaskPart` stores the quantity and unit price of a part used in a specific task.
* Customers and vehicles can have multiple appointments.

## Persistence

Database persistence is handled through:

* Spring Data JPA repositories
* Hibernate ORM
* MySQL
* Entity relationships such as `@OneToMany` and `@ManyToOne`

The database schema is managed automatically by Hibernate according to the application configuration.
