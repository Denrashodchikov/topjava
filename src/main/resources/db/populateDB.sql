DELETE
FROM meals;
DELETE
FROM user_role;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2024-06-23 18:30:20', 'First', 1000),
       (100000, '2024-06-23 12:30:20', 'First', 123),
       (100000, '2024-06-23 11:11:11', 'First', 321),
       (100000, '2024-01-01 20:00:00', 'First', 200),
       (100000, '2024-01-01 15:30:40', 'First', 150),
       (100000, '2024-01-01 14:10:50', 'First', 1230),
       (100000, '2024-01-01 13:15:20', 'First', 1000),
       (100000, '2024-01-01 12:30:20', 'First', 800),
       (100001, '2024-02-04 10:00:00', 'Second', 1000),
       (100001, '2024-02-02 10:00:00', 'Second', 1000),
       (100001, '2024-02-02 21:00:00', 'Second', 1700);
