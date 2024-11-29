CREATE TABLE IF NOT EXISTS sales_person (
	id SERIAL PRIMARY KEY,
	email varchar(255) NULL,
	first_name varchar(30) NULL,
	last_name varchar(30) NULL,
	phone_number varchar(255) NULL,
	"position" varchar(255) NULL,
	"password" varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS sale (
	id SERIAL PRIMARY KEY,
	sale_date date NULL,
	sale_price float8 NOT NULL,
	car_model_id int4 NULL,
	customer_id int4 NULL,
	salesperson_id int4 NULL,
	drive varchar(255) NULL,
	fuel_type varchar(255) NULL,
	transmission varchar(255) NULL,
	volume varchar(255) NULL,
	file_name varchar(255) NULL,
	FOREIGN KEY (car_model_id) REFERENCES car_model(id),
	FOREIGN KEY (customer_id) REFERENCES customer(id),
	FOREIGN KEY (salesperson_id) REFERENCES sales_person(id)
);
