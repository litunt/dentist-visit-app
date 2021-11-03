DROP TABLE IF EXISTS dentist;
DROP TABLE IF EXISTS dentist_visit;

CREATE TABLE dentist (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_dentist_id PRIMARY KEY (id)
);

CREATE TABLE dentist_visit (
    id BIGINT AUTO_INCREMENT,
    dentist_id BIGINT NOT NULL,
    visit_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_dentist FOREIGN KEY (dentist_id) REFERENCES dentist (id)
);