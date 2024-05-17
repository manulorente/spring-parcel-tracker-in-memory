INSERT INTO addresses (id, email, first_name, last_name, street, city, zip_code, country) VALUES 
(1, 'admin1@example.com', 'John', 'Doe', '123 Admin St', 'Admin City', '12345', 'Admin Country'),
(2, 'customer1@example.com', 'Jane', 'Smith', '456 Oak Ave', 'Riverside', '54321', 'USA'),
(3, 'source@example.com', 'Source', 'Address', '123 Source St', 'Source City', '12345', 'Source Country'),
(4, 'destination@example.com', 'Destination', 'Address', '123 Destination St', 'Destination City', '54321', 'Destination Country'),
(5, 'customerInfo@example.com', 'Customer', 'Info', '456 CustomerInfo St', 'CustomerInfo City', '67890', 'CustomerInfo Country');

INSERT INTO invoices (id, price, tax, date, due_date, total, customer_info_id, service_info) VALUES
(1, 100.0, 10.0, '2023-01-01', '2023-01-31', 110.0, 5, 'Company Info'),
(2, 150.0, 15.0, '2023-01-02', '2023-02-01', 165.0, 5, 'Company Info');

INSERT INTO users (id, username, password, role, address_id) VALUES 
(1, 'admin', '$2a$10$3', 'ADMIN', 1),
(2, 'customer1', '$2a$10$3', 'CUSTOMER', 2);

INSERT INTO deliveries (id, packet_type, packet_weight, packet_height, packet_width, packet_length, transportation, source_id, destination_id, delivery_date, estimated_arrival_date, status, invoice_id) 
VALUES 
(1, 'BOX', 1.5, 10.0, 20.0, 30.0, 'STANDARD', 3, 4, '2023-01-01', '2023-01-05', 'IN_TRANSIT', 1),
(2, 'ENVELOPE', 0.5, 5.0, 10.0, 15.0, 'EXPRESS', 3, 4, '2023-01-02', '2023-01-03', 'PENDING', 2);

INSERT INTO comments (id, user_id, date, rating, description) 
VALUES 
(1, 2, '2023-01-01', 4.5, 'Great service!'),
(2, 2, '2023-01-02', 3.5, 'Good service!');

INSERT INTO reports (id, number_of_deliveries, average_rating, total_income) 
VALUES 
(1, 2, 4.5, 220.0),
(2, 1, 3.5, 110.0);
