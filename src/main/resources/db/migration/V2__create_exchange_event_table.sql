CREATE TABLE exchange_event (
    id SERIAL PRIMARY KEY NOT NULL,
    date_published TIMESTAMP NOT NULL,
    absolute_amount INTEGER NOT NULL
);
