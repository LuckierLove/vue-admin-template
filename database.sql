CREATE TABLE User(
    id CHAR(32) NOT NULL PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    email VARCHAR(64) NOT NULL,
    role VARCHAR(255),
    gmt_create DATETIME NOT NULL,
    gmt_update DATETIME NOT NULL
);