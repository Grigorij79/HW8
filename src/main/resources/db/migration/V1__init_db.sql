CREATE TABLE worker (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(1000) NOT NULL CHECK (CHAR_LENGTH(name) >= 2),
    birthday DATE CHECK (birthday >= '1900-01-01'),
    level  ENUM('Trainee', 'Junior', 'Middle', 'Senior') NOT NULL,
    salary INTEGER CHECK (salary >= 100 AND salary <= 100000)
);
CREATE TABLE client (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(1000) NOT NULL CHECK (CHAR_LENGTH(name) >= 2)
);
CREATE TABLE project(
    id IDENTITY PRIMARY KEY,
    client_id BIGINT,
    start_date DATE,
    finish_date DATE
);
CREATE TABLE project_worker(
    project_id BIGINT REFERENCES project(id) NOT NULL,
    worker_id BIGINT REFERENCES worker(id) NOT NULL,
    PRIMARY KEY (project_id,worker_id)
);






















