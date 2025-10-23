CREATE TABLE products (
    product_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE,
    online_status VARCHAR(20),
    long_description TEXT,
    short_description VARCHAR(500)
);

CREATE TABLE categories (
    category_id BIGINT NOT NULL PRIMARY KEY,
    category_name VARCHAR(255),
    parent_category_id BIGINT NULL
);

CREATE TABLE product_category_map (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id)
);

CREATE TABLE processed_data_set (
    file_name VARCHAR(255) NOT NULL PRIMARY KEY
);