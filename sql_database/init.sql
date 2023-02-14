
CREATE TABLE town (
                       id SERIAL PRIMARY KEY,
                       name varchar(255) NOT NULL,
                       code varchar(255) NOT NULL UNIQUE
);
CREATE TABLE district (
                      id SERIAL PRIMARY KEY,
                      name varchar(255) NOT NULL,
                      code varchar(255) NOT NULL UNIQUE,
                      town_code varchar(255) NOT NULL
);