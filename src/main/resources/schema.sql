DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS user_data;

CREATE TABLE IF NOT EXISTS user_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fullName VARCHAR NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    role VARCHAR NOT NULL,
    id_user_fk BIGINT NOT NULL,
    PRIMARY KEY(role, id_user_fk),
    FOREIGN KEY (id_user_fk) REFERENCES user_data(id) ON DELETE CASCADE
);