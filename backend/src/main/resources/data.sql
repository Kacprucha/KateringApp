CREATE TABLE IF NOT EXISTS allergen (
                                        allergen_id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS ingredient (
                                          ingredient_id SERIAL PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL
    );

INSERT INTO ingredient (ingredient_id, name) VALUES (1, 'Milk');
INSERT INTO ingredient (ingredient_id, name) VALUES (2, 'Peanut Butter');
INSERT INTO ingredient (ingredient_id, name) VALUES (3, 'Wheat Flour');
INSERT INTO ingredient (ingredient_id, name) VALUES (4, 'Soy Sauce');
INSERT INTO ingredient (ingredient_id, name) VALUES (5, 'Egg Whites');

INSERT INTO allergen (allergen_id, name) VALUES (1, 'Gluten');
INSERT INTO allergen (allergen_id, name) VALUES (2, 'Peanuts');
INSERT INTO allergen (allergen_id, name) VALUES (3, 'Dairy');
INSERT INTO allergen (allergen_id, name) VALUES (4, 'Soy');
INSERT INTO allergen (allergen_id, name) VALUES (5, 'Eggs');
INSERT INTO allergen (allergen_id, name) VALUES (6, 'Fish');
INSERT INTO allergen (allergen_id, name) VALUES (7, 'Shellfish');
INSERT INTO allergen (allergen_id, name) VALUES (8, 'Tree nuts');
INSERT INTO allergen (allergen_id, name) VALUES (9, 'Wheat');
INSERT INTO allergen (allergen_id, name) VALUES (10, 'Sesame');

-- Powiązania dla 'Milk' (np. alergeny: Dairy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (1, 3);

-- Powiązania dla 'Peanut Butter' (alergeny: Peanuts, Tree nuts)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (2, 2);
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (2, 8);

-- Powiązania dla 'Wheat Flour' (alergen: Gluten, Wheat)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (3, 1);
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (3, 9);

-- Powiązania dla 'Soy Sauce' (alergen: Soy)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (4, 4);

-- Powiązania dla 'Egg Whites' (alergen: Eggs)
INSERT INTO ingredient_allergen (ingredient_id, allergen_id) VALUES (5, 5);
