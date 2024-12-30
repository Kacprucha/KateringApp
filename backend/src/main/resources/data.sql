-- Tworzenie tabeli 'catering_firm_data' z ręcznie przypisywanym ID
CREATE TABLE IF NOT EXISTS catering_firm_data (
                                                  catering_firm_id UUID PRIMARY KEY,  -- Ręczne przypisywanie ID
                                                  name VARCHAR(255) NOT NULL,
    info TEXT,
    logo BYTEA
    );

-- Tworzenie tabeli 'client'
CREATE TABLE IF NOT EXISTS client (
                                        client_id UUID PRIMARY KEY,  -- Ręczne przypisywanie ID
                                        first_name VARCHAR(255),
                                        last_name VARCHAR(255),
                                        email VARCHAR(255),
                                        phone_number VARCHAR(255),
                                        address VARCHAR(255)

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
    destination_address VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    order_date_time TIMESTAMP,
    due_date_time TIMESTAMP,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(11) NOT NULL,
    total_price NUMERIC(10, 2),
    completed_at TIMESTAMP
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

-- Wstawianie danych do tabeli 'client'
INSERT INTO client (client_id, first_name, last_name, email, phone_number, address)
VALUES
    ('6c84fb95-12c4-11ec-82a8-0242ac130003', 'John', 'Doe', 'johndoe@gmail.com', '+48 341 231 412', 'ul. Klonowa 15/3, Warszawa'),
    ('6c84fb95-12c4-11ec-82a8-0242ac130004', 'Jane', 'Doe', 'janedoe@gmail.com', '+48 412 341 412', 'ul. Jasna 7A, 02-456 Kraków');

-- Wstawianie danych do tabeli 'ingredient'
INSERT INTO ingredient (ingredient_id, name)
VALUES
    (1, 'Milk'),
    (2, 'Peanut Butter'),
    (3, 'Wheat Flour'),
    (4, 'Soy Sauce'),
    (5, 'Egg Whites'),
    (6, 'Butter'),
    (7, 'Sugar'),
    (8, 'Salt'),
    (9, 'Black Pepper'),
    (10, 'Olive Oil'),
    (11, 'Garlic'),
    (12, 'Onion'),
    (13, 'Tomato'),
    (14, 'Carrot'),
    (15, 'Potato'),
    (16, 'Chicken Breast'),
    (17, 'Beef Mince'),
    (18, 'Pork Chops'),
    (19, 'Fish Fillet'),
    (20, 'Shrimp'),
    (21, 'Cheddar Cheese'),
    (22, 'Mozzarella Cheese'),
    (23, 'Basil'),
    (24, 'Parsley'),
    (25, 'Cumin'),
    (26, 'Cinnamon'),
    (27, 'Vanilla Extract'),
    (28, 'Honey'),
    (29, 'Yogurt'),
    (30, 'Lettuce');

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

-- Fish Fillet (Fish)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES (19, 6);

-- Shrimp (Shellfish)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES (20, 7);

-- Cheddar Cheese (Dairy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES (21, 3);

-- Mozzarella Cheese (Dairy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES (22, 3);

-- Yogurt (Dairy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id)
VALUES (29, 3);

-- Wstawianie danych do tabeli 'meal'
INSERT INTO meal (meal_id, name, price, description, photo, catering_firm_id)
VALUES
    (101, 'Pasta Carbonara', 12, 'A classic Italian pasta dish with creamy sauce and pancetta.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (102, 'Margherita Pizza', 10, 'A simple pizza with tomato, mozzarella, and basil.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (103, 'Grilled Chicken Salad', 8, 'A healthy salad with grilled chicken, fresh greens, and vinaigrette.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (104, 'Vegan Burger', 7, 'A plant-based burger with a side of fries.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (105, 'Tropical Smoothie', 5, 'A refreshing smoothie made with pineapple, mango, and coconut milk.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (106, 'Acai Bowl', 6, 'A smoothie bowl topped with granola, fruits, and honey.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (107, 'Spaghetti Bolognese', 13, 'Pasta with a rich meat sauce made from beef and tomatoes.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (108, 'Pepperoni Pizza', 11, 'Pizza topped with spicy pepperoni and melted cheese.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (109, 'Caesar Salad', 9, 'Classic Caesar salad with romaine, croutons, and creamy dressing.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (110, 'Tofu Stir Fry', 8, 'A stir fry made with tofu, mixed vegetables, and soy sauce.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (111, 'Mango Smoothie', 5, 'A sweet smoothie made with fresh mango and yogurt.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (112, 'Falafel Wrap', 7, 'A wrap filled with crispy falafel, lettuce, and tahini sauce.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (113, 'Grilled Veggie Sandwich', 6, 'A sandwich with grilled vegetables, hummus, and fresh herbs.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (114, 'Chicken Tikka Masala', 14, 'Chicken in a creamy and spicy tomato-based curry.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (115, 'Mushroom Risotto', 12, 'Creamy risotto with sautéed mushrooms and parmesan cheese.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (116, 'Lentil Soup', 7, 'A hearty soup made with lentils, vegetables, and spices.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (117, 'Shakshuka', 9, 'Eggs poached in a spicy tomato and pepper sauce.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (118, 'Cheeseburger', 10, 'A classic beef burger with cheese, lettuce, and tomato.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (119, 'Pineapple Fried Rice', 11, 'Fried rice with pineapple, peas, and a touch of curry.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (120, 'Baked Ziti', 13, 'Ziti pasta baked with marinara sauce and mozzarella cheese.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (121, 'Veggie Burger', 8, 'A plant-based patty served with lettuce, tomato, and a side of fries.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (122, 'Avocado Toast', 6, 'Toast topped with smashed avocado, chili flakes, and a poached egg.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (123, 'Beef Tacos', 9, 'Soft tortillas filled with seasoned beef, cheese, and salsa.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (124, 'Grilled Salmon', 15, 'Fresh salmon grilled and served with a side of vegetables.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (125, 'Fruit Salad', 5, 'A mix of fresh seasonal fruits served with a honey dressing.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (126, 'Chicken Quesadilla', 10, 'Grilled tortilla filled with chicken, cheese, and guacamole.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (127, 'Spinach and Feta Wrap', 8, 'Whole wheat wrap with spinach, feta cheese, and hummus.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (128, 'Vegetable Samosas', 7, 'Crispy pastry filled with spiced vegetables and served with chutney.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (129, 'Steak and Fries', 16, 'Grilled steak served with crispy fries and a side salad.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (130, 'Eggplant Parmesan', 12, 'Breaded eggplant layered with marinara sauce and cheese, baked to perfection.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (131, 'Fried Chicken', 12, 'Crispy fried chicken served with mashed potatoes and gravy.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (132, 'Greek Salad', 8, 'Salad with cucumbers, olives, feta cheese, and olive oil dressing.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (133, 'Vegetable Stir Fry', 9, 'Mixed vegetables stir fried with soy sauce and served with rice.', null, '6c84fb95-12c4-11ec-82a8-0242ac130006'),
    (134, 'Chicken Fajitas', 13, 'Grilled chicken with bell peppers, onions, and tortillas for wrapping.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005'),
    (135, 'Peking Duck', 18, 'A traditional Chinese dish made with crispy duck, pancakes, and hoisin sauce.', null, '6c84fb95-12c4-11ec-82a8-0242ac130005');

-- Wstawianie danych do tabeli 'delivery_type'
INSERT INTO delivery_type (delivery_type_id, delivery_type)
VALUES
    (1, 'DELIVERY_MAN'),
    (2, 'PERSONAL_COLLECTION');

-- Powiązania dla 'catering_firm_data_delivery_type'
INSERT INTO catering_firm_data_delivery_type (catering_firm_id, delivery_type_id)
VALUES
    ('6c84fb95-12c4-11ec-82a8-0242ac130005', 1),  -- Italian Delights Catering: Home Delivery
    ('6c84fb95-12c4-11ec-82a8-0242ac130005', 2),  -- Italian Delights Catering: Pick-Up
    ('6c84fb95-12c4-11ec-82a8-0242ac130006', 1),  -- Healthy Bites: Home Delivery
    ('6c84fb95-12c4-11ec-82a8-0242ac130006', 2);  -- Smoothie Masters: Home Delivery

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (101, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good service', 5, '123 Starting St', '789 Destination Ave', 22, null, 'John', 'Doe', '2024-12-01 10:00:00', '2024-12-15 18:00:00', 'john.doe@example.com', '123456789'),
    (102, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Quick delivery', 4, '456 Another St', '101 Another Ave', 15, '2024-03-28 00:00:00'::timestamp, 'Jane', 'Smith', '2024-11-20 09:30:00', '2024-12-01 14:00:00', 'jane.smith@example.com', '987654321'),
    (103, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130003', 'Not delivered on time', 2, '789 Late St', '111 Final Ave', 11, null, 'Alice', 'Johnson', '2024-11-15 08:45:00', '2024-11-30 16:00:00', 'alice.johnson@example.com', '654321987'),
    (104, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Delicious meals!', 5, '22 Elm St', '88 Pine St', 18, '2024-03-15 12:45:00'::timestamp, 'Michael', 'Brown', '2024-02-28 10:00:00', '2024-03-15 12:45:00', 'michael.brown@example.com', '111222333'),
    (105, 'COMPLETED', '919b0f69-766e-4f51-a36c-5e9e643385cd', 'Loved the smoothies!', 4, '300 Oak St', '700 Maple St', 10, '2024-03-20 15:30:00'::timestamp, 'Emily', 'Davis', '2024-03-05 08:45:00', '2024-03-20 15:30:00', 'emily.davis@example.com', '444555666'),
    (106, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130005', 'Waiting for delivery...', 3, '55 Birch St', '66 Willow St', 12, NULL, 'James', 'Wilson', '2024-03-10 09:30:00', '2024-03-25 14:00:00', 'james.wilson@example.com', '777888999'),
    (107, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130003', 'Fast service!', 5, '99 Cedar St', '101 Oak St', 25, '2024-03-21 18:00:00'::timestamp, 'Olivia', 'Moore', '2024-03-08 10:00:00', '2024-03-21 18:00:00', 'olivia.moore@example.com', '112233445'),
    (108, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130002', 'Had to cancel', 1, '200 Ash St', '300 Spruce St', 15, NULL, 'Liam', 'Taylor', '2024-03-01 10:00:00', '2024-03-15 14:00:00', 'liam.taylor@example.com', '556677889'),
    (109, 'COMPLETED', '919b0f69-766e-4f51-a36c-5e9e643385cd', 'Very fresh ingredients', 4, '45 Palm St', '50 Mango St', 14, '2024-03-25 11:20:00'::timestamp, 'Sophia', 'Anderson', '2024-03-10 08:45:00', '2024-03-25 11:20:00', 'sophia.anderson@example.com', '667788990'),
    (110, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130006', 'Looking forward to it', 4, '77 Cypress St', '99 Mahogany St', 17, NULL, 'Charlotte', 'Thomas', '2024-03-05 08:45:00', '2024-03-25 18:00:00', 'charlotte.thomas@example.com', '778899001'),
    (111, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130003', 'Satisfying meal', 5, '33 Cherry St', '44 Chestnut St', 20, '2024-04-01 19:45:00'::timestamp, 'Henry', 'Martin', '2024-03-10 08:45:00', '2024-04-01 19:45:00', 'henry.martin@example.com', '889900112'),
    (112, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130005', 'Late order', 2, '11 Pear St', '22 Peach St', 13, NULL, 'Grace', 'White', '2024-03-10 08:45:00', '2024-03-25 18:00:00', 'grace.white@example.com', '990011223'),
    (113, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Great taste', 5, '19 Apricot St', '31 Grape St', 16, '2024-04-05 20:10:00'::timestamp, 'Isabella', 'Harris', '2024-03-10 08:45:00', '2024-04-05 20:10:00', 'isabella.harris@example.com', '334455667'),
    (114, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130005', 'Best pizza!', 5, '21 Lime St', '42 Fig St', 22, '2024-04-10 12:00:00'::timestamp, 'Elijah', 'Clark', '2024-03-10 08:45:00', '2024-04-10 12:00:00', 'elijah.clark@example.com', '445566778'),
    (115, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good service', 5, '123 Starting St', '789 Destination Ave', 22, null, 'John', 'Doe', '2024-12-01 10:00:00', '2024-12-15 18:00:00', 'john.doe@example.com', '123456789'),
    (116, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Quick delivery', 4, '456 Another St', '101 Another Ave', 15, '2024-03-28 00:00:00'::timestamp, 'Jane', 'Smith', '2024-11-20 09:30:00', '2024-12-01 14:00:00', 'jane.smith@example.com', '987654321'),
    (117, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130003', 'Not delivered on time', 2, '789 Late St', '111 Final Ave', 11, null, 'Alice', 'Johnson', '2024-11-15 08:45:00', '2024-11-30 16:00:00', 'alice.johnson@example.com', '654321987'),
    (118, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Delicious meals!', 5, '22 Elm St', '88 Pine St', 18, '2024-03-15 12:45:00'::timestamp, 'Michael', 'Brown', '2024-02-28 10:00:00', '2024-03-15 12:45:00', 'michael.brown@example.com', '111222333'),
    (119, 'COMPLETED', '919b0f69-766e-4f51-a36c-5e9e643385cd', 'Loved the smoothies!', 4, '300 Oak St', '700 Maple St', 10, '2024-03-20 15:30:00'::timestamp, 'Emily', 'Davis', '2024-03-05 08:45:00', '2024-03-20 15:30:00', 'emily.davis@example.com', '444555666'),
    (120, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130005', 'Waiting for delivery...', 3, '55 Birch St', '66 Willow St', 12, NULL, 'James', 'Wilson', '2024-03-10 09:30:00', '2024-03-25 14:00:00', 'james.wilson@example.com', '777888999'),
    (121, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130003', 'Satisfying meal', 5, '33 Cherry St', '44 Chestnut St', 20, '2024-04-01 19:45:00'::timestamp, 'Henry', 'Martin', '2024-03-10 08:45:00', '2024-04-01 19:45:00', 'henry.martin@example.com', '889900112');

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

-- Zamówienie 104
INSERT INTO order_meals (order_id, meal_id) VALUES (104, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (104, 102);

-- Zamówienie 105
INSERT INTO order_meals (order_id, meal_id) VALUES (105, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (105, 104);

-- Zamówienie 106
INSERT INTO order_meals (order_id, meal_id) VALUES (106, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (106, 106);

-- Zamówienie 107
INSERT INTO order_meals (order_id, meal_id) VALUES (107, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (107, 102);

-- Zamówienie 108
INSERT INTO order_meals (order_id, meal_id) VALUES (108, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (108, 104);

-- Zamówienie 109
INSERT INTO order_meals (order_id, meal_id) VALUES (109, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (109, 106);

-- Zamówienie 110
INSERT INTO order_meals (order_id, meal_id) VALUES (110, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (110, 102);

-- Zamówienie 111
INSERT INTO order_meals (order_id, meal_id) VALUES (111, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (111, 104);

-- Zamówienie 112
INSERT INTO order_meals (order_id, meal_id) VALUES (112, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (112, 106);

-- Zamówienie 113
INSERT INTO order_meals (order_id, meal_id) VALUES (113, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (113, 102);

-- Zamówienie 114
INSERT INTO order_meals (order_id, meal_id) VALUES (114, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (114, 104);

-- Zamówienie 115
INSERT INTO order_meals (order_id, meal_id) VALUES (115, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (115, 106);

-- Zamówienie 116
INSERT INTO order_meals (order_id, meal_id) VALUES (116, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (116, 102);

-- Zamówienie 117
INSERT INTO order_meals (order_id, meal_id) VALUES (117, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (117, 104);

-- Zamówienie 118
INSERT INTO order_meals (order_id, meal_id) VALUES (118, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (118, 106);

-- Zamówienie 119
INSERT INTO order_meals (order_id, meal_id) VALUES (119, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (119, 102);

-- Zamówienie 120
INSERT INTO order_meals (order_id, meal_id) VALUES (120, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (120, 104);

-- Zamówienie 121
INSERT INTO order_meals (order_id, meal_id) VALUES (121, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (121, 104);

-- Dodatkowe seedowanie zamówień

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (122, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good servicee', 5, '123 Starting St', '789 Destination Ave', 22, null, 'John', 'Doe', '2024-12-01 10:00:00', '2024-12-15 18:00:00', 'john.doe@example.com', '123456789');


INSERT INTO order_meals (order_id, meal_id) VALUES (122, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (122, 102);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (123, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Great taste', 4, '456 Starting St', '987 Destination Ave', 25, null, 'Alice', 'Smith', '2024-12-02 14:00:00', '2024-12-18 16:00:00', 'alice.smith@example.com', '234567890');

INSERT INTO order_meals (order_id, meal_id) VALUES (123, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (123, 104);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (124, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Delicious food!', 5, '789 Starting St', '654 Destination Ave', 28, '2024-12-03 12:30:00', 'Bob', 'Johnson', '2024-12-03 11:00:00', '2024-12-17 15:00:00', 'bob.johnson@example.com', '345678901');

INSERT INTO order_meals (order_id, meal_id) VALUES (124, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (124, 106);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (125, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good value', 4, '321 Starting St', '321 Destination Ave', 20, null, 'Claire', 'Taylor', '2024-12-04 09:00:00', '2024-12-20 17:00:00', 'claire.taylor@example.com', '456789012');

INSERT INTO order_meals (order_id, meal_id) VALUES (125, 107);
INSERT INTO order_meals (order_id, meal_id) VALUES (125, 108);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (126, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Very tasty!', 4, '111 Starting St', '999 Destination Ave', 18, null, 'Derek', 'White', '2024-12-05 15:00:00', '2024-12-19 14:00:00', 'derek.white@example.com', '567890123');

INSERT INTO order_meals (order_id, meal_id) VALUES (126, 109);
INSERT INTO order_meals (order_id, meal_id) VALUES (126, 110);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (127, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Satisfying meal', 4, '456 Starting St', '876 Destination Ave', 24, '2024-12-06 13:00:00', 'Eva', 'Wilson', '2024-12-06 10:00:00', '2024-12-21 13:00:00', 'eva.wilson@example.com', '678901234');

INSERT INTO order_meals (order_id, meal_id) VALUES (127, 111);
INSERT INTO order_meals (order_id, meal_id) VALUES (127, 112);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (128, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Could be better', 3, '222 Starting St', '555 Destination Ave', 26, null, 'Frank', 'Miller', '2024-12-07 16:00:00', '2024-12-22 16:30:00', 'frank.miller@example.com', '789012345');

INSERT INTO order_meals (order_id, meal_id) VALUES (128, 113);
INSERT INTO order_meals (order_id, meal_id) VALUES (128, 114);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (129, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Highly recommend!', 5, '555 Starting St', '111 Destination Ave', 27, null, 'Grace', 'Brown', '2024-12-08 12:00:00', '2024-12-23 17:00:00', 'grace.brown@example.com', '890123456');

INSERT INTO order_meals (order_id, meal_id) VALUES (129, 115);
INSERT INTO order_meals (order_id, meal_id) VALUES (129, 116);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (130, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Will order again', 5, '333 Starting St', '444 Destination Ave', 30, '2024-12-09 11:00:00', 'Hannah', 'Jones', '2024-12-09 09:00:00', '2024-12-24 14:00:00', 'hannah.jones@example.com', '901234567');

INSERT INTO order_meals (order_id, meal_id) VALUES (130, 117);
INSERT INTO order_meals (order_id, meal_id) VALUES (130, 118);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (131, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Average', 3, '777 Starting St', '444 Destination Ave', 23, null, 'Ian', 'Davis', '2024-12-10 17:00:00', '2024-12-25 18:30:00', 'ian.davis@example.com', '012345678');

INSERT INTO order_meals (order_id, meal_id) VALUES (131, 119);
INSERT INTO order_meals (order_id, meal_id) VALUES (131, 120);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (132, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Food was cold', 2, '222 Starting St', '333 Destination Ave', 19, null, 'Liam', 'Taylor', '2024-12-11 08:00:00', '2024-12-26 10:00:00', 'liam.taylor@example.com', '112233445');

INSERT INTO order_meals (order_id, meal_id) VALUES (132, 121);
INSERT INTO order_meals (order_id, meal_id) VALUES (132, 122);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (133, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Not worth the price', 1, '111 Starting St', '444 Destination Ave', 32, null, 'Mason', 'Williams', '2024-12-12 09:30:00', '2024-12-27 12:30:00', 'mason.williams@example.com', '223344556');

INSERT INTO order_meals (order_id, meal_id) VALUES (133, 123);
INSERT INTO order_meals (order_id, meal_id) VALUES (133, 124);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (134, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Very bad experience', 1, '222 Starting St', '555 Destination Ave', 22, null, 'Ethan', 'Martinez', '2024-12-13 10:00:00', '2024-12-28 14:00:00', 'ethan.martinez@example.com', '334455667');

INSERT INTO order_meals (order_id, meal_id) VALUES (134, 125);
INSERT INTO order_meals (order_id, meal_id) VALUES (134, 126);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (135, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'The taste was awful', 2, '333 Starting St', '666 Destination Ave', 23, null, 'Oliver', 'Davis', '2024-12-14 11:00:00', '2024-12-29 13:30:00', 'oliver.davis@example.com', '445566778');

INSERT INTO order_meals (order_id, meal_id) VALUES (135, 127);
INSERT INTO order_meals (order_id, meal_id) VALUES (135, 128);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (136, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'I did not like it', 3, '444 Starting St', '777 Destination Ave', 24, null, 'Sophia', 'Lopez', '2024-12-15 12:00:00', '2024-12-30 14:00:00', 'sophia.lopez@example.com', '556677889');

INSERT INTO order_meals (order_id, meal_id) VALUES (136, 129);
INSERT INTO order_meals (order_id, meal_id) VALUES (136, 130);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (137, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Cold and tasteless', 2, '555 Starting St', '888 Destination Ave', 25, null, 'Ava', 'Garcia', '2024-12-16 13:30:00', '2024-12-31 16:00:00', 'ava.garcia@example.com', '667788990');

INSERT INTO order_meals (order_id, meal_id) VALUES (137, 131);
INSERT INTO order_meals (order_id, meal_id) VALUES (137, 132);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (138, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Not enough flavor', 3, '666 Starting St', '999 Destination Ave', 21, null, 'Jackson', 'Hernandez', '2024-12-17 14:00:00', '2025-01-01 17:00:00', 'jackson.hernandez@example.com', '778899001');

INSERT INTO order_meals (order_id, meal_id) VALUES (138, 133);
INSERT INTO order_meals (order_id, meal_id) VALUES (138, 134);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (139, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Overcooked', 2, '777 Starting St', '111 Destination Ave', 19, null, 'Lucas', 'Clark', '2024-12-18 15:00:00', '2025-01-02 18:00:00', 'lucas.clark@example.com', '889900112');

INSERT INTO order_meals (order_id, meal_id) VALUES (139, 135);
INSERT INTO order_meals (order_id, meal_id) VALUES (139, 134);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (140, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Waste of money', 1, '888 Starting St', '222 Destination Ave', 20, null, 'Emily', 'Martinez', '2024-12-19 16:30:00', '2025-01-03 19:00:00', 'emily.martinez@example.com', '990011223');

INSERT INTO order_meals (order_id, meal_id) VALUES (140, 133);
INSERT INTO order_meals (order_id, meal_id) VALUES (140, 132);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (141, 'PENDING', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Not tasty at all', 1, '999 Starting St', '123 Destination Ave', 22, null, 'Oliver', 'Taylor', '2024-12-20 17:30:00', '2025-01-04 20:00:00', 'oliver.taylor@example.com', '112233445');

INSERT INTO order_meals (order_id, meal_id) VALUES (141, 131);
INSERT INTO order_meals (order_id, meal_id) VALUES (141, 130);

-- 5 orders with status 'COMPLETED'
INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (142, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Very good meal', 5, '123 Starting St', '234 Destination Ave', 20, '2024-12-12 14:00:00', 'John', 'Doe', '2024-12-12 12:30:00', '2024-12-15 17:00:00', 'john.doe@example.com', '123456789');

INSERT INTO order_meals (order_id, meal_id) VALUES (142, 101);
INSERT INTO order_meals (order_id, meal_id) VALUES (142, 102);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (143, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Not bad', 3, '456 Starting St', '567 Destination Ave', 25, '2024-12-13 16:00:00', 'Alice', 'Smith', '2024-12-13 14:30:00', '2024-12-16 18:00:00', 'alice.smith@example.com', '234567890');

INSERT INTO order_meals (order_id, meal_id) VALUES (143, 103);
INSERT INTO order_meals (order_id, meal_id) VALUES (143, 104);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (144, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good taste, but a bit salty', 4, '789 Starting St', '890 Destination Ave', 27, '2024-12-14 18:00:00', 'Bob', 'Johnson', '2024-12-14 17:30:00', '2024-12-17 19:00:00', 'bob.johnson@example.com', '345678901');

INSERT INTO order_meals (order_id, meal_id) VALUES (144, 105);
INSERT INTO order_meals (order_id, meal_id) VALUES (144, 106);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (145, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Amazing flavor', 5, '321 Starting St', '432 Destination Ave', 30, '2024-12-15 12:00:00', 'Claire', 'Taylor', '2024-12-15 11:30:00', '2024-12-18 20:00:00', 'claire.taylor@example.com', '456789012');

INSERT INTO order_meals (order_id, meal_id) VALUES (145, 107);
INSERT INTO order_meals (order_id, meal_id) VALUES (145, 108);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (146, 'COMPLETED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Good, but could be hotter', 3, '654 Starting St', '765 Destination Ave', 22, '2024-12-16 19:30:00', 'Derek', 'White', '2024-12-16 18:00:00', '2024-12-19 21:00:00', 'derek.white@example.com', '567890123');

INSERT INTO order_meals (order_id, meal_id) VALUES (146, 109);
INSERT INTO order_meals (order_id, meal_id) VALUES (146, 110);

-- 5 orders with status 'CANCELED'
INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (147, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Too late', 2, '111 Starting St', '222 Destination Ave', 20, null, 'Eva', 'Wilson', '2024-12-17 13:00:00', '2024-12-22 16:30:00', 'eva.wilson@example.com', '678901234');

INSERT INTO order_meals (order_id, meal_id) VALUES (147, 111);
INSERT INTO order_meals (order_id, meal_id) VALUES (147, 112);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (148, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Not fresh enough', 3, '222 Starting St', '333 Destination Ave', 22, null, 'Frank', 'Miller', '2024-12-18 14:30:00', '2024-12-23 17:00:00', 'frank.miller@example.com', '789012345');

INSERT INTO order_meals (order_id, meal_id) VALUES (148, 113);
INSERT INTO order_meals (order_id, meal_id) VALUES (148, 114);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (149, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'I changed my mind', 1, '333 Starting St', '444 Destination Ave', 18, null, 'Grace', 'Brown', '2024-12-19 15:00:00', '2024-12-24 19:00:00', 'grace.brown@example.com', '890123456');

INSERT INTO order_meals (order_id, meal_id) VALUES (149, 115);
INSERT INTO order_meals (order_id, meal_id) VALUES (149, 116);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (150, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Didnt want it anymore', 1, '444 Starting St', '555 Destination Ave', 24, null, 'Hannah', 'Jones', '2024-12-20 16:30:00', '2024-12-25 20:00:00', 'hannah.jones@example.com', '901234567');

INSERT INTO order_meals (order_id, meal_id) VALUES (150, 117);
INSERT INTO order_meals (order_id, meal_id) VALUES (150, 118);

INSERT INTO orders (id, order_status, client_id, opinion, rate, starting_address, destination_address, total_price, completed_at, name, surname, order_date_time, due_date_time, email, phone_number)
VALUES
    (151, 'CANCELLED', '6c84fb95-12c4-11ec-82a8-0242ac130004', 'Food was too expensive', 2, '555 Starting St', '666 Destination Ave', 28, null, 'Ian', 'Davis', '2024-12-21 17:00:00', '2024-12-26 21:00:00', 'ian.davis@example.com', '012345678');

INSERT INTO order_meals (order_id, meal_id) VALUES (151, 119);
INSERT INTO order_meals (order_id, meal_id) VALUES (151, 120);
