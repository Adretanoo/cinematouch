-- Movies (UA)
INSERT INTO movie (title, duration, genre, rating, description, poster_image_url)
VALUES ('Початок', 148, 'Наукова фантастика', 'PG-13', 'Фільм Нолана про сни.',
        '/img/inception.jpg'),
       ('Аватар', 162, 'Фентезі', 'PG-13', 'Пригода на Пандорі.', '/img/avatar.jpg'),
       ('Інтерстеллар', 169, 'Фантастика', 'PG-13', 'Космічна подорож за межі галактик.',
        '/img/interstellar.jpg'),
       ('Матриця', 136, 'Бойовик', 'R', 'Що є реальністю?', '/img/matrix.jpg'),
       ('Дюна', 155, 'Фентезі', 'PG-13', 'Епічна історія виживання.', '/img/dune.jpg');

-- Movie translations (EN)
INSERT INTO movie_translation (movie_id, language_code, translated_title, translated_description)
VALUES (1, 'en', 'Inception', 'A mind-bending thriller.'),
       (2, 'en', 'Avatar', 'An epic journey on Pandora.'),
       (3, 'en', 'Interstellar', 'A voyage beyond the stars.'),
       (4, 'en', 'The Matrix', 'What is reality?'),
       (5, 'en', 'Dune', 'A grand tale of survival and prophecy.');

-- Sessions
INSERT INTO session (movie_id, start_time, hall_number)
VALUES (1, '2025-05-06 18:00:00', 1),
       (1, '2025-05-06 21:00:00', 1),
       (2, '2025-05-06 20:30:00', 2),
       (3, '2025-05-07 17:00:00', 3),
       (4, '2025-05-07 19:00:00', 1);

-- Seats
INSERT INTO seat (hall_number, row, seat_number)
VALUES (1, 'A', 1),
       (1, 'A', 2),
       (1, 'B', 1),
       (2, 'C', 5),
       (3, 'D', 3);

-- Tickets
INSERT INTO ticket (session_id, seat_id, price, printed)
VALUES (1, 1, 150.00, FALSE),
       (1, 2, 150.00, TRUE),
       (2, 3, 180.00, FALSE),
       (3, 4, 160.00, FALSE),
       (4, 5, 200.00, TRUE);

-- Food items (UA)
INSERT INTO food_item (name, price, available)
VALUES ('Попкорн', 50.00, TRUE),
       ('Кола', 35.00, TRUE),
       ('Хот-дог', 40.00, TRUE),
       ('Сік', 25.00, TRUE),
       ('Начос', 45.00, FALSE);

-- Food translations (EN)
INSERT INTO food_item_translation (food_item_id, language_code, translated_name)
VALUES (1, 'en', 'Popcorn'),
       (2, 'en', 'Cola'),
       (3, 'en', 'Hot Dog'),
       (4, 'en', 'Juice'),
       (5, 'en', 'Nachos');

-- Orders
INSERT INTO orders (ticket_id, total_price, payment_status)
VALUES (1, 200.00, 'PAID'),
       (2, 185.00, 'PENDING'),
       (3, 180.00, 'CANCELLED'),
       (4, 160.00, 'PAID'),
       (5, 230.00, 'PAID');

-- Order food items
INSERT INTO order_food_items (order_id, food_item_id, quantity, subtotal)
VALUES (1, 1, 1, 50.00),
       (2, 2, 2, 70.00),
       (2, 4, 1, 25.00),
       (3, 3, 1, 40.00),
       (4, 5, 2, 90.00);

-- Payment methods
INSERT INTO payment_method (method_name)
VALUES ('Картка'),
       ('Готівка'),
       ('Google Pay');

-- Payments
INSERT INTO payment (order_id, amount, method_id, status)
VALUES (1, 200.00, 1, 'COMPLETED'),
       (2, 185.00, 3, 'PENDING'),
       (3, 180.00, 2, 'FAILED'),
       (4, 160.00, 1, 'COMPLETED'),
       (5, 230.00, 4, 'COMPLETED');

-- Promotions
INSERT INTO promotion (name, discount_percent, start_date, end_date)
VALUES ('Весняна знижка', 10.00, '2025-05-01', '2025-05-31'),
       ('Вечірній сеанс', 15.00, '2025-05-01', '2025-05-15'),
       ('Акція вихідного дня', 20.00, '2025-05-10', '2025-05-12'),
       ('1+1 на попкорн', 100.00, '2025-05-03', '2025-05-05'),
       ('Кіно+сік за 60 грн', 25.00, '2025-05-01', '2025-05-07');