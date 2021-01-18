INSERT INTO ingredient (id, name) VALUES (0, 'Penicillin');
INSERT INTO ingredient (id, name) VALUES (1, 'Sulfonamides');
INSERT INTO ingredient (id, name) VALUES (2, 'Anticonvulsants');
INSERT INTO ingredient (id, name) VALUES (3, 'Aspirin');
INSERT INTO ingredient (id, name) VALUES (4, 'Ibuprofen');
INSERT INTO ingredient (id, name) VALUES (5, 'Insulin');

INSERT INTO patient (id, first_name, last_name, user_type, penalty_count, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (1, 'Tom', 'Peterson', 4, 0, 'tom.peterson@gmail.com', 'tommy123', '00987563214', 'USA', 41, 87, 'Fifth Ave', 'Chicago');
INSERT INTO patient (id, first_name, last_name, user_type, penalty_count, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (2, 'Jovana', 'Jeremic', 4, 0, 'jovana.jeremic@gmail.com', 'malakojacinicuda', '+988795562', 'France', 49, 2, 'Lui V', 'Paris');

INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (1, 4);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (1, 2);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (1, 5);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (2, 2);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (2, 1);


-- add additional tables when needed
INSERT INTO pharmacy (id, name, country, latitude, longitude, street, town, description) VALUES
                     (0, 'Pfizer', 'USA', 41, 87, 'Fifth Ave', 'Chicago', 'Pfizer Inc. is an American multinational pharmaceutical corporation. Pfizer is one of the world''s largest pharmaceutical companies, and was ranked 64th on the 2020 Fortune 500 list of the largest U.S. corporations by total revenue, at $51.75 billion');
INSERT INTO pharmacy (id, name, country, latitude, longitude, street, town, description, description) VALUES
                     (0, 'Moderna', 'USA', 42, 71, 'Sixth Ave', 'Boston', 'Moderna is an American pharmaceutical and biotechnology company based in Cambridge, Massachusetts. It focuses on drug discovery, drug development, and vaccine technologies based exclusively on messenger RNA (mRNA).');
INSERT INTO pharmacy (id, name, country, latitude, longitude, street, town, description, description) VALUES
                     (0, 'AstraZeneca', 'GB', 51, 0, 'First Ave', 'London', 'AstraZeneca plc is a British-Swedish multinational pharmaceutical and biopharmaceutical company with its headquarters in Cambridge, England. AstraZeneca has a portfolio of products for major disease areas including cancer, cardiovascular, gastrointestinal, infection, respiratory and inflammation.');
