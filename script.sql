-- use test;

-- create table Orders (
-- 	id int primary key auto_increment,
--     start_longitude double,
--     start_latitude double,
--     end_longitude double,
--     end_latitude double,
--     order_status varchar(10) check (order_status = 'PENDING' or order_status = 'DELIVERED')
-- )auto_increment=1;

-- create table Rider (
-- 	id int primary key auto_increment,
--     longitude double,
--     latitude double,
--     rider_status varchar(10) check (rider_status = 'FREE' or rider_status = 'ON_THE_WAY'),
--     order_id int,
--     constraint FK_ORDER_ID foreign key (order_id) references Orders (id)
-- ) auto_increment=1;

-- insert into Rider (longitude, latitude, rider_status) values 
-- (6.0, 33.5, 'FREE'),
-- (36.3, 36.0, 'FREE'),
-- (17.8, 6.0, 'FREE'),
-- (22.5, 11.7, 'FREE');

-- insert into Orders (start_longitude, start_latitude, end_longitude, end_latitude, order_status) values
-- (22.0, 9.1, 44.3, 28.3, 'PENDING'),
-- (38.0, 32.4, 43.2, 13.0, 'PENDING'),
-- (5.3, 38.7, 13.6, 5.5, 'PENDING');

select r.id, min(sqrt(power((o.start_longitude - r.longitude), 2) + power((o.start_latitude - r.latitude), 2))) as distance from Rider r, Orders o where r.rider_status = 'FREE' and o.id = 1 group by r.id having distance <= 5.0 order by distance asc limit 0,1;

select o.id, sqrt(power((o.start_longitude - r.longitude), 2) + power((o.start_latitude - r.latitude), 2)) as distance from Rider r, Orders o where o.order_status = 'PENDING' and r.id = 4 group by o.id having distance <= 5.0 order by distance asc limit 0,1;