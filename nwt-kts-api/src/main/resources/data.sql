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

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Client', 'c','c','Marko','Markovic' , '0644281080');

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Client', 'a','a','Nikola','Nikolic' , '0644281080');

insert into users( dtype , email ,password ,name ,lastname ,phone  )
values('Client', 'b','b','Lazar','Lazarovic' , '0644281080');

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka1', 0, TO_TIMESTAMP('2022-02-09 07:00:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka2', 0, TO_TIMESTAMP('2022-02-09 07:01:00', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka3', 0, TO_TIMESTAMP('2022-02-09 07:01:30', 'YYYY-MM-DD HH24:MI:SS') ,1);

INSERT INTO MESSAGE("CONTENT","ADMIN_MESSAGE","TIME_STAMP","USER_ID")
VALUES('poruka4', 0, TO_TIMESTAMP('2022-02-09 07:01:30', 'YYYY-MM-DD HH24:MI:SS') ,2);