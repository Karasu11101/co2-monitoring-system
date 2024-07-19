INSERT INTO user_data (fullName, username, email, password)
VALUES
    ('John Doe', 'johndoe', 'johndoe@example.com', '$2a$10$PJRx46gBKAZj7SbqtpZnPOA58HFWimsP.0uogPzaNlFtrrB0bsnfi');


INSERT INTO user_data (fullName, username, email, password)
VALUES
    ('Viviane', 'Lady of the Lake', 'nimue@example.com', '$2a$10$PJRx46gBKAZj7SbqtpZnPOA58HFWimsP.0uogPzaNlFtrrB0bsnfi');


INSERT INTO user_data (fullName, username, email, password)
VALUES
    ('Merlin Ambrosius', 'emrys', 'wild@example.com', '$2a$10$PJRx46gBKAZj7SbqtpZnPOA58HFWimsP.0uogPzaNlFtrrB0bsnfi');


INSERT INTO user_data (fullName, username, email, password)
VALUES
    ('Jane Doe', 'janedoe', 'janedoe@example.com', '$2a$10$PJRx46gBKAZj7SbqtpZnPOA58HFWimsP.0uogPzaNlFtrrB0bsnfi');


INSERT INTO authorities (role, id_user_fk)
VALUES
    ('ROLE_ADMIN', 1);


INSERT INTO authorities (role, id_user_fk)
VALUES
    ('ROLE_ADMIN', 2);


INSERT INTO authorities (role, id_user_fk)
VALUES
    ('ROLE_ADMIN', 3);


INSERT INTO authorities (role, id_user_fk)
VALUES
    ('ROLE_USER', 4);


INSERT INTO city (name)
VALUES
    ('Barcelona');


INSERT INTO city (name)
VALUES
    ('Wien');


INSERT INTO city (name)
VALUES
    ('München');


INSERT INTO district (name, id_city_fk)
VALUES
    ('Gràcia', 1);


INSERT INTO district (name, id_city_fk)
VALUES
    ('Eixample', 1);


INSERT INTO district (name, id_city_fk)
VALUES
    ('Währing', 2);


INSERT INTO district (name, id_city_fk)
VALUES
    ('Penzing', 2);


INSERT INTO district (name, id_city_fk)
VALUES
    ('Maxvorstadt', 3);


INSERT INTO sensor (name, id_district_fk)
VALUES
    ('Gràcia sensor n.001', 1);


INSERT INTO sensor (name, id_district_fk)
VALUES
    ('Eixample sensor n.001', 2);


INSERT INTO sensor (name, id_district_fk)
VALUES
    ('Währing sensor n.001', 3);


INSERT INTO sensor (name, id_district_fk)
VALUES
    ('Penzing sensor n.001', 4);


INSERT INTO sensor (name, id_district_fk)
VALUES
    ('Maxvorstadt sensor n.001', 5);


INSERT INTO co2_reading (id_sensor_fk, username_fk, ppm, record_date)
VALUES
    (
        (SELECT id FROM sensor WHERE id = 1),
        (SELECT username FROM user_data WHERE username = 'johndoe'),
        350.5,
        now()
    );


INSERT INTO co2_reading (id_sensor_fk, username_fk, ppm, record_date)
VALUES
    (
        (SELECT id FROM sensor WHERE id = 2),
        (SELECT username FROM user_data WHERE username = 'johndoe'),
        245.5,
        now()
    );


INSERT INTO co2_reading (id_sensor_fk, username_fk, ppm, record_date)
VALUES
    (
        (SELECT id FROM sensor WHERE id = 3),
        (SELECT username FROM user_data WHERE username = 'Lady of the Lake'),
        350.5,
        now()
    );


INSERT INTO co2_reading (id_sensor_fk, username_fk, ppm, record_date)
VALUES
    (
        (SELECT id FROM sensor WHERE id = 4),
        (SELECT username FROM user_data WHERE username = 'Lady of the Lake'),
        245.5,
        now()
    );


INSERT INTO co2_reading (id_sensor_fk, username_fk, ppm, record_date)
VALUES
    (
        (SELECT id FROM sensor WHERE id = 5),
        (SELECT username FROM user_data WHERE username = 'emrys'),
        350.5,
        now()
    );
