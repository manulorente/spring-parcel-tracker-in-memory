INSERT INTO ADDRESS (email, firstName, lastName, street, city, zipCode, country) 
VALUES ('test1@example.com', 'John', 'Doe', '123 Main St', 'Springfield', '12345', 'USA'),
       ('test2@example.com', 'Jane', 'Smith', '456 Oak Ave', 'Riverside', '54321', 'USA');

INSERT INTO USERS (username, password, role, address_id)
VALUES ('admin', '$2a$10$3', 'ADMIN', 1),
       ('user', '$2a$10$3', 'CUSTOMER', 2);