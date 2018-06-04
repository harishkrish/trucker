# Trucker, A Spring Boot Application

Trucker is an IOT application which mocks real life tracking of truck details. Tha application was created as a spring boot application, with rest service end points.

### Service end points:

* /trucker/vehicles 
	* GET : Get details of all vehicles
	* PUT : Create/Update vehicle details

* /trucker/readings
	* POST : Create readings generated from vehicles

* /trucker/allAlerts/<priority>
	* GET : Get details of all alerts generated since 2 hours with given priority(1:HIGH ,2:MEDIUM, 3:LOW). 

* /trucker/location/<vin>
	* GET : Get details of vehicle's latest location within last 30 minutes.

* /trucker/alerts/<vin>
	* GET : Get details of the historic alerts created for the given Vehicle.

* /trucker/vehicle/<vin>
	* Get : Get all details of the Vehicle, given its vin