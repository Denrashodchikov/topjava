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

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '2024-06-23 18:30:20', 'Test desc', 1000),
       (100001, CURRENT_TIMESTAMP - (2 * interval '1 day'), 'Test desc 2', 2000),
       (100000, '2023-12-31 11:11:11', 'Dublicate desc Dub', 321),
       (100000, '2024-01-01 12:30:20', 'Dublicate desc', 123);