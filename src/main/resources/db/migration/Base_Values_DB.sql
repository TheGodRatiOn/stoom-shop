/*
INSERT INTO users(id, password, name, role) VALUES ('24979136-39eb-4b09-acd7-a0765cc7f90f','$2a$10$C19qKkqS.XnLJTi3yPCtbOJD7k2amzGnib7MYKmLeknehZpx5eGK2', 'user1','ROLE_ADMIN');
INSERT INTO users(id, password, name, role) VALUES ('b5a9f645-00dd-4230-8f44-835770d25527', '$2a$10$d6JoFPgUv89dlpuTyIens.DaHN8fI.Wz/GuDozHOsl7rBqi5/k5Zq', 'user2','ROLE_PUBLISHER');
INSERT INTO users(id, password, name, role) VALUES ('793cf14b-a2c8-4fff-910d-1fbca4e09127', '$2a$10$NIvQeMslGmfMwVB1yzMGv.tTYjzLA4rz7O/i0/OiWFVssoYLHHMUy', 'user3','ROLE_EMPLOYEE');
INSERT INTO users(id, password, name, role) VALUES ('e2614806-b55d-4270-b03a-05a6f15141a8', '$2a$10$dUMAnZG.BR1vl7dWZnA6wO9xkz/Ilk8yW25yEmzjPF/ZX9IKbxpNi', 'user4', 'ROLE_CUSTOMER');
INSERT INTO users(id, password, name, role) VALUES ('29fe5e85-799f-4097-8894-71d699d0009c', '$2a$10$FK.IIvdArKmrfHlImyAW/ej4PLewyyqkhgWo5M2IOUCgQuJ/RXhaO', 'user5','ROLE_CUSTOMER');
*/
INSERT INTO games(id, price, name, url) VALUES ('2db9334c-b217-4da2-a70e-68638678295d', 1199, 'The Witcher 3 Wild Hunt', 'https://i.playground.ru/p/Av5WiHYhH25BIRz3dCdaeg.jpeg');
INSERT INTO games(id, price, name, url) VALUES ('284655a3-c358-4d25-bf35-933423efa7ec', 2999, 'Call Of Duty Modern Warfare', 'https://i14.kanobu.ru/c/73d5f022fd0705456d59771fc5482494/200x284/u.kanobu.ru/games/80639295-496e-4eff-880c-7046004b021c.jpg');
INSERT INTO games(id, price, name, url) VALUES ('7a4cfa58-c229-4ed8-85ca-7be700c02697', 3999, 'Call Of Duty Cold War', 'https://ru.wikipedia.org/wiki/Call_of_Duty:_Black_Ops_Cold_War#/media/Файл:Black_Ops_–_Cold_War_Cover.jpeg');
INSERT INTO games(id, price, name, url) VALUES ('2c8bd654-4034-4abf-b8d1-b0075886d7b7', 1999, 'Divinity Original Sin 2 Definitive Edition', 'https://cdn.cloudflare.steamstatic.com/steam/apps/435150/header.jpg?t=1592914193');
INSERT INTO games(id, price, name, url) VALUES ('0851ed2b-7ab4-4882-9150-faba7dc19763', 2599, 'Far Cry 5 Gold Edition', 'https://cdn1.ozone.ru/s3/multimedia-d/wc1200/6006558637.jpg');

INSERT INTO game_users(game_user_pk, game_fk, user_fk) VALUES ('6103c949-36e0-4194-a575-a29c0f7a5c9c', '284655a3-c358-4d25-bf35-933423efa7ec', 'e2614806-b55d-4270-b03a-05a6f15141a8');
INSERT INTO game_users(game_user_pk, game_fk, user_fk) VALUES ('9be7262b-e091-47bb-b5b1-0bacbc7f2f39', '7a4cfa58-c229-4ed8-85ca-7be700c02697', 'e2614806-b55d-4270-b03a-05a6f15141a8');
INSERT INTO game_users(game_user_pk, game_fk, user_fk) VALUES ('f2f7a1d6-4fa9-4963-8fb7-819ec43d5986', '2db9334c-b217-4da2-a70e-68638678295d', '29fe5e85-799f-4097-8894-71d699d0009c');
INSERT INTO game_users(game_user_pk, game_fk, user_fk) VALUES ('e88335a2-1625-45ea-9af7-6f68b63a9115', '2c8bd654-4034-4abf-b8d1-b0075886d7b7', '29fe5e85-799f-4097-8894-71d699d0009c');
INSERT INTO game_users(game_user_pk, game_fk, user_fk) VALUES ('df1b73e5-b9ad-40cd-aeaa-4b155ad50260', '0851ed2b-7ab4-4882-9150-faba7dc19763', '793cf14b-a2c8-4fff-910d-1fbca4e09127');

INSERT INTO messages(id, text_m, user_sender_fk, user_reciever_fk) VALUES ('1ffa0596-f545-4c92-875d-9f9114aa3318', 'LOL HAVE YOU SEEN THAT BS?!', '29fe5e85-799f-4097-8894-71d699d0009c', 'e2614806-b55d-4270-b03a-05a6f15141a8');
INSERT INTO messages(id, text_m, user_sender_fk, user_reciever_fk) VALUES ('a464b2ba-46c5-41dd-aeb9-05196a389602', 'No idea how did you managed to', 'e2614806-b55d-4270-b03a-05a6f15141a8', '29fe5e85-799f-4097-8894-71d699d0009c');

INSERT INTO reviews(id, text_review, asessment, game_fk, user_fk) VALUES ('c1b9d063-6049-4084-9378-2445ec8ff4f5', 'THE BEST GAME OF ALL TIME EXCEPT I HATE LOOTBOXES', 4.5, '7a4cfa58-c229-4ed8-85ca-7be700c02697', 'e2614806-b55d-4270-b03a-05a6f15141a8');