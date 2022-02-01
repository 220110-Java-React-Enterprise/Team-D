CREATE TABLE TestReflection(
num INT NOT NULL,
name VARCHAR(50),
b BOOL,
CONSTRAINT TestReflection_pk PRIMARY KEY (num)
);

SELECT * FROM TestReflection;

DROP TABLE TestReflection;