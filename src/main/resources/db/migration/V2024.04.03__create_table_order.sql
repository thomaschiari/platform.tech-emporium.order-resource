CREATE TABLE orders (
    id_order character varying(256) NOT NULL,
    id_product INTEGER NOT NULL,
    quantity_order INTEGER NOT NULL,
    email_order character varying(256) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id_order)
);
