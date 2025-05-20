CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(100),
    price NUMERIC,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);