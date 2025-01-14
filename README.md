# Order Processing System Project - Test
This is my technical test assessment - simple Order Processing System

## Table of Contents
- Pre-Condition
- Pre-Setup
- Project Structure Design
- Model Design
- Functional
- Testing
- JUnit Test

## Pre-Condition
- Java 17 (openjdk17)
- Maven project (maven version 3.8.8)
- IDE (Eclipse or others)

## Pre-Setup
Import the project as the maven project, make sure maven update the project to install dependencies.

## Project Structure Design
There is different package to keep the usage as below:
- com.test.orderprocessingsystem 
	- to keep the spring boot application and servlet initializer
- com.test.orderprocessingsystem.constant 
	- to keep all the enum or constant value
- com.test.orderprocessingsystem.controller 
	- to keep all the controller to receive api request and api response
- com.test.orderprocessingsystem.exception 
	- to keep all the customize exception and throw exception
- com.test.orderprocessingsystem.model 
	- to keep all the model class
- com.test.orderprocessingsystem.runner 
	- to keep all the application runner when application startup
- com.test.orderprocessingsystem.service 
	- to keep all the service class with business logic validation
- com.test.orderprocessingsystem.servlet 
	- to keep all the servlet class to receive api request and api response
- com.test.orderprocessingsystem.usecase 
	- to keep all the service interface class

### constant
- to keep the enum value like currency, customer type, status

### controller
- to keep the rest controller for GET Mapping, POST Mapping, PUT Mapping, DELETE Mapping.
- integrate with usecase class and run the implementation from service class.

### exception
- to keep the global exception handling 
- to keep the customized exception
- to handle all the exception and response to web application

### model
- Auditable - record of created datetime and updated datetime
- Maintenance - record of identification
- Order - order detail with choosen product
- Product - product detail

### runner
- to keep all the application runner when application startup
- preload few product records and save into cache

### service
- BatchService - to implement the executor service with scheduler to update the order status with specific requirement
- OrderService - to save the order record, update the order record, query the order record by id or customer name, delete the order record by id, to validate the order detail with product business requirement
- ProductService - to save the product record and query the product record by type, to validate the product business requirement

### servlet
- to keep the web servlet for doGet, doPost, doPut, doDelete method.
- integrate with usecase class and run the implementation from service class.
- to response the content in web browser format

### usecase
- OrderUseCase - interface without implementation for add new order, modifiy order, query the order by id or customer name, delete order by id
- ProductUseCase - interface without implementation for add new product, modifiy product and query the product by type 

## Model Design
- Auditable - abstract class
	- attribute
		- createdDateTime as Date
		- updatedDateTime as Date
	- setter and getter method
	- abstract method saveRecord
	- abstract method updateRecord
- Maintenance
	- attribute
		- id as String
		- module as String (id purpose from different model)
	- setter and getter method
	- saveRecord method
		- set id as module + yyyyMMddHHmmss
		- set createdDateTime as current datetime
		- set updatedDateTime as current datetime
	- updateRecord method
		- set updatedDateTime as current datetime
- Order -> (inherit) Maintenance -> (inherit) Auditable 
	- attribute
		- customerName as String
		- customerType as String
		- product as Product
		- amount as BigDecimal
		- currency as String
		- status as String
	- setter and getter method
	- override the saveRecord method
- Product -> (inherit) Maintenance -> (inherit) Auditable
	- attribute
		- type as String
		- allowSpecificCurrency as boolean
		- allowCurrency as String
		- disallowSpecificCurrency as boolean
		- disallowCurrency as String
		- allowSpecificCustomerType as boolean
		- allowCustomerType as String
	- setter and getter method

## Functional
- REST API provided for order (OrderController)
	- add (using POST mapping)
	- update (using PUT mapping)
	- query (using GET mapping) 
	- delete (using DELETE mapping)
- Web Servlet API provided for order (OrderServlet)
	- add (using POST mapping)
	- update (using PUT mapping)
	- query (using GET mapping) 
	- delete (using DELETE mapping)
- Batch 
	- to use scheduled executor service update order status from pending to processed 
	- schedule each 1 seconds to execute it and priority as VIP customer type
	- schedule each 1 day to execute it and priority as non VIP customer type, if there is VIP customer type records
- All the record save, update, query and delete in cache

## Testing
Find out the @SpingBootApplication class and run as Java Application
- API test
	- Using Postman
	- More information will show in document testing.docx
- Batch test
	- Application start will launch the batch

## JUnit Test
There is few junit test under folder src/test/java with package com.test.orderprocessingsystem.service. Test runner as JUnit 4.
- BatchServiceTest
	- to test the status changes
- OrderServiceTest
	- to test crud for the order record
	- to test exception handle when process order record
- ProductServiceTest
	- to test cru for the product record
