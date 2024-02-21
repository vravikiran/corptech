A web application using Spring Boot that integrates with both Salesforce and SQL Server databases.
Added Swagger for API documentation
secures the endpoints using Spring Security
Implemented authentication and authorization for the endpoints using JWT(JSON Web tokens)
To build  docker images and deploy it in any cloud environment added dockerfile and docker-compose file.
Users with the role "ADMIN" should be able to access all endpoints, while users with
the role "USER" should have limited access.
Below are the API endpoints for customer:
  POST /api/customers: Create a new customer.
  GET /api/customers/{id}: Get a customer by ID.
  GET /api/customers: Get all customers.
  PUT /api/customers/{id}: Update a customer by ID.
  DELETE /api/customers/{id}: Delete a customer by ID.
API endpoints to create a new user and generate token based on user credentials:
  POST /auth/addUser: Adds a new user
  POST /auth/generateToken Generates token based on provided user details
Added the SQL scripts for creating tables for customer and user entities
Implemented methods to synchronize customer data between the SQL Server database
and Salesforce. A secure connection is created between the systems to
keep the data in transit and at rest secure.
unit tests for the API endpoints and the data synchronization process.
A scheduler is created to initiate the synchronization process between salesforce and sql server databases which runs daily.

  
  
