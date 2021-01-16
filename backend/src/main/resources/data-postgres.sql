INSERT INTO ingredient (id, name) VALUES (0, 'Penicillin');
INSERT INTO ingredient (id, name) VALUES (1, 'Sulfonamides');
INSERT INTO ingredient (id, name) VALUES (2, 'Anticonvulsants');
INSERT INTO ingredient (id, name) VALUES (3, 'Aspirin');
INSERT INTO ingredient (id, name) VALUES (4, 'Ibuprofen');
INSERT INTO ingredient (id, name) VALUES (5, 'Insulin');

INSERT INTO contact (id, country, latitude, longitude, street, town, phone_number) VALUES (0, 'USA', 41, 87, 'Fifth Ave', 'Chicago', '00987563214');
INSERT INTO contact (id, country, latitude, longitude, street, town, phone_number) VALUES (1, 'France', 49, 2, 'Lui V', 'Paris', '+988795562');

INSERT INTO credentials (id, email, password) VALUES (0, 'tom.peterson@gmail.com', 'tommy123');
INSERT INTO credentials (id, email, password) VALUES (1, 'jovana.jeremic@gmail.com', 'malakojacinicuda');

INSERT INTO patient (id, first_name, last_name, user_type, contact_id, credentials_id, penalty_count) VALUES
                    (1, 'Tom', 'Peterson', 4, 0, 0, 0);
INSERT INTO patient (id, first_name, last_name, user_type, contact_id, credentials_id, penalty_count) VALUES
                    (2, 'Jovana', 'Jeremic', 4, 1, 1, 0);

INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (1, 4);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (1, 2);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (1, 5);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (2, 2);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (2, 1);