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
INSERT INTO pharmacy_admin (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town, pharmacy_id)
VALUES (2, 'Elena', 'Kitic', 2, 'elena@gmail.com', 'pshpsh', '0605435487', 'SRB', 41, 87, 'Nemanjina', 'Belgrade',0);

INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (1, 2, 1);
INSERT INTO medication_quantity (id ,quantity, medication_id)
VALUES (2, 5, 0);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (3, 1, 3);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (4, 5, 4);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (20, 5, 4);

-- poseban medication quantity za report lekova, ne diraj
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (5, 10, 4);
-- kraj

INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 2);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 4);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 1);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (0, 3);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (1, 2);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (2, 1);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (1, 1);
INSERT INTO pharmacy_medication_quantity(pharmacy_id, medication_quantity_id) VALUES (1, 3);

insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (1,0,0, 300, '2021-01-01', '2021-02-05');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (2,4,0, 400, '2021-01-01', '2021-02-10');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (3,1,0, 760, '2021-01-01', '2021-03-02');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (4,3,0, 99, '2021-01-01', '2021-05-18');

insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (5,0,1, 123, '2021-01-01', '2021-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (6,1,2, 642, '2021-01-01', '2021-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (7,1,1, 3765, '2021-01-01', '2021-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (8,3,1, 323, '2021-01-01', '2021-02-20');

--medication price list history
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (9,0,1, 500, '2020-01-01', '2020-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (10,0,1, 430, '2019-01-01', '2019-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (11,0,1, 330, '2018-01-01', '2018-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (12,1,1, 530, '2018-01-01', '2018-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (13,1,1, 630, '2019-01-01', '2019-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (14,3,1, 130, '2019-01-01', '2019-02-20');
insert into medication_price_list(id,medication_id, pharmacy_id, cost, period_start, period_end) values
    (15,3,1, 230, '2018-01-01', '2018-02-20');

INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
 VALUES (0, '2021-03-31', 3, 1,0);
-- INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
-- VALUES (1, '2021-01-28', 2, 4,0);
-- INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
-- VALUES (2, '2021-01-27', 2, 20,0);
-- INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
-- VALUES (2, '2021-01-26', 3, 1,6);

-- NE DIRAJ OVO! Potrebno za report! Potpis : David
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id, is_discounted)
VALUES (1, '2021-01-31', 2, 5,0, true);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (2, '2021-01-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (3, '2020-12-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (4, '2020-12-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (5, '2020-11-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (6, '2020-10-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (7, '2020-10-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (8, '2020-09-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (9, '2020-08-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (10, '2020-08-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (11, '2020-07-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (12, '2020-06-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (13, '2020-05-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (14, '2020-04-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (15, '2020-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (16, '2020-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (17, '2020-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (18, '2019-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (19, '2018-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (20, '2017-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (21, '2016-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (22, '2015-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (23, '2014-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (24, '2013-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (25, '2012-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (26, '2011-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (27, '2012-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (28, '2014-02-25', 2, 5,0);
INSERT INTO medication_reservation(id, pick_up_date, status, medication_quantity_id, patient_id)
VALUES (29, '2016-02-25', 2, 5,0);
-- kraj

INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,0);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (0,1);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (0,2);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (4,0);

-- podaci za report medication, Potpis : David
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,1);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,2);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,3);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,4);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,5);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,6);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,7);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,8);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,9);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,10);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,11);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,12);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,13);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,14);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,15);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,16);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,17);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,18);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,19);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,20);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,21);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,22);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,23);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,24);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,25);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,26);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,27);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,28);
INSERT INTO pharmacy_medication_reservation (pharmacy_id, medication_reservation_id)
VALUES (1,29);
-- kraj

-- INSERT INTO pharmacy_dermatologist(pharmacy_id, dermatologist_id) VALUES (1,1);


INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (1, '2020-01-01 09:00:00', '2021-12-31 17:00:00', 1);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (2, '2020-01-01 10:00:00', '2021-12-31 16:00:00', 0);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (3, '2020-01-01 07:00:00', '2021-12-31 12:00:00', 0);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (4, '2020-01-01 13:00:00', '2021-12-31 15:00:00', 1);
INSERT INTO working_hours (id, period_start, period_end, pharmacy_id) VALUES (5, '2020-01-01 17:00:00', '2021-12-31 20:00:00', 0);


INSERT INTO dermatologist_working_hours(dermatologist_id, working_hours_id) VALUES (3,3);
INSERT INTO dermatologist_working_hours(dermatologist_id, working_hours_id) VALUES (3,4);
INSERT INTO dermatologist_working_hours(dermatologist_id, working_hours_id) VALUES (5,5);

INSERT INTO pharmacist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town, working_hours_id, is_active)
    VALUES (1, 'Jovan', 'Jovic', 1, 'jovan.Pharmacist@gmail.com', 'jovanj', '00987563214', 'USA', 41, 87, 'Fifth Ave', 'Chicago',1, true);
INSERT INTO pharmacist (id, first_name, last_name, user_type, email, password, phone_number, country, latitude, longitude, street, town, working_hours_id, is_active)
    VALUES (2, 'Filip', 'Markovic', 1, 'filip@gmail.com', 'filip', '00987563214', 'SRB', 41, 87, 'Fifth Ave', 'NY',2, true);

-- INSERT INTO pharmacy_pharmacist(pharmacy_id, pharmacist_id) VALUES (1,1);
-- INSERT INTO pharmacy_pharmacist(pharmacy_id, pharmacist_id) VALUES (0,2);

INSERT INTO vacation_request(id, employee_id, employee_type, period_end, period_start, vacation_note, vacation_request_status,pharmacy_id)
    VALUES (1,3,0, '2021-03-02', '2021-02-10', 'Godisnji odmor dermatolog', 0, 0);
INSERT INTO vacation_request(id, employee_id, employee_type, period_end, period_start, vacation_note, vacation_request_status,pharmacy_id)
    VALUES (2,1,1, '2021-07-08', '2021-04-01', 'Godisnji odmor farmaceut', 0, 1);


insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (1,3,0, null, '2021-03-01 11:00:00', '2021-03-01 10:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (2,3,0, null, '2021-03-01 10:45:00', '2021-03-01 10:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (3,3,0, null, '2021-03-01 13:00:00', '2021-03-01 12:00:00', 1, null,null,0, true);

insert into appointment (id,   examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (4,1,0, 0, '2021-03-01 13:00:00', '2021-03-01 12:00:00', 1, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (5,1,3, 0, '2021-03-08 13:00:00', '2021-03-08 12:00:00', 1, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (6,0,0, null, '2021-01-01 13:00:00', '2021-01-01 12:00:00', 0, null,null,1, true);

insert into appointment (id,   examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (40,1,1, 0, '2021-03-01 14:00:00', '2021-03-01 13:00:00', 1, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (41,1,1, 0, '2021-03-08 08:00:00', '2021-03-08 09:00:00', 1, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (42,1,1, 0, '2021-01-01 10:00:00', '2021-01-01 11:00:00', 0, null,null,1, true);

-- NE DIRAJ MI OVA TRI!!!!!  Potpis : Jovan!!!
insert into appointment (id,   examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (7,2,0, 0, '2021-03-01 13:00:00', '2021-03-01 12:00:00', 0, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (8,2, 0, 6, '2021-03-01 14:00:00', '2021-03-01 13:00:00', 0, null,null,1, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
    values (9,2 ,0, 0, '2021-01-01 16:00:00', '2021-01-01 15:00:00', 0, null,null,1, true);


-- NE DIRAJ MI OVO NI SLUCAJNO, TREBA MI ZA DUGOROCNI REPORT!!!!!  Potpis : David!!!
insert into appointment (id,   examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (10,3,1, 0, '2021-01-01 13:00:00', '2021-01-01 12:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (11,3, 1, 6, '2021-01-01 14:00:00', '2021-01-01 13:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (12,3 ,1, 0, '2021-01-01 16:00:00', '2021-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (13,3 ,1, 0, '2020-12-01 16:00:00', '2020-12-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (14,3 ,1, 0, '2020-11-01 16:00:00', '2020-11-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (15,3 ,1, 0, '2020-10-01 16:00:00', '2020-10-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (16,3 ,1, 0, '2020-09-01 16:00:00', '2020-09-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (17,3 ,1, 0, '2020-08-01 16:00:00', '2020-08-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (18,3 ,1, 0, '2020-07-01 16:00:00', '2020-07-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (19,3 ,1, 0, '2020-06-01 16:00:00', '2020-06-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (20,3 ,1, 0, '2020-05-01 16:00:00', '2020-05-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (21,3 ,1, 0, '2020-04-01 16:00:00', '2020-04-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (22,3 ,1, 0, '2020-03-01 16:00:00', '2020-03-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (23,3 ,1, 0, '2020-02-01 16:00:00', '2020-02-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (24,3 ,1, 0, '2020-01-01 16:00:00', '2020-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (25,3 ,1, 0, '2020-01-01 16:00:00', '2020-01-01 15:00:00', 1, null,null,0, true);

insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (26,3 ,1, 0, '2019-01-01 16:00:00', '2019-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (27,3 ,1, 0, '2019-01-01 16:00:00', '2019-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (28,3 ,1, 0, '2019-01-01 16:00:00', '2019-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (38,3 ,1, 0, '2018-01-01 16:00:00', '2018-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (29,3 ,1, 0, '2017-01-01 16:00:00', '2017-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (30,3 ,1, 0, '2016-01-01 16:00:00', '2016-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (31,3 ,1, 0, '2015-01-01 16:00:00', '2015-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (32,3 ,1, 0, '2014-01-01 16:00:00', '2014-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (33,3 ,1, 0, '2013-01-01 16:00:00', '2013-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (34,3 ,1, 0, '2012-01-01 16:00:00', '2012-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (35,3 ,1, 0, '2011-01-01 16:00:00', '2011-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (36,3 ,1, 0, '2011-01-01 16:00:00', '2011-01-01 15:00:00', 1, null,null,0, true);
insert into appointment (id,  examiner_id, appointment_status,  patient_id, period_end, period_start, pharmacy_id, report, therapy_id, type, is_active)
values (37,3 ,1, 0, '2010-01-01 16:00:00', '2010-01-01 15:00:00', 1, null,null,0, true);

--- kraj

-- medication quantity only for medication orders
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (6, 100, 0);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (7, 53, 1);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (8, 65, 2);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (9, 1020, 3);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (10, 1342, 4);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (11, 5123, 3);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (12, 6000, 3);
INSERT INTO medication_quantity (id, quantity, medication_id)
VALUES (13, 2000, 2);


INSERT INTO medication_order(id, deadline, pharmacy_admin_id, status)
VALUES (1, '2021-01-01', 1, 0);
INSERT INTO medication_order(id, deadline, pharmacy_admin_id, status)
VALUES (2, '2021-03-10', 1, 0);
INSERT INTO medication_order(id, deadline, pharmacy_admin_id, status)
VALUES (3, '2021-03-10', 1, 0);

INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (6,1);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (7,1);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (8,1);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (9,2);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (10,3);
INSERT INTO medication_order_medication_quantity(medication_quantity_id, medication_order_id)
values (12,3);


insert into medication_lacking_event(id, employee_id, employee_type, event_date, medication_id, pharmacy_id)
values (1, 3,0, '2021-01-01', 0,1);
insert into medication_lacking_event(id, employee_id, employee_type, event_date, medication_id, pharmacy_id)
values (2, 4,0, '2020-12-25', 3,1);
insert into medication_lacking_event(id, employee_id, employee_type, event_date, medication_id, pharmacy_id)
values (3, 1,1, '2021-02-03', 4,1);

insert into supplier (id,country , latitude, longitude,street, town,phone_number,email,password,  first_name, last_name, user_type)
VALUES (1,'SRB', 41, 87, 'Avenue 3rd', 'Belgrade',  '0605435487','jovancica@gmail.com','jole123','Jovana', 'Markovic',1);

insert into supplier_medication_quantity(supplier_id, medication_quantity_id)
VALUES(1,1);
insert into supplier_medication_quantity(supplier_id, medication_quantity_id)
VALUES(1,2);
insert into supplier_medication_quantity(supplier_id, medication_quantity_id)
VALUES(1,10);
insert into supplier_medication_quantity(supplier_id, medication_quantity_id)
VALUES(1,11);
insert into supplier_medication_quantity(supplier_id, medication_quantity_id)
VALUES(1,13);

insert into medication_offer(id, cost,shipping_date,status,medication_order_id)
VALUES(1,111000,'2020-12-31',1,1);
insert into medication_offer(id, cost,shipping_date,status,medication_order_id)
VALUES(2,96,'2022-02-03',0,2);


insert into supplier_medication_offer(supplier_id, medication_offer_id)
VALUES(1,2);
insert into promotion(id, content, period_end, period_start, pharmacy_id)
values (1, 'Ovi lekovi su na popustu : Bromazepan, Aspirin, Xanax', '2021-03-1', '2021-01-01', 1);
insert into promotion_medications_on_promotion(promotion_id, medications_on_promotion_id)
values (1, 0);
insert into promotion_medications_on_promotion(promotion_id, medications_on_promotion_id)
values (1, 1);
insert into promotion_medications_on_promotion(promotion_id, medications_on_promotion_id)
values (1, 4);

insert into patient_promotions(patient_id, promotions_id) values (0,1);

-- SELECT MAX(id) FROM appointment;
--
-- SELECT nextval('bookmarks_id_seq');


-- AKO NEKO PROMENI PICKU CU MU POLOMITI!!!!!!
INSERT INTO eprescription(id, date_issued, patient_id, status) VALUES (0, '2021-03-01', 0, 0);
INSERT INTO eprescription(id, date_issued, patient_id, status) VALUES (1, '2021-03-02', 0, 1);
INSERT INTO eprescription(id, date_issued, patient_id, status) VALUES (2, '2021-03-03', 0, 2);
INSERT INTO eprescription(id, date_issued, patient_id, status) VALUES (3, '2021-03-04', 0, 0);
INSERT INTO eprescription(id, date_issued, patient_id, status) VALUES (4, '2021-03-05', 0, 1);
INSERT INTO eprescription(id, date_issued, patient_id, status) VALUES (5, '2021-03-06', 0, 2);

INSERT INTO medication_quantity (id, quantity, medication_id) VALUES (30, 5, 0);
INSERT INTO medication_quantity (id, quantity, medication_id) VALUES (31, 1, 1);
INSERT INTO medication_quantity (id, quantity, medication_id) VALUES (32, 2, 2);
INSERT INTO medication_quantity (id, quantity, medication_id) VALUES (33, 3, 3);
INSERT INTO medication_quantity (id, quantity, medication_id) VALUES (34, 1, 4);
INSERT INTO medication_quantity (id, quantity, medication_id) VALUES (35, 4, 3);

INSERT INTO eprescription_medication_quantity(eprescription_id, medication_quantity_id) VALUES (0, 30);
INSERT INTO eprescription_medication_quantity(eprescription_id, medication_quantity_id) VALUES (1, 31);
INSERT INTO eprescription_medication_quantity(eprescription_id, medication_quantity_id) VALUES (2, 32);
INSERT INTO eprescription_medication_quantity(eprescription_id, medication_quantity_id) VALUES (3, 33);
INSERT INTO eprescription_medication_quantity(eprescription_id, medication_quantity_id) VALUES (4, 34);
INSERT INTO eprescription_medication_quantity(eprescription_id, medication_quantity_id) VALUES (5, 35);

-- pharmacy grade
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (1, 3, 0, 1, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (2, 4, 0, 1, 6);
-- pharmacist grame
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (3, 5, 1, 1, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (4, 2, 1, 1, 6);
insert into grade(id, grade, grade_type, graded_id, patient_id)
     values (5, 5, 1, 2, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
     values (6, 3, 1, 2, 6);
-- dermatologist grade
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (7, 5, 2, 3, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (8, 2, 2, 4, 6);
insert into grade(id, grade, grade_type, graded_id, patient_id)
     values (9, 5, 2, 5, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
     values (10, 3, 2, 3, 6);
--medication grade
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (11, 4, 3, 0, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (12, 3, 3, 1, 6);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (13, 2, 3, 2, 0);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (14, 1, 3, 3, 6);
insert into grade(id, grade, grade_type, graded_id, patient_id)
    values (15, 3, 3, 4, 6);

insert into side_effect(id,name)
values(0,'allergy');
insert into side_effect(id,name)
values(1,'dry mouth');
insert into side_effect(id,name)
values(2,'internal bleeding');
insert into side_effect(id,name)
values(3,'lost of concentration');
insert into side_effect(id,name)
values(4,'aggression');
insert into side_effect(id,name)
values(5,'diarrhea');
insert into side_effect(id,name)
values(6,'diarrhea');
insert into side_effect(id,name)
values(7,'nausea');
insert into side_effect(id,name)
values(8,'migraine');
insert into side_effect(id,name)
values(9,'dizziness');
insert into side_effect(id,name)
values(10,'insomnia');

insert into complaint(id,complainee_id,content,type,patient_id)
values(0,1,'Bezobrazan, nadmen.',0,0);
insert into complaint(id,complainee_id,content,type,patient_id)
values(1,3,'Nece da da recept!',1,0);

