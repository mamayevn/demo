CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS person (
   	id SERIAL PRIMARY KEY,
   	age int4 NULL,
   	email varchar(255) NOT NULL,
   	first_name varchar(30) NOT NULL,
   	CONSTRAINT person_age_check CHECK ((age >= 18))
);