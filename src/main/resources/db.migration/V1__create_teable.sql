INSERT INTO users(FIRST_NAME, LAST_NAME, PHONE_NUMBER, ROLE, EMAIL, create_date, ADDRESS)
VALUES ('Bakyt', 'Latibov', '*79998885522', 'ADMIN', 'bakyt@gmail.com', '2023.06.5', 'moscow'),
       ('Asan', 'Tairov', '*79854455566', 'USER', 'asan@gmail.com', '2023.06.15', 'moscow');

INSERT INTO brands(brand_name)
VALUES ('APPLE'),
       ('SAMSUNG'),
       ('HONOR'),
       ('HUAWEI'),
       ('XIAOMI');

INSERT INTO products(article, body_shape,sub_category, color, discount, file, gender,in_stock, memory, name, operating_system, price,
                     processor, rating, release_date, screen, sim_card, warranty, water_resistance, weight,
                     wireless_interface, brand_id)
VALUES ('0214', 'OVAL','WATCHBANDS', 'black', '15', null, 'FEMALE','10', '125Gb', 'iphone 12', 'IOS', '95000', 'A15 Bionic', '5',
        '2022.12.26', '53 (2340*1080) IPS', '1', '12', null, '173', null, 1);

-- INSERT INTO brands_products(brand_id, products_id)
-- VALUES (1, 1);

INSERT INTO baskets(AMOUNT, DISCOUNT, GRAND_TOTAL, TOTAL, USER_ID)
VALUES ('5', '15', '25103', '5', '2');

INSERT INTO categories(title)
VALUES ('Смартфоны'),
       ('Ноутбуки и планшеты'),
       ('Смарт-часы и браслеты'),
       ('Аксессуары');

-- INSERT INTO sub_categories(title)
-- VALUES ('Сотовые / мобильные телефоны, смартфоны');

-- INSERT INTO categories_sub_categories(category_id, sub_category_id)
-- VALUES (1, 1);

INSERT INTO orders(address, created, payment, photo, status, sum, update, basket_id, user_id)
VALUES ('bishkek', '2023.06.22', 'CASH', null, 'DELIVERED', '6584752', null, 1, 2);

INSERT INTO order_details(amount, delivery, price, order_id, product_id, order_details_id)
VALUES ('1', 'DELIVERY', '29501', 1, 1, 1);

INSERT INTO products_categories(product_id, category_id)
VALUES (1, 1);

INSERT INTO reviews(commentary, photo, rade)
VALUES (null, null, '2');

INSERT INTO products_reviews(product_id, review_id)
VALUES (1, 2);

INSERT INTO responses_to_reviews (massage, user_id)
VALUES ('comment', 2);

-- INSERT INTO reviews_products (review_id, products_id)
-- VALUES (2, 1);

INSERT INTO reviews_to_responses_reviews(reviews_id, response_reviews_id)
VALUES (2, 1);

INSERT INTO users_reviews(user_id, review_id)
VALUES (2, 1);
