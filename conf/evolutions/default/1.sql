-- Tags schema

-- !Ups

CREATE TABLE Tag (
    id varchar NOT NULL,
    name varchar NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE Tag;
