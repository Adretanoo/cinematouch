-- Вставка фільмів
INSERT INTO movie (id, title, duration, genre, rating, description, poster_image_url)
VALUES (1, 'The Matrix', 136, 'Sci-Fi', 'R', 'A hacker discovers the true nature of reality.',
        'matrix.jpg'),
       (2, 'Inception', 148, 'Sci-Fi', 'PG-13',
        'A thief steals corporate secrets through shared dreaming.', 'inception.jpg'),
       (3, 'Parasite', 132, 'Thriller', 'R', 'Class conflict erupts in a wealthy family’s home.',
        'parasite.jpg'),
       (4, 'Interstellar', 169, 'Sci-Fi', 'PG-13',
        'A team travels through a wormhole searching for a new home for humanity.',
        'interstellar.jpg');

-- Вставка перекладів фільмів
INSERT INTO movie_translation (id, movie_id, language_code, translated_title,
                               translated_description)
VALUES (1, 1, 'uk', 'Матриця', 'Хакер дізнається про справжню природу реальності.'),
       (2, 1, 'en', 'The Matrix', 'A hacker discovers the true nature of reality.'),
       (3, 2, 'uk', 'Початок',
        'Злодій викрадає корпоративні секрети через технологію спільних снів.'),
       (4, 2, 'en', 'Inception', 'A thief steals corporate secrets through shared dreaming.');

-- Вставка сеансів
INSERT INTO session (id, movie_id, start_time, hall_number)
VALUES (1, 1, '2025-05-10 18:30:00', 1),
       (2, 2, '2025-05-10 20:00:00', 1),
       (3, 3, '2025-05-11 19:00:00', 2),
       (4, 4, '2025-05-11 21:00:00', 2);

-- Вставка місць
INSERT INTO seat (id, hall_number, row, seat_number)
VALUES (1, 1, 'A', 1),
       (2, 1, 'A', 2),
       (3, 2, 'B', 1),
       (4, 2, 'B', 2);

-- Вставка квитків
INSERT INTO ticket (id, session_id, seat_id, price, printed)
VALUES (1, 1, 1, 8.50, FALSE),
       (2, 1, 2, 8.50, TRUE),
       (3, 2, 3, 9.00, FALSE),
       (4, 3, 4, 9.50, TRUE);

-- Вставка позицій меню
INSERT INTO food_item (id, name, price, available)
VALUES (1, 'Popcorn', 5.00, TRUE),
       (2, 'Soda', 3.00, TRUE),
       (3, 'Nachos', 4.50, FALSE),
       (4, 'Candy', 2.50, TRUE);

-- Вставка перекладів позицій меню
INSERT INTO food_item_translation (id, food_item_id, language_code, translated_name)
VALUES (1, 1, 'uk', 'Попкорн'),
       (2, 2, 'uk', 'Газований напій'),
       (3, 3, 'uk', 'Начос'),
       (4, 4, 'uk', 'Цукерки');

-- Вставка методів оплати
INSERT INTO payment_method (id, method_name)
VALUES (1, 'Credit Card'),
       (2, 'Cash'),
       (3, 'PayPal'),
       (4, 'Mobile Pay');

-- Вставка замовлень
INSERT INTO orders (id, ticket_id, total_price, payment_status, food_item_ids)
VALUES (1, 1, 13.50, 'PENDING', '{1,2}'),
       (2, 2, 5.00, 'PAID', '{1}'),
       (3, 3, 4.50, 'CANCELLED', '{3}'),
       (4, 4, 2.50, 'PAID', NULL);

-- Вставка рядків order_food_items
INSERT INTO order_food_items (order_id, food_item_id, quantity, subtotal)
VALUES (1, 1, 1, 5.00),
       (1, 2, 1, 3.00),
       (2, 1, 1, 5.00),
       (3, 3, 1, 4.50);

-- Вставка платежів
INSERT INTO payment (id, order_id, amount, method_id, status, timestamp)
VALUES (1, 1, 13.50, 1, 'PENDING', '2025-05-10 19:00:00'),
       (2, 2, 5.00, 2, 'COMPLETED', '2025-05-10 20:15:00'),
       (3, 3, 4.50, 3, 'FAILED', '2025-05-11 19:30:00'),
       (4, 4, 2.50, 4, 'COMPLETED', '2025-05-11 21:30:00');

-- Вставка промоцій
INSERT INTO promotion (id, name, discount_percent, start_date, end_date, description, image_path)
VALUES (1, 'Summer Sale', 10.00, '2025-06-01', '2025-06-30', '10% off on all tickets',
        'summer.jpg'),
       (2, 'Student Discount', 15.00, '2025-05-15', '2025-05-31', '15% for students with ID',
        'student.jpg'),
       (3, 'Evening Special', 20.00, '2025-05-01', '2025-05-14', '20% on shows after 8 PM',
        'evening.jpg'),
       (4, 'Holiday Promo', 25.00, '2025-12-20', '2025-12-31', '25% during year-end holidays',
        'holiday.jpg');
