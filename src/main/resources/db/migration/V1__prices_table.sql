create table brands (
    id BIGINT PRIMARY KEY,
    name CHARACTER VARYING(50) NOT NULL
);

create table prices (
    id BIGINT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    priority BIGINT NOT NULL,
    price DOUBLE NOT NULL,
    currency CHARACTER VARYING(10) NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(id)
);