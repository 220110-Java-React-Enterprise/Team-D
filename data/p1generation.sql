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

INSERT INTO pone.Card(question, answer1, answer2, answer3, answer4, correct_answer, creator_id) VALUES ('What is REST?', 'Residual State Transfer', 'Restful State Transit', 'Representational State Transfer', 'Resilient State Transit', 3, 1);

INSERT INTO pone.User(first_name, last_name, email) VALUES ('Harry', 'Potter', 'mymail@email.me');