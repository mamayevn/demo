CREATE TABLE IF NOT EXISTS customer (
	id SERIAL PRIMARY KEY,
	birth_date date NULL,
	email varchar(255) NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	occupation varchar(255) NULL,
	phone_number varchar(255) NULL
);

