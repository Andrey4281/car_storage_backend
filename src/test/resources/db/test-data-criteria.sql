INSERT INTO cars (id, category, brand, engine, transmission, carcass)
VALUES (4, 'criteriaTest', 'criteriaTest', 'criteriaTest', 'criteriaTest', 'criteriaTest'),
       (5, 'criteriaTestOther', 'criteriaTestOther', 'criteriaTestOther', 'criteriaTestOther', 'criteriaTestOther');
INSERT INTO  users (id, login, phone, password)
VALUES (4, 'criteriaTest', '89001999455','criteriaTest'),
       (5, 'criteriaTestOther', '89001999456', 'criteriaTestOther');
INSERT INTO users_roles (id, user_id, role_id)
VALUES (4, 4, 2),
       (5, 5, 2);
INSERT INTO adverts (id, description, status, user_id, car_id)
VALUES (4, 'criteriaTest', false, 4, 4),
       (5, 'criteriaTestOther', true, 5, 5);


