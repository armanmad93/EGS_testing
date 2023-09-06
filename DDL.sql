CREATE DATABASE IF NOT EXISTS egs_testing;

CREATE TABLE IF NOT EXISTS scenario
(
    id int auto_increment primary key,
    scenario_name varchar(25)
);

CREATE TABLE IF NOT EXISTS test_data
(
    id          BIGINT auto_increment primary key,
    scenario_id int,
    name        varchar(255),
    gender      varchar(25),
    status      varchar(25),
    email       varchar(50),
    description varchar(50),
    created     timestamp,
    FOREIGN KEY (scenario_id) REFERENCES scenario(id)
);