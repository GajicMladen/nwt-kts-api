insert into vehicle_price(type, price) values(0, 250);
insert into vehicle_price(type, price) values(1, 450);
insert into vehicle_price(type, price) values(2, 350);
insert into vehicle_price(type, price) values(3, 300);
insert into vehicle_price(type, price) values(4, 400);

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Driver', 'm','m','Mladen','Gajic' , '0644281080');

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Driver', 'j','j','Jovan','Tomic' , '06965466');

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Driver', 'd','d','Djosa','Jovanovic' , '0666595632');

insert into users( dtype , email ,password ,name ,lastname ,phone ,active )
values('Client', 'c','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Marko','Markovic' , '0644281080',true);

insert into users( dtype , email ,password ,name ,lastname ,phone,active  )
values('Client', 'a','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Nikola','Nikolic' , '0644281080',true);

insert into users( dtype , email ,password ,name ,lastname ,phone ,active )
values('Client', 'b','$2a$10$XeS1WZloSVVq2Z2dJd3L7ePADJy51sWu/oLqcy.Qcmppr6VcUtcr6','Lazar','Lazarovic' , '0644281080',true);

insert into users( dtype , email ,password ,name ,lastname ,phone, active  )
values('Client', 'p3r5kul45@gmail.com','lozinka1','Jovan','Tomic' , '0644281080', true);

insert into users( dtype , email ,password ,name ,lastname ,phone, active  )
values('Client', 'jtomic1@gmail.com','$2a$10$0W7w6ZXLQPXonylfuYH80uuLPeM/v52yAs37OHvhbrkqQi7Ki8b4q','Jovan','Tomic' , '0644281080', true);


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

INSERT INTO USER_ROLE(user_id, role_id) VALUES (4, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (5, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (6, 2);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (7, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (8, 1);
INSERT INTO USER_ROLE(user_id, role_id) VALUES (5, 2);
