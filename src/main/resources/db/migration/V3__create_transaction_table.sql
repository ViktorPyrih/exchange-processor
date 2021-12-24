CREATE TABLE "transaction" (
    id SERIAL PRIMARY KEY NOT NULL,
    created_at TIMESTAMP NOT NULL,
    price INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);
