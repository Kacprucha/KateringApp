-- Tworzenie tabeli 'catering_firm_data' z ręcznie przypisywanym ID
CREATE TABLE IF NOT EXISTS catering_firm_data (
                                                  catering_firm_id UUID PRIMARY KEY,  -- Ręczne przypisywanie ID
                                                  name VARCHAR(255) NOT NULL,
    info TEXT,
    logo BYTEA
    );

-- Tworzenie tabeli 'ingredient'
CREATE TABLE IF NOT EXISTS ingredient (
                                          ingredient_id BIGINT PRIMARY KEY,  -- Ręczne przypisywanie ID
                                          name VARCHAR(255) NOT NULL
    );

-- Tworzenie tabeli 'allergen'
CREATE TABLE IF NOT EXISTS allergen (
                                        allergen_id BIGINT PRIMARY KEY,  -- Ręczne przypisywanie ID
                                        name VARCHAR(255) NOT NULL
    );

-- Tworzenie tabeli 'ingredient_allergen' (relacja wiele do wielu)
CREATE TABLE IF NOT EXISTS ingredient_allergen (
                                                   ingredient_id BIGINT,
                                                   allergen_id BIGINT,
                                                   PRIMARY KEY (ingredient_id, allergen_id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(ingredient_id),
    FOREIGN KEY (allergen_id) REFERENCES allergen(allergen_id)
    );

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY,
    order_status VARCHAR(50) NOT NULL,
    client_id UUID,
    opinion TEXT,
    rate INT NOT NULL,
    starting_address VARCHAR(255) NOT NULL,
    destination_address VARCHAR(255) NOT NULL
);

-- Tworzenie tabeli 'meal'
CREATE TABLE IF NOT EXISTS meal (
                                    meal_id BIGINT PRIMARY KEY,  -- Ręczne przypisywanie ID
                                    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    description TEXT NOT NULL,
    photo BYTEA,
    catering_firm_id UUID,
    FOREIGN KEY (catering_firm_id) REFERENCES catering_firm_data(catering_firm_id)
    );

CREATE TABLE IF NOT EXISTS order_meals (
    order_id BIGINT NOT NULL,
    meal_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, meal_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (meal_id) REFERENCES meal(meal_id) ON DELETE CASCADE
);

-- Tworzenie tabeli 'delivery_type'
CREATE TABLE IF NOT EXISTS delivery_type (
                                             delivery_type_id BIGINT PRIMARY KEY,  -- Ręczne przypisywanie ID
                                             delivery_type VARCHAR(255) NOT NULL
    );

-- Tworzenie tabeli 'catering_firm_data_delivery_type' (relacja wiele do wielu)
CREATE TABLE IF NOT EXISTS catering_firm_data_delivery_type (
                                                                catering_firm_id UUID,
                                                                delivery_type_id BIGINT,
                                                                PRIMARY KEY (catering_firm_id, delivery_type_id),
    FOREIGN KEY (catering_firm_id) REFERENCES catering_firm_data(catering_firm_id),
    FOREIGN KEY (delivery_type_id) REFERENCES delivery_type(delivery_type_id)
    );

CREATE TABLE IF NOT EXISTS meal_ingredients (
                                                meal_id BIGINT,
                                                ingredient_id BIGINT,
                                                PRIMARY KEY (meal_id, ingredient_id),
    FOREIGN KEY (meal_id) REFERENCES meal(meal_id) ON DELETE CASCADE,  -- Powiązanie z tabelą meal
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(ingredient_id) ON DELETE CASCADE  -- Powiązanie z tabelą ingredient
    );

-- Wstawianie danych do tabeli 'catering_firm_data'
INSERT INTO catering_firm_data (catering_firm_id, name, info, logo)
VALUES
    ('6c84fb95-12c4-11ec-82a8-0242ac130005', 'Italian Delights Catering', 'Italian cuisine with a variety of pasta and pizza options', null),
    ('6c84fb95-12c4-11ec-82a8-0242ac130006', 'Healthy Bites', 'Fresh, organic, and healthy meals', null),
    ('919b0f69-766e-4f51-a36c-5e9e643385cd', 'Smoothie Masters', 'Delicious smoothies and acai bowls', null);

-- Wstawianie danych do tabeli 'ingredient'
INSERT INTO ingredient (ingredient_id, name)
VALUES
    (1, 'Milk'),
    (2, 'Peanut Butter'),
    (3, 'Wheat Flour'),
    (4, 'Soy Sauce'),
    (5, 'Egg Whites');

-- Wstawianie danych do tabeli 'allergen'
INSERT INTO allergen (allergen_id, name)
VALUES
    (1, 'Gluten'),
    (2, 'Peanuts'),
    (3, 'Dairy'),
    (4, 'Soy'),
    (5, 'Eggs'),
    (6, 'Fish'),
    (7, 'Shellfish'),
    (8, 'Tree nuts'),
    (9, 'Wheat'),
    (10, 'Sesame');

-- Powiązania dla 'Milk' (alergeny: Dairy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES
    (1, 3);

-- Powiązania dla 'Peanut Butter' (alergeny: Peanuts, Tree nuts)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES
    (2, 2),
    (2, 8);

-- Powiązania dla 'Wheat Flour' (alergeny: Gluten, Wheat)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES
    (3, 1),
    (3, 9);

-- Powiązania dla 'Soy Sauce' (alergen: Soy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES
    (4, 4);

-- Powiązania dla 'Egg Whites' (alergen: Eggs)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES
    (5, 5);

-- Wstawianie danych do tabeli 'meal'
INSERT INTO meal (meal_id, name, price, description, photo, catering_firm_id)
VALUES
    (101, 'Pasta Carbonara', 12, 'A classic Italian pasta dish with creamy sauce and pancetta.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (102, 'Margherita Pizza', 10, 'A simple pizza with tomato, mozzarella, and basil.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (103, 'Grilled Chicken Salad', 8, 'A healthy salad with grilled chicken, fresh greens, and vinaigrette.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (104, 'Vegan Burger', 7, 'A plant-based burger with a side of fries.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (105, 'Tropical Smoothie', 5, 'A refreshing smoothie made with pineapple, mango, and coconut milk.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (106, 'Acai Bowl', 6, 'A smoothie bowl topped with granola, fruits, and honey.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006');

-- Wstawianie danych do tabeli 'delivery_type'
INSERT INTO delivery_type (delivery_type_id, delivery_type)
VALUES
    (1, 'DELIVERY_MAN'),
    (2, 'PERSONAL_COLLECTION');

-- Powiązania dla 'catering_firm_data_delivery_type'
INSERT INTO catering_firm_data_delivery_type (catering_firm_id, delivery_type_id)
VALUES
    ('919b0f69-766e-4f51-a36c-5e9e643385cd', 1),  -- Italian Delights Catering: Home Delivery
    ('919b0f69-766e-4f51-a36c-5e9e643385cd', 2),  -- Italian Delights Catering: Pick-Up
    ('919b0f69-766e-4f51-a36c-5e9e643385cd', 1),  -- Healthy Bites: Home Delivery
    ('919b0f69-766e-4f51-a36c-5e9e643385cd', 1);  -- Smoothie Masters: Home Delivery

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address)
VALUES
    (101, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good service', 5, '123 Starting St', '789 Destination Ave'),
    (102, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Quick delivery', 4, '456 Another St', '101 Another Ave'),
    (103, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130003', 'Not delivered on time', 2, '789 Late St', '111 Final Ave');

-- Pasta Carbonara (Meal ID: 1) - składniki: Wheat Flour (ID: 3), Milk (ID: 1), Egg Whites (ID: 5)
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (101, 3);
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (101, 1);
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (101, 5);

-- Margherita Pizza (Meal ID: 2) - składniki: Wheat Flour (ID: 3), Dairy (ID: 1)
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (102, 3);
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (102, 1);

-- Grilled Chicken Salad (Meal ID: 3) - składniki: Soy Sauce (ID: 4)
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (103, 4);

-- Vegan Burger (Meal ID: 4) - składniki: Wheat Flour (ID: 3), Soy Sauce (ID: 4)
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (104, 3);
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (104, 4);

-- Tropical Smoothie (Meal ID: 5) - składniki: Milk (ID: 1)
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (105, 1);

-- Acai Bowl (Meal ID: 6) - składniki: Dairy (ID: 1)
INSERT INTO meal_ingredients (meal_id, ingredient_id) VALUES (106, 1);

INSERT INTO order_meals (order_id, meal_id) VALUES (101, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (101, 102);

INSERT INTO order_meals (order_id, meal_id) VALUES (102, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (102, 104);

INSERT INTO order_meals (order_id, meal_id) VALUES (103, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (103, 106);