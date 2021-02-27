-- Titles schema

-- !Ups

CREATE TABLE Title (
    id varchar NOT NULL,
    name varchar NOT NULL,
    description varchar NOT NULL,
    image varchar NOT NULL,
    tags varchar NOT NULL,
    updated varchar NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE Title;
