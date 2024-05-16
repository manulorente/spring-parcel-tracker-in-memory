INSERT INTO USERS (id, username, password, role, email, first_name, last_name, street, city, zip_code, country)
VALUES (1, 'admin', '$2a$10$3', 'ADMIN', 'test1@example.com', 'John', 'Doe', '123 Main St', 'Springfield', '12345', 'USA'),
       (2, 'user', '$2a$10$3', 'CUSTOMER', 'test2@example.com', 'Jane', 'Smith', '456 Oak Ave', 'Riverside', '54321', 'USA');