-- ENUM типи
CREATE TYPE payment_status_enum AS ENUM ('PENDING', 'COMPLETED', 'FAILED');
CREATE TYPE order_payment_status_enum AS ENUM ('PENDING', 'PAID', 'CANCELLED');

-- Таблиця movie
CREATE TABLE movie
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    duration         INT          NOT NULL,
    genre            VARCHAR(100),
    rating           VARCHAR(10),
    description      TEXT,
    poster_image_url TEXT
);
CREATE INDEX idx_movie_title ON movie (title);

-- Таблиця movie_translation
CREATE TABLE movie_translation
(
    id                     SERIAL PRIMARY KEY,
    movie_id               INT REFERENCES movie (id) ON DELETE CASCADE,
    language_code          VARCHAR(10) NOT NULL,
    translated_title       VARCHAR(255),
    translated_description TEXT,
    UNIQUE (movie_id, language_code)
);

-- Таблиця session
CREATE TABLE session
(
    id          SERIAL PRIMARY KEY,
    movie_id    INT REFERENCES movie (id) ON DELETE CASCADE,
    start_time  TIMESTAMP NOT NULL,
    hall_number INT       NOT NULL
);
CREATE INDEX idx_session_movie_id ON session (movie_id);
CREATE INDEX idx_session_start_time ON session (start_time);

-- Таблиця seat
CREATE TABLE seat
(
    id          SERIAL PRIMARY KEY,
    hall_number INT     NOT NULL,
    row         CHAR(1) NOT NULL,
    seat_number INT     NOT NULL,
    UNIQUE (hall_number, row, seat_number)
);

-- Таблиця ticket
CREATE TABLE ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT REFERENCES session (id) ON DELETE CASCADE,
    seat_id    INT REFERENCES seat (id),
    price      DECIMAL(10, 2) NOT NULL,
    printed    BOOLEAN DEFAULT FALSE
);
CREATE INDEX idx_ticket_session_id ON ticket (session_id);
CREATE INDEX idx_ticket_seat_id ON ticket (seat_id);


CREATE TABLE food_item
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    price     DECIMAL(10, 2) NOT NULL,
    available BOOLEAN DEFAULT TRUE
);

-- Таблиця food_item_translation
CREATE TABLE food_item_translation
(
    id              SERIAL PRIMARY KEY,
    food_item_id    INT REFERENCES food_item (id) ON DELETE CASCADE,
    language_code   VARCHAR(10)  NOT NULL,
    translated_name VARCHAR(255) NOT NULL,
    UNIQUE (food_item_id, language_code)
);

-- Таблиця orders
CREATE TABLE orders
(
    id             SERIAL PRIMARY KEY,
    ticket_id      INT REFERENCES ticket (id),
    total_price    DECIMAL(10, 2)            NOT NULL,
    payment_status order_payment_status_enum NOT NULL
);
ALTER TABLE orders
    ADD COLUMN food_item_ids INT[] NULL;

-- Таблиця order_food_items
CREATE TABLE order_food_items
(
    order_id     INT REFERENCES orders (id) ON DELETE CASCADE,
    food_item_id INT REFERENCES food_item (id) ON DELETE CASCADE,
    quantity     INT            NOT NULL,
    subtotal     DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (order_id, food_item_id)
);

-- Таблиця payment_method
CREATE TABLE payment_method
(
    id          SERIAL PRIMARY KEY,
    method_name VARCHAR(50) UNIQUE NOT NULL
);

-- Таблиця payment
CREATE TABLE payment
(
    id        SERIAL PRIMARY KEY,
    order_id  INT REFERENCES orders (id) ON DELETE CASCADE,
    amount    DECIMAL(10, 2)      NOT NULL,
    method_id INT REFERENCES payment_method (id),
    status    payment_status_enum NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS promotion;

CREATE TABLE promotion
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255)    NOT NULL,
    discount_percent DECIMAL(5, 2)   NOT NULL,
    start_date       DATE            NOT NULL,
    end_date         DATE            NOT NULL,
    description      TEXT,
    image_path       VARCHAR(255)
);
