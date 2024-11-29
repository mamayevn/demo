CREATE TABLE IF NOT EXISTS cars (
	id SERIAL PRIMARY KEY,
	brand varchar(255) NULL,
	engine_volume float8 NOT NULL,
	model varchar(255) NULL,
	price float8 NOT NULL,
	"year" int4 NOT NULL,
	person_id int4 NULL,
	CONSTRAINT cars_price_check CHECK ((price >= (0)::double precision)),
	CONSTRAINT cars_year_check CHECK ((year >= 1901)), FOREIGN KEY (person_id) REFERENCES person(id));

CREATE TABLE IF NOT EXISTS transmission (
	id SERIAL PRIMARY KEY,
	"name" varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS drive (
	id SERIAL PRIMARY KEY,
	"name" varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS brand (
	id SERIAL PRIMARY KEY,
	"name" varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS volume (
	id SERIAL PRIMARY KEY,
	"name" varchar(255) NULL,
	volume_value varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS fuel_type (
	id SERIAL PRIMARY KEY,
	"name" varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS car_model (
	id SERIAL PRIMARY KEY,
	drive varchar(255) NULL,
	fuel_type varchar(255) NULL,
	"name" varchar(255) NULL,
	transmission varchar(255) NULL,
	volume varchar(255) NULL,
	brand_id int4 NULL,
	drive_id int4 NULL,
	fuel_type_id int4 NULL,
	transmission_id int4 NULL,
	volume_id int4 NULL,
	FOREIGN KEY (volume_id) REFERENCES volume(id),
	FOREIGN KEY (brand_id) REFERENCES brand(id),
	FOREIGN KEY (drive_id) REFERENCES drive(id),
	FOREIGN KEY (transmission_id) REFERENCES transmission(id),
	FOREIGN KEY (fuel_type_id) REFERENCES fuel_type(id)
);
