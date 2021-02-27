-- Tasks schema

-- !Ups

CREATE TABLE Task (
    id varchar NOT NULL,
    title_id varchar NOT NULL,
    group_name varchar NOT NULL,
    priority int NOT NULL,
    memo varchar NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE Task;
