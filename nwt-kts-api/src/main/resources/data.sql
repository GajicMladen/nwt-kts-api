insert into vehicle_price(type, price) values(0, 250);
insert into vehicle_price(type, price) values(1, 450);
insert into vehicle_price(type, price) values(2, 350);
insert into vehicle_price(type, price) values(3, 300);
insert into vehicle_price(type, price) values(4, 400);

insert into users( dtype , email ,password ,name ,lastname ,phone, active, profile_photo, town)
values('Driver', 'm','m','Mladen','Gajic' , '0644281080', true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone, active, profile_photo, town  )
values('Driver', 'j','j','Jovan','Tomic' , '06965466', true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone, active, profile_photo, town  )
values('Driver', 'd','d','Djosa','Jovanovic' , '0666595632', true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone ,active, profile_photo, town )
values('Client', 'c','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Marko','Markovic' , '0644281080',true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone,active, profile_photo, town  )
values('Client', 'a','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Nikola','Nikolic' , '0644281080',true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone ,active, profile_photo, town )
values('Client', 'djordjejovanovic27@gmail.com','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Lazar','Lazarovic' , '0644281080',true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone, active, profile_photo, town  )
values('Driver', 'p3r5kul45@gmail.com','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Jovan','Tomic' , '0644281080', true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into users( dtype , email ,password ,name ,lastname ,phone, active, profile_photo, town  )
values('Client', 'jtomic1@gmail.com','$2a$10$0W7w6ZXLQPXonylfuYH80uuLPeM/v52yAs37OHvhbrkqQi7Ki8b4q','Jovan','Tomic' , '0644281080', true, 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Novi Sad');

insert into coordinate(latitude, longitude) values (45.249602, 19.849632);
insert into coordinate(latitude, longitude) values (45.235220255076584, 19.83856247725572);
insert into coordinate(latitude, longitude) values (45.254820, 19.852750);

insert into vehicle(name, plate_number, capacity, type)
values('Toyota Prius', 'NS001TX', 3, 0);
insert into vehicle(name, plate_number, capacity, type)
values('Toyota Prius', 'NS002TX', 3, 0);
insert into vehicle(name, plate_number, capacity, type)
values('Toyota Prius', 'NS003TX', 4, 0);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka1', 0, TO_TIMESTAMP('2022-02-09 07:00:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('admin poruka 1 ', 1, TO_TIMESTAMP('2022-02-09 07:01:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka2', 0, TO_TIMESTAMP('2022-02-09 07:01:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka3', 0, TO_TIMESTAMP('2022-02-09 07:01:30', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka4', 0, TO_TIMESTAMP('2022-02-09 07:01:30', 'YYYY-MM-DD HH24:MI:SS') ,2);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka5', 0, TO_TIMESTAMP('2022-02-09 07:02:30', 'YYYY-MM-DD HH24:MI:SS') ,3);

-- ROLE	
INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_DRIVER');


INSERT INTO USER_ROLE(user_id, role_id) VALUES (1, 3);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (2, 3);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (3, 3);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (4, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (5, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (6, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (7, 3);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (8, 2);
-- INSERT INTO USER_ROLE(user_id, role_id) VALUES (5, 2);

INSERT INTO FARE (driver_id, price, is_accepted, is_reservation, distance, is_active)
VALUES (1, 626, true, false, 3133.3, true);

INSERT INTO CLIENTS_FOR_FARE(fare_id, client_id) VALUES (1, 4);

insert into coordinate(latitude, longitude) values (45.259699, 19.850104);
insert into coordinate(latitude, longitude) values (45.240284, 19.845228);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (1, 4);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (1, 5);

-- ZAHTEVI ZA IZMENU VOZACA
INSERT INTO DRIVER_CHANGE_REQUEST(resolved, last_name, name, phone, profile_photo, town, user_id)
VALUES (false, 'Maric', 'Miomir', '0601234567', 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Kula', 7);

INSERT INTO DRIVER_CHANGE_REQUEST(resolved, last_name, name, phone, profile_photo, town, user_id)
VALUES (false, 'Stupar', 'Mirko', '0607564321', 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Crvenka', 1);

INSERT INTO DRIVER_CHANGE_REQUEST(resolved, last_name, name, phone, profile_photo, town, user_id)
VALUES (false, 'Mihailovic', 'Ivan', '0625458867', 'https://i.ibb.co/VCfhmKQ/image.jpg', 'Sivac', 2);

-- KOORDINATE
insert into coordinate(latitude, longitude) values (45.259699, 19.850104);
insert into coordinate(latitude, longitude) values (45.240284, 19.845228);

INSERT INTO COORDINATE(latitude, longitude, name, stop_number) VALUES (45.24286562719128, 19.842105547654754, 'Bulevar oslobodjenja', 1);
INSERT INTO COORDINATE(latitude, longitude, name, stop_number) VALUES (45.24806268406050, 19.8167046783952, 'Polgar Andrasa 1', 2);
INSERT INTO COORDINATE(latitude, longitude, name, stop_number) VALUES (45.24020648400466, 19.82242163041011, 'Bulevar Evrope 4', 1);
INSERT INTO COORDINATE(latitude, longitude, name, stop_number) VALUES (45.260509514725584, 19.83358742103717, 'Булевар краља Петра I 13', 2);
INSERT INTO COORDINATE(latitude, longitude, name, stop_number) VALUES (45.24383255749274, 19.84174186750492, 'Radomira Rase Radujkova', 1);
INSERT INTO COORDINATE(latitude, longitude, name, stop_number) VALUES (45.25525317629811, 19.81126309153317, 'Булевар краља Петра I 13', 2);

-- VOZNJE
INSERT INTO FARE(driver_id, price, request_time, start_time, end_time, is_accepted, calculate_shortest, is_reservation, distance, estimated_time_left, is_active)
VALUES (7, 300, TO_TIMESTAMP('2023-01-22 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-01-22 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-01-22 08:10:00', 'YYYY-MM-DD HH24:MI:SS'), true, false, false, 10000, 0, false);

INSERT INTO FARE(driver_id, price, request_time, start_time, end_time, is_accepted, calculate_shortest, is_reservation, distance, estimated_time_left, is_active)
VALUES (7, 400, TO_TIMESTAMP('2023-01-22 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-01-22 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-01-22 09:10:00', 'YYYY-MM-DD HH24:MI:SS'), true, false, false, 5000, 0, false);

INSERT INTO FARE(driver_id, price, request_time, start_time, end_time, is_accepted, calculate_shortest, is_reservation, distance, estimated_time_left, is_active)
VALUES (3, 350, TO_TIMESTAMP('2023-01-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-01-22 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-01-22 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), true, false, false, 7500, 0, false);

-- STOPS
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (1, 4);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (1, 5);

INSERT INTO STOPS(fare_id, coordinates_id) VALUES (2, 6);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (2, 7);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (3, 8);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (3, 9);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (4, 10);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (4, 11);

-- CLIENTS_FOR_FARE
INSERT INTO CLIENTS_FOR_FARE(fare_id, client_id) VALUES (2, 6);
INSERT INTO CLIENTS_FOR_FARE(fare_id, client_id) VALUES (3, 6);
INSERT INTO CLIENTS_FOR_FARE(fare_id, client_id) VALUES (4, 6);