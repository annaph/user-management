------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
DESIGN:
Solution consists of three projects:
1) Domain - contains domain object definitions, in this case only User class
2) Server - contains server side codebase
3) Client - contains client side codebase

Server is designed/developed as microservice that exposes one REST endpoint to fetch all available users from server.

Client is designed/developed as microservice that exposes REST endpoints to provide CRUD operations. It uses in memory H2 database to store users.
Upon startup client calls server to fetch all available users and import them in database. Client also has a demo UI.

Client codebase is divided into two separate parts:
* UI:
	- static/index.html
	- static/js/usermanagement.js
	- static/css/usermanagement.css
* Backend:
	- com.user.management.client.Application - application entry point
	- com.user.management.client.UserController - REST controller
	- com.user.management.client.UserService - backend business logic
	- com.user.management.client..UserDao - User dao component

------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
BUILD:
Pre-requirements
	* Java8
	* Gradle

1) Build domain project:
	# cd user-management-domain/
	# gradle wrapper --gradle-version 2.13
	# ./gradlew build

2) Build server project
	# cd user-management-server/
	# gradle wrapper --gradle-version 2.13
	# ./gradlew build

3) Build client project:
	# cd user-management-client/
	# gradle wrapper --gradle-version 2.13
	# ./gradlew build

------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
RUN:
1) Run server:
	# cd user-management-server/
	# ./gradlew bootRun

2) Run client:
	# cd user-management-client/
	# ./gradlew bootRun
	
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------	
USAGE:
	* To see all users available on server side use url:
		http://localhost:9090/users
		
	* To inspect client database use url:
		http://localhost:8080/h2
		
		Use following on H2 login screen:
		Saved Settings: Generic H2 (Embedded)
		Setting Name: 	Generic H2 (Embedded)
		Driver Class: 	org.h2.Driver
		JDBC URL:		jdbc:h2:mem:client-db
		User Name: 		sa
		Password: 		<leave_empty>
		
	* To access demo client UI use url:
		http://localhost:8080/
	
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
IMPROVEMENTS:
1) Write unit/integration tests for client backend (I didn't get to do this).
   This would require mocking server calls.
2) Java style checker against code located in src/main folder
3) Code coverage analysis report
4) API docs
5) Installation file and procedure for deployment
6) Switch to Play frameworks! I went for Spring due to better experience with this framework

------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
