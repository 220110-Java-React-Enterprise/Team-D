# Flashcard
Brandon Perrien &amp; Cheryl See

Our project is a notecard application designed to assist with studying.  Each notecard is a set of a question + up to four possible answers that can be added via API
by providing a JSON object in the correct format.  Our application also supports submitting cards via html forms (web client provided) and retrieving all the cards
created by a given user.

Technologies Used:
Java 8
Maven - project management
Tomcat - web server / web servlet container
MariaDB - data persistence
AWS - providing relational database
IntellJ - IDE
SmartTomcat - plugin for IntellJ, simplifies Tomcat launch
Postman v.9.14.0 - testing of API endpoints
DBeaver 21.3.4 - database manager

Dependencies:
Jackson 2.13.1
JavaX Servlets - 3.1.0
JUnit 4.13.1
Mockito 4.3.1
Custom ORM (source included in repository)

Features
- Custom-created ORM that can add objects using Reflection API
- Can add notecards
- Can remove notecards
- Can modify notecards
- Can grab a list of notecards created by a specific user
- Can add notecards via JSON or using the website (form)
- Can register new users via JSON or using the website (form)

To-do list:
- Add ability to generate tables dynamically
- Add ability to retrieve cards using website
- Add ability to modify cards using website
- Add methods for testing using JUnit / Mockito


Getting Started
git clone https://github.com/220110-Java-React-Enterprise/Team-D.git

Must create the card and user databases prior to using the application:
- The database credentials must be specified in ./src/main/resources/jdbc.properties
Required fields:
hostname (Address to access the database)
port (Port number for the database)
dbname=pone (If this is changed, must modify the sample ddl statements below)
username (Username with read/write/access permissions for the database)
password (Password for the above account)

SQL Statements needed to setup database: 
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

CREATE TABLE pone.User(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(30)
);

If launching from IntellJ -
First build the custom ORM to compile a jar and place it in your local cache.  This can be done from the command line by navigating to ./ORM/ and using the command "mvn install".

Then open the Flashcard directory as your project directory - Go to "Edit Configuration", Add New Configuration > Smart Tomcat
Specify the Tomcat Server (Apache Tomcat/8.5.73), the Context Path /Flashcard, and server port (8080 by default).  Save this configuration by pressing Ok and use this to run the application.  If Smart Tomcat is not used, some files may need to be copied to WEB-INF and configuration changes will be needed to be done manually.

If it is running successfully it should show the address where you can access the api in the IntellJ console.  (http://localhost:8080/Flashcard)



Usage

You can interact with the API by sending requests through Postman.

Alternatively, you can open the webpages in ./Flashcard/client to submit post requests to the server.  index.html will let you create new notecards, while newuser.html will allow you to add new users.


Contributors

Brandon Perrien
Cheryl See


License

This project has no license.
