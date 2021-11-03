INSERT INTO dentist (name) VALUES ('John Smith');
INSERT INTO dentist (name) VALUES ('Mary Jane');
INSERT INTO dentist (name) VALUES ('Karl Gustav');
INSERT INTO dentist (name) VALUES ('Toomas Tamm');

INSERT INTO dentist_visit (dentist_id, visit_time) VALUES (1, parsedatetime('10-11-2021 15:30', 'dd-MM-yyyy hh:mm'));
INSERT INTO dentist_visit (dentist_id, visit_time) VALUES (1, parsedatetime('10-11-2021 14:30', 'dd-MM-yyyy hh:mm'));
INSERT INTO dentist_visit (dentist_id, visit_time) VALUES (1, parsedatetime('10-11-2021 10:30', 'dd-MM-yyyy hh:mm'));
INSERT INTO dentist_visit (dentist_id, visit_time) VALUES (3, parsedatetime('15-11-2021 09:30', 'dd-MM-yyyy hh:mm'));
INSERT INTO dentist_visit (dentist_id, visit_time) VALUES (3, parsedatetime('16-11-2021 13:30', 'dd-MM-yyyy hh:mm'));
INSERT INTO dentist_visit (dentist_id, visit_time) VALUES (3, parsedatetime('17-11-2021 15:30', 'dd-MM-yyyy hh:mm'));