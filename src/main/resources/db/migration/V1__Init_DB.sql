CREATE TABLE users
(
id VARCHAR (36) PRIMARY KEY,
password VARCHAR (64),
name VARCHAR (128),
role VARCHAR (32)
);

CREATE TABLE messages
(
id VARCHAR (36) PRIMARY KEY,
text_m VARCHAR (1024),
user_reciever_fk VARCHAR (36),
user_sender_fk VARCHAR (36),
time_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_sender_fk) REFERENCES users (id)
);

CREATE TABLE games
(
id VARCHAR (36) PRIMARY KEY,
price INT,
name VARCHAR (128),
url VARCHAR (256)
);

CREATE TABLE game_users
(
game_user_pk VARCHAR (36) PRIMARY KEY,
game_fk VARCHAR (36),
user_fk VARCHAR (36),
FOREIGN KEY (game_fk) REFERENCES games (id),
FOREIGN KEY (user_fk) REFERENCES users (id)
);

CREATE TABLE reviews
(
id VARCHAR (36) PRIMARY KEY,
text_review VARCHAR (1024),
asessment REAL,
user_fk VARCHAR (36),
game_fk VARCHAR (36),
FOREIGN KEY (user_fk) REFERENCES users (id),
FOREIGN KEY (game_fk) REFERENCES games (id)
);