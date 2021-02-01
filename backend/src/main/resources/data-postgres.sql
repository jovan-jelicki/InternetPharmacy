INSERT INTO ingredient (id, name) VALUES (0, 'Penicillin');
INSERT INTO ingredient (id, name) VALUES (1, 'Sulfonamides');
INSERT INTO ingredient (id, name) VALUES (2, 'Anticonvulsants');
INSERT INTO ingredient (id, name) VALUES (3, 'Aspirin');
INSERT INTO ingredient (id, name) VALUES (4, 'Ibuprofen');
INSERT INTO ingredient (id, name) VALUES (5, 'Insulin');



INSERT INTO patient (id, first_name, last_name, user_type, penalty_count, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (0, 'Tom', 'Peterson', 4, 0, 'tom.peterson@gmail.com', 'tommy123', '00987563214', 'USA', 41, 87, 'Fifth Ave', 'Chicago');
INSERT INTO patient (id, first_name, last_name, user_type, penalty_count, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (6, 'Jovana', 'Jeremic', 4, 0, 'jovana.jeremic@gmail.com', 'malakojacinicuda', '+988795562', 'France', 49, 2, 'Lui V', 'Paris');

INSERT INTO dermatologist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (3, 'Pera', 'Peric', 0, 'pera.Dermatologist@gmail.com', 'perap', '00987563214', 'USA', 41, 87, 'Fifth Ave', 'Chicago');
INSERT INTO dermatologist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (4, 'Agate', 'Fendi', 0, 'agate@gmail.com', 'perap', '00987563214', 'SRB', 41, 87, 'Fifth Ave', 'Chicago');
INSERT INTO dermatologist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town)
                    VALUES (5, 'Peter', 'Smith', 0, 'pera@gmail.com', 'perap', '00987563214', 'GER', 41, 87, 'Fifth Ave', 'Chicago');


INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (0, 4);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (0, 2);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (0, 5);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (6, 2);
INSERT INTO patient_allergies (patient_id, allergies_id) VALUES (6, 1);

INSERT INTO pharmacy (id, name, country, latitude, longitude, street, town, description, pharmacist_cost, dermatologist_cost) VALUES
                     (0, 'Pfizer', 'USA', 41, 87, 'Fifth Ave', 'Chicago', 'Pfizer Inc. is an American multinational pharmaceutical corporation. Pfizer is one of the world''s largest pharmaceutical companies, and was ranked 64th on the 2020 Fortune 500 list of the largest U.S. corporations by total revenue, at $51.75 billion',2000,1400);
INSERT INTO pharmacy (id, name, country, latitude, longitude, street, town, description, pharmacist_cost, dermatologist_cost) VALUES
                     (1, 'Moderna', 'USA', 42, 71, 'Sixth Ave', 'Boston', 'Moderna is an American pharmaceutical and biotechnology company based in Cambridge, Massachusetts. It focuses on drug discovery, drug development, and vaccine technologies based exclusively on messenger RNA (mRNA).',3999,2999);
INSERT INTO pharmacy (id, name, country, latitude, longitude, street, town, description, pharmacist_cost, dermatologist_cost) VALUES
                     (2, 'AstraZeneca', 'GB', 51, 0, 'First Ave', 'London', 'AstraZeneca plc is a British-Swedish multinational pharmaceutical and biopharmaceutical company with its headquarters in Cambridge, England. AstraZeneca has a portfolio of products for major disease areas including cancer, cardiovascular, gastrointestinal, infection, respiratory and inflammation.',5000,4000);

INSERT INTO pharmacy (id, country, latitude, longitude, street, town, description, name, pharmacist_cost, dermatologist_cost) VALUES
                     (4, 'Portugal', 43,3,'Sui gue peauqe', 'Lisbon', 'All purpose pharmacy!', 'Suei Mei',1400,3000);


INSERT INTO medication (id, name, type, dose, loyalty_points, medication_shape, manufacturer, medication_issue, note) VALUES
                       (0, 'Xanax', 1, 2, 2, 1, 'Ivancic i sinovi', 0, 'Take when tired of life...');
INSERT INTO medication (id, name, type, dose, loyalty_points, medication_shape, manufacturer, medication_issue, note) VALUES
                       (1, 'Aspirin', 1, 5, 5, 3, 'Ivancic i sinovi', 1, 'May help after some wine');
INSERT INTO medication (id, name, type, dose, loyalty_points, medication_shape, manufacturer, medication_issue, note) VALUES
                       (2, 'Brufen', 0, 10, 1, 0, 'BioFarm', 0, 'Take when your temperature is 39.2');
INSERT INTO medication (id, name, type, dose, loyalty_points, medication_shape, manufacturer, medication_issue, note) VALUES
                       (3, 'Ditenzin', 0, 1, 10, 3, 'Ivancic i sinovi', 0, 'Take when pressure is 200/100...');
INSERT INTO medication (id, name, type, dose, loyalty_points, medication_shape, manufacturer, medication_issue, note) VALUES
                       (4, 'Bromazepan', 1, 6, 20, 4, 'BioFarm', 1, 'Take while studying PSW');


INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (0, 1);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (1, 2);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (2, 4);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (3, 2);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (4, 3);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (0, 5);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (1, 5);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (2, 5);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (3, 0);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (4, 0);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (0, 2);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (1, 3);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (2, 0);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (3, 5);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (4, 1);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (0, 0);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (1, 4);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (1, 0);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (2, 3);
INSERT INTO medication_ingredient (medication_id, ingredient_id) VALUES (0, 4);


INSERT INTO pharmacy_admin (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town, pharmacy_id)
VALUES (1, 'Jovan', 'Brokovich', 2, 'brokovich@gmail.com', 'broka', '0605435487', 'SRB', 41, 87, 'Avenue 3rd', 'Belgrade',1);

INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (1, 2, 1);
INSERT INTO medication_quantity (id ,quantity, medication_id)
VALUES (2, 5, 0);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (3, 1, 3);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (4, 5, 4);

INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 2);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 4);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 1);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 3);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (1, 2);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (2, 1);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (1, 1);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (1, 3);

insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (1,0,0, 300, '2021-01-01', '2021-02-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (2,4,0, 300, '2021-01-01', '2021-02-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (3,1,0, 300, '2021-01-01', '2021-02-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (4,3,0, 300, '2021-01-01', '2021-02-02');

insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (5,0,1, 300, '2021-01-01', '2021-02-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (6,1,2, 300, '2021-01-01', '2021-02-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (7,1,1, 300, '2021-01-01', '2021-02-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (8,3,1, 300, '2021-01-01', '2021-02-02');

INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (0, '2021-01-31', 3, 1,0);
-- INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
-- VALUES (2, '2021-01-26', 3, 1,6);

INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (0,0);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (4,0);

-- INSERT INTO pharmacy_dermatologist(pharmacy_id, dermatologist_id) VALUES (1,1);


INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (1, '2020-01-01 09:00:00', '2021-12-31 17:00:00', 1);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (2, '2020-01-01 10:00:00', '2021-12-31 16:00:00', 0);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (3, '2020-01-01 07:00:00', '2021-12-31 12:00:00', 0);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (4, '2020-01-01 13:00:00', '2021-12-31 15:00:00', 1);


INSERT INTO dermatologist_working_hours(dermatologist_id, working_hours_id) VALUES (3,3);
INSERT INTO dermatologist_working_hours(dermatologist_id, working_hours_id) VALUES (3,4);

INSERT INTO pharmacist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town, working_hours_id, is_active)
    VALUES (1, 'Jovan', 'Jovic', 1, 'jovan.Pharmacist@gmail.com', 'jovanj', '00987563214', 'USA', 41, 87, 'Fifth Ave', 'Chicago',1, true);
INSERT INTO pharmacist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town, working_hours_id, is_active)
    VALUES (2, 'Filip', 'Markovic', 1, 'filip@gmail.com', 'filip', '00987563214', 'SRB', 41, 87, 'Fifth Ave', 'NY',2, true);

-- INSERT INTO pharmacy_pharmacist(pharmacy_id, pharmacist_id) VALUES (1,1);
-- INSERT INTO pharmacy_pharmacist(pharmacy_id, pharmacist_id) VALUES (0,2);

INSERT INTO vacation_request(id, employee_id, employee_type, period_end, period_start, vacation_note, vacation_request_status,pharmacy_id)
    VALUES (1,3,0, '2021-03-01', '2021-02-10', 'Godisnji odmor dermatolog', 1, 0);
INSERT INTO vacation_request(id, employee_id, employee_type, period_end, period_start, vacation_note, vacation_request_status,pharmacy_id)
    VALUES (2,1,1, '2021-07-08', '2021-01-01', 'Godisnji odmor farmaceut', 0, 1);


insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (1,3,0, null, '2021-03-01 11:00:00', '2021-03-01 10:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (2,3,0, null, '2021-03-01 10:45:00', '2021-03-01 10:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (3,3,0, null, '2021-03-01 13:00:00', '2021-03-01 12:00:00', 1, null,null,0, true);

insert into appointment (id,   examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (4,1,0, null, '2021-03-01 13:00:00', '2021-03-01 12:00:00', 0, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (5,1,0, null, '2021-03-01 13:00:00', '2021-03-01 12:00:00', 0, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (6,1,0, null, '2021-01-01 13:00:00', '2021-01-01 12:00:00', 0, null,null,1, true);


-- medication quantity only for medication orders
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (5, 100, 0);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (6, 53, 1);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (7, 65, 2);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (8, 1020, 3);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (9, 1342, 4);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (10, 5123, 3);

INSERT INTO medication_order(id, deadline, pharmacy_admin_id, status)
VALUES (1, '2020-03-04', 1, 0);
INSERT INTO medication_order(id, deadline, pharmacy_admin_id, status)
VALUES (2, '2020-03-10', 1, 0);

INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (5,1);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (6,1);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (7,1);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (8,2);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (9,2);

-- SELECT MAX(id) FROM appointment;
--
-- SELECT nextval('bookmarks_id_seq');