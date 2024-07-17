INSERT INTO user_data (fullName, username, email, password)
VALUES ('Cartman',
        'mage111',
        'test@test.com',
        '3eb3fe66b31e3b4d10fa70b5cad49c7112294af6ae4e476a1c405155d45aa121');

INSERT INTO authorities (role, id_user_fk)
VALUES ('ADMIN', 1);