CREATE TABLE message
(
id VARCHAR (36) PRIMARY KEY,
text_m VARCHAR (1024),
time_date TIMESTAMP,
user_reciever_fk VARCHAR (36),
user_sender_fk VARCHAR (36),
FOREIGN KEY (user_sender_fk) REFERENCES user (id)
);

CREATE TABLE user
(
id VARCHAR (36) PRIMARY KEY,
name VARCHAR (128),
role VARCHAR (32),
FOREIGN KEY (id) REFERENCES review (user_fk)
);

CREATE TABLE game_user
(
game_fk VARCHAR (36) PRIMARY KEY,
user_fk VARCHAR (36),
FOREIGN KEY (game_fk) REFERENCES game (id),
FOREIGN KEY (user_fk) REFERENCES user (id)
);

CREATE TABLE game
(
id VARCHAR (36) PRIMARY KEY,
name VARCHAR (128),
url VARCHAR (256),
price INT,
FOREIGN KEY (id) REFERENCES review (game_fk)
);

CREATE TABLE review
(
id VARCHAR (36) PRIMARY KEY,
text_review VARCHAR (1024),
assessment REAL,
user_fk VARCHAR (36),
game_fk VARCHAR (36),
FOREIGN KEY (user_fk) REFERENCES user (id),
FOREIGN KEY (game_fk) REFERENCES game (id)
);