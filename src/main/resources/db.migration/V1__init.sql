drop table if exists root;
drop table if exists equation;

CREATE TABLE equation
(
    id                 SERIAL PRIMARY KEY,
    expression         VARCHAR(255) NOT NULL,
    solution   BOOLEAN      NOT NULL
);

CREATE TABLE root
(
    root_id          SERIAL PRIMARY KEY,
    root        VARCHAR(255) NOT NULL,
    equation_id BIGINT           NOT NULL,
    FOREIGN KEY (equation_id) REFERENCES equation (id) ON DELETE CASCADE
);
