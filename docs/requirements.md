# Functional Requirements

## Customers

* Register customers
* Update customer information
* Delete customers when no existing relationships prevent deletion
* Retrieve customer information

## Vehicles

* Register vehicles
* Update vehicle information
* Associate vehicles with customers
* Retrieve the service order history of a vehicle

## Mechanics

* Register mechanics
* Update mechanic information
* Activate and deactivate mechanics
* Assign active mechanics to service orders

## Service Orders

* Create service orders
* Associate service orders with vehicles
* Update service order status
* Define service order priority
* Record vehicle mileage
* Track relevant lifecycle dates
* Store customer notes and internal notes
* Associate tasks and mechanics
* Calculate labor and parts costs
* Approve or reject estimates
* Prevent operational changes after completion or cancellation

## Tasks

* Create tasks associated with service orders
* Record labor hours
* Record hourly rates
* Associate used parts through TaskPart

## Parts and Stock

* Register and update parts
* Search parts by reference or name
* Add stock
* Retrieve low-stock parts
* Automatically reduce stock when parts are used
* Restore stock when part usage is reduced or removed
* Prevent the use of quantities greater than the available stock
* Prevent direct stock quantity changes through normal part updates

## Appointments

* Create appointments for customers and vehicles
* Retrieve appointments by customer, vehicle, status and date range
* Confirm, complete and cancel appointments
* Reschedule appointments
* Prevent appointments from being scheduled in the past
* Prevent updates after completion or cancellation
* Allow deletion only for cancelled appointments

## Future Improvements

* Invoicing
* Reports
* Authentication and authorization
* Web frontend
* API documentation
* Automated tests
* Docker and deployment
