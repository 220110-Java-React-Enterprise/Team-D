# Project 1: Flashcard
Brandon Perrien &amp; Cheryl See

## Description:
Our project is a notecard application designed to assist with studying.  Each notecard is a set of a question + up to four possible answers that can be added via API
by providing a JSON object in the correct format.  Our application also supports submitting cards via html forms (web client provided) and retrieving all the cards
created by a given user.

### Part 1: Custom ORM:
Our custom ORM is designed to use the Reflection API and custom annotations to determine the properties of the objects to be persisted.  The ORM can be packaged into a jar file via Maven and used as a dependency.

### Part 2: API
This uses the custom ORM as a dependency.  A front end client (webpage) is provided, which allows creating new cards and users; the full API functionality is available by sending JSON requests by any means.

## Tech Stack:
- Java 8
- Maven - project management
- Tomcat - web server / web servlet container
- MariaDB - data persistence
- AWS - providing relational database
- IntellJ - IDE
- SmartTomcat - plugin for IntellJ, simplifies Tomcat launch
- Postman v.9.14.0 - testing of API endpoints
- DBeaver 21.3.4 - database manager

## Dependencies:
- Jackson 2.13.1
- JavaX Servlets - 3.1.0
- JUnit 4.13.1
- Mockito 4.3.1
- Custom ORM (source included in repository)


## Minimum Requiremnets:
- Proper use of OOP principles
- CRUD operations are supported for at least 2 types of objects.
- Communication is done with HTTP exchanges, and resources are transmitted as JSON in request/response bodies.
- JDBC and persistence logic should all be part of your ORM which abstracts this away from the rest of the application.
- Documentation (all classes and methods have adequate Javadoc comments)
- All Exceptions are caught and logged to a file

## Bonus Features
- ORM can build foreign key relations according o object references.
- ORM can design schema on the fly

## User Stories
- As a user, I store JSON objects by invoking the proper endpoint (POST/Create).
- As a user, I can change objects by invoking the proper endpoint (PUT/Update).
- As a user, I can retrieve objects by invoking the proper endpoint (GET/Read).
- As a user, I can delete objects by invoking the proper endpoint (DELETE/Delete).
- As a user, I can retrieve all objects that belong to me. (transmit the user as part of the request header, and build a relation in the database in some way to tie the objects to the user)
    
## Features
- Custom-created ORM that can add objects using Reflection API
- Can add notecards
- Can remove notecards
- Can modify notecards
- Can grab a list of notecards created by a specific user
- Can add notecards via JSON or using the website (form)
- Can register new users via JSON or using the website (form)

## To-do list:
- Add ability to generate tables dynamically
- Add ability to retrieve cards using website
- Add ability to modify cards using website
- Add methods for testing using JUnit / Mockito


## Getting Started
- To clone, user: git clone https://github.com/220110-Java-React-Enterprise/Team-D.git
- Must create the card and user databases prior to using the application:
- The database credentials must be specified in ./src/main/resources/jdbc.properties


## Required fields:
- hostname (Address to access the database)
- port (Port number for the database)
- dbname=pone (If this is changed, must modify the sample ddl statements below)
- username (Username with read/write/access permissions for the database)
- password (Password for the above account)

![image](https://user-images.githubusercontent.com/97481827/155006508-439107c1-95fc-46e0-9337-a3e86ca9bcd9.png)


### SQL Statements needed to setup database: 
```
CREATE TABLE pone.Card(
	card_id INT PRIMARY KEY AUTO_INCREMENT,
	question VARCHAR(200),
	answer1 VARCHAR(100),
	answer2 VARCHAR(100),
	answer3 VARCHAR(100),
	answer4 VARCHAR(100),
	correct_answer INT,
	creator_id INT
);
```
```
CREATE TABLE pone.User(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(30)
);
```

### Launching from IntellJ ###
- First build the custom ORM to compile a jar and place it in your local cache.  This can be done from the command line by navigating to ./ORM/ and using the command "mvn install".
- Then open the Flashcard directory as your project directory - Go to "Edit Configuration", Add New Configuration > Smart Tomcat
- Specify the Tomcat Server (Apache Tomcat/8.5.73), the Context Path /Flashcard, and server port (8080 by default).  
- Save this configuration by pressing Ok and use this to run the application.  If Smart Tomcat is not used, some files may need to be copied to WEB-INF and configuration changes will be needed to be done manually.

![image](https://user-images.githubusercontent.com/97481827/155006552-73645fe2-2610-46c6-9da5-eece868f12e6.png)


If it is running successfully it should show the address where you can access the api in the IntellJ console.  (http://localhost:8080/Flashcard)
![image](https://user-images.githubusercontent.com/97481827/155006612-ffb6b14a-709b-42f7-bbd2-557b85b553f5.png)



## Usage ##
- You can interact with the API by sending requests through Postman.
- Alternatively, you can open index.html in ./Flashcard/client to submit post requests to the server.
![image](https://user-images.githubusercontent.com/97481827/155006831-498f8966-7eae-452d-8bd5-4add0b6ce5ef.png)
![image](https://user-images.githubusercontent.com/97481827/155006907-c6fbed95-3638-4115-a408-fc2ae876bec3.png)



## Contributors ##
Brandon Perrien
Cheryl See


## License ##
This project has no license.





