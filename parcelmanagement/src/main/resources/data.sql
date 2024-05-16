INSERT INTO ADDRESS (id, email, first_name, last_name, street, city, zip_code, country) 
VALUES (1, 'test1@example.com', 'John', 'Doe', '123 Main St', 'Springfield', '12345', 'USA'),
       (2, 'test2@example.com', 'Jane', 'Smith', '456 Oak Ave', 'Riverside', '54321', 'USA');

INSERT INTO USERS (id, username, password, role, address_id)
VALUES (1, 'admin', '$2a$10$3', 'ADMIN', 1),
       (2, 'user', '$2a$10$3', 'CUSTOMER', 2);