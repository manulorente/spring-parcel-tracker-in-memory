-- Insert addresses
INSERT INTO ADDRESSES (CITY, COUNTRY, EMAIL, FIRST_NAME, LAST_NAME, STREET, ZIP_CODE) VALUES 
('Anytown', 'CountryA', 'john.doe@example.com', 'John', 'Doe', '123 Main St', '12345'),
('Othertown', 'CountryB', 'jane.smith@example.com', 'Jane', 'Smith', '456 Maple Ave', '67890'),
('Sometown', 'CountryC', 'alice.jones@example.com', 'Alice', 'Jones', '789 Elm St', '13579'),
('Newcity', 'CountryD', 'bob.brown@example.com', 'Bob', 'Brown', '101 Pine St', '24680'),
('Oldtown', 'CountryE', 'charlie.davis@example.com', 'Charlie', 'Davis', '202 Oak St', '11223'),
('Springfield', 'CountryF', 'eve.white@example.com', 'Eve', 'White', '303 Birch St', '44556');

-- Insert users
INSERT INTO USERS (USERNAME, PASSWORD, ROLE, ADDRESS_ID) VALUES 
('admin', 'admin', 'ROLE_ADMIN', 1),
('customer1', 'customer1', 'ROLE_CUSTOMER', 2),
('customer2', 'customer2', 'ROLE_CUSTOMER', 3),
('customer3', 'customer3', 'ROLE_CUSTOMER', 4),
('customer4', 'customer4', 'ROLE_CUSTOMER', 5),
('customer5', 'customer5', 'ROLE_CUSTOMER', 6);

-- Insert invoices
INSERT INTO INVOICES (CUSTOMER_INFO_ID, DATE, DUE_DATE, PRICE, TAX, TOTAL, SERVICE_INFO) VALUES 
(2, '2024-05-01', '2024-06-01', 100.00, 10.00, 110.00, 'Standard Shipping for electronics'),
(3, '2024-05-02', '2024-06-02', 250.00, 25.00, 275.00, 'Express Shipping for furniture'),
(4, '2024-05-03', '2024-06-03', 300.00, 30.00, 330.00, 'Urgent Shipping for documents'),
(5, '2024-05-04', '2024-06-04', 150.00, 15.00, 165.00, 'Standard Shipping for books'),
(6, '2024-05-05', '2024-06-05', 180.00, 18.00, 198.00, 'Express Shipping for clothing');

-- Insert deliveries
INSERT INTO DELIVERIES (SOURCE_ID, DESTINATION_ID, PACKET_TYPE, PACKET_WEIGHT, PACKET_HEIGHT, PACKET_LENGTH, PACKET_WIDTH, DELIVERY_DATE, ESTIMATED_ARRIVAL_DATE, INVOICE_ID, STATUS, TRANSPORTATION) VALUES 
(1, 2, 'BOX', 5.0, 10.0, 15.0, 20.0, '2024-05-10', '2024-05-15', 1, 'PENDING', 'STANDARD'),
(2, 3, 'ENVELOPE', 1.0, 2.0, 3.0, 4.0, '2024-05-11', '2024-05-16', 2, 'IN_TRANSIT', 'EXPRESS'),
(3, 4, 'DOCUMENT', 0.5, 1.0, 1.5, 2.0, '2024-05-12', '2024-05-17', 3, 'DELIVERED', 'URGENT'),
(4, 5, 'BOX', 7.0, 12.0, 18.0, 25.0, '2024-05-13', '2024-05-18', 4, 'PENDING', 'STANDARD'),
(5, 6, 'BOX', 3.0, 8.0, 10.0, 15.0, '2024-05-14', '2024-05-19', 5, 'IN_TRANSIT', 'EXPRESS');

-- Insert comments
INSERT INTO COMMENTS (USER_ID, DESCRIPTION, RATING, DATE) VALUES 
(2, 'Excellent service!', 4.5, '2024-05-01'),
(3, 'Very good, but could be faster.', 4.0, '2024-05-02'),
(4, 'Average experience.', 3.0, '2024-05-03'),
(5, 'Great customer support.', 5.0, '2024-05-04'),
(6, 'Quick and reliable.', 4.8, '2024-05-05');

-- Insert reports
INSERT INTO REPORTS (DATE, TOTAL_INCOME, AVERAGE_RATING, NUMBER_OF_DELIVERIES) VALUES 
('2024-05-15', 1100.00, 4.26, 3),
('2024-06-15', 1450.00, 4.3, 4),
('2024-07-15', 1980.00, 4.42, 5);
