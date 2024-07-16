CREATE TABLE IF NOT EXISTS user_data (
    id VARCHAR PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    password VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    role VARCHAR NOT NULL,
    id_user_fk VARCHAR NOT NULL,
    PRIMARY KEY(role, id_user_fk),
    FOREIGN KEY (id_user_fk) REFERENCES user_data(id) ON DELETE CASCADE
);