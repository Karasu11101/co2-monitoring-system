INSERT INTO user_data (fullName, username, email, password)
VALUES
    ('John Doe', 'johndoe', 'johndoe@example.com', '$2a$10$PJRx46gBKAZj7SbqtpZnPOA58HFWimsP.0uogPzaNlFtrrB0bsnfi');


INSERT INTO authorities (role, id_user_fk)
VALUES
    ('ROLE_ADMIN', 1);

INSERT INTO city (name)
VALUES
    ('Barcelona');


INSERT INTO district (name, id_city_fk)
VALUES
    ('Gràcia', 1);


INSERT INTO sensor (name, id_district_fk)
VALUES
    ('Gràcia sensor n.001', 1);


INSERT INTO co2_reading (id_sensor_fk, username_fk, ppm, record_date)
VALUES
    (
        (SELECT id FROM sensor WHERE id = 1),
        (SELECT username FROM user_data WHERE username = 'johndoe'),
        350.5,
        now()
    );
