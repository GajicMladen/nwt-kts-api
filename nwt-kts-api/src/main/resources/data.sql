insert into vehicle_price(type, price) values(0, 250);
insert into vehicle_price(type, price) values(1, 450);
insert into vehicle_price(type, price) values(2, 350);
insert into vehicle_price(type, price) values(3, 300);
insert into vehicle_price(type, price) values(4, 400);

insert into coordinate(latitude, longitude) values (45.249602, 19.849632);
insert into coordinate(latitude, longitude) values (45.254820, 19.852750);
insert into coordinate(latitude, longitude) values (45.254820, 19.852750);

insert into vehicle(name, plate_number, capacity, type)
values('Toyota Prius', 'NS001TX', 3, 0);
insert into vehicle(name, plate_number, capacity, type)
values('Toyota Prius', 'NS002TX', 3, 0);
insert into vehicle(name, plate_number, capacity, type)
values('Toyota Prius', 'NS003TX', 4, 0);

insert into users(active, dtype , email ,password ,name ,lastname ,phone, driver_status, vehicle_id, position_id)
values(true, 'Driver', 'm','m','Mladen','Gajic' , '0644281080', 2, 1, 3);

insert into users(active, dtype , email ,password ,name ,lastname ,phone, driver_status, vehicle_id, position_id)
values(true, 'Driver', 'j','j','Jovan','Tomic' , '06965466', 1, 2, 1);

insert into users(active, dtype , email ,password ,name ,lastname ,phone, driver_status, vehicle_id, position_id)
values(true, 'Driver', 'd','d','Djosa','Jovanovic' , '0666595632', 1, 3, 2);

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Client', 'c','c','Marko','Markovic' , '0644281080');

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Client', 'a','a','Nikola','Nikolic' , '0644281080');

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Client', 'b','b','Lazar','Lazarovic' , '0644281080');

insert into users( dtype , email ,password ,name ,lastname ,phone, active  )
values('Client', 'p3r5kul45@gmail.com','lozinka1','Jovan','Tomic' , '0644281080', true);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka1', 0, TO_TIMESTAMP('2022-02-09 07:00:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka2', 0, TO_TIMESTAMP('2022-02-09 07:01:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka3', 0, TO_TIMESTAMP('2022-02-09 07:01:30', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka4', 0, TO_TIMESTAMP('2022-02-09 07:01:30', 'YYYY-MM-DD HH24:MI:SS') ,2);

-- ROLE	
INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_DRIVER');

INSERT INTO USER_ROLE(user_id, role_id) VALUES (4, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (5, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (6, 2);

INSERT INTO FARE (driver_id, price, is_accepted, is_reservation, distance, is_active)
VALUES (1, 626, true, false, 3133.3, true);

INSERT INTO CLIENTS_FOR_FARE(fare_id, client_id) VALUES (1, 4);

insert into coordinate(latitude, longitude) values (45.259699, 19.850104);
insert into coordinate(latitude, longitude) values (45.240284, 19.845228);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (1, 4);
INSERT INTO STOPS(fare_id, coordinates_id) VALUES (1, 5);