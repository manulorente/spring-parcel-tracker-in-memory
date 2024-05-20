INSERT INTO ADDRESSES (CITY, COUNTRY, EMAIL, FIRST_NAME, LAST_NAME, STREET, ZIP_CODE) VALUES 
('Madrid', 'España', 'juan.perez@ejemplo.com', 'Juan', 'Pérez', 'Calle Mayor, 1', '28013'),
('Barcelona', 'España', 'ana.garcia@ejemplo.com', 'Ana', 'García', 'Avenida Diagonal, 45', '08019'),
('Valencia', 'España', 'alicia.lopez@ejemplo.com', 'Alicia', 'López', 'Calle Colón, 23', '46004'),
('Sevilla', 'España', 'roberto.martinez@ejemplo.com', 'Roberto', 'Martínez', 'Calle Sierpes, 12', '41004'),
('Zaragoza', 'España', 'carlos.sanchez@ejemplo.com', 'Carlos', 'Sánchez', 'Calle Alfonso I, 8', '50003'),
('Bilbao', 'España', 'eva.blanco@ejemplo.com', 'Eva', 'Blanco', 'Gran Vía, 10', '48001');

INSERT INTO USERS (USERNAME, PASSWORD, ROLE, ADDRESS_ID) VALUES 
('admin', 'admin', 'ROLE_ADMIN', 1),
('cliente1', 'cliente1', 'ROLE_CUSTOMER', 2),
('cliente2', 'cliente2', 'ROLE_CUSTOMER', 3),
('cliente3', 'cliente3', 'ROLE_CUSTOMER', 4),
('cliente4', 'cliente4', 'ROLE_CUSTOMER', 5),
('cliente5', 'cliente5', 'ROLE_CUSTOMER', 6);

INSERT INTO INVOICES (CUSTOMER_INFO_ID, DATE, DUE_DATE, PRICE, TAX, TOTAL, SERVICE_INFO) VALUES 
(2, '2024-05-01', '2024-06-01', 100.00, 10.00, 110.00, 'Envío estándar de electrónica'),
(3, '2024-05-02', '2024-06-02', 250.00, 25.00, 275.00, 'Envío exprés de muebles'),
(4, '2024-05-03', '2024-06-03', 300.00, 30.00, 330.00, 'Envío urgente de documentos'),
(5, '2024-05-04', '2024-06-04', 150.00, 15.00, 165.00, 'Envío estándar de libros'),
(6, '2024-05-05', '2024-06-05', 180.00, 18.00, 198.00, 'Envío exprés de ropa');

INSERT INTO DELIVERIES (SOURCE_ID, DESTINATION_ID, PACKET_TYPE, PACKET_WEIGHT, PACKET_HEIGHT, PACKET_LENGTH, PACKET_WIDTH, DELIVERY_DATE, ESTIMATED_ARRIVAL_DATE, INVOICE_ID, STATUS, TRANSPORTATION) VALUES 
(1, 2, 'BOX', 5.0, 10.0, 15.0, 20.0, '2024-05-10', '2024-05-15', 1, 'PENDING', 'STANDARD'),
(2, 3, 'ENVELOPE', 1.0, 2.0, 3.0, 4.0, '2024-05-11', '2024-05-16', 2, 'IN_TRANSIT', 'EXPRESS'),
(3, 4, 'DOCUMENT', 0.5, 1.0, 1.5, 2.0, '2024-05-12', '2024-05-17', 3, 'DELIVERED', 'URGENT'),
(4, 5, 'BOX', 7.0, 12.0, 18.0, 25.0, '2024-05-13', '2024-05-18', 4, 'PENDING', 'STANDARD'),
(5, 6, 'BOX', 3.0, 8.0, 10.0, 15.0, '2024-05-14', '2024-05-19', 5, 'IN_TRANSIT', 'EXPRESS');

INSERT INTO COMMENTS (USER_ID, DESCRIPTION, RATING, DATE) VALUES 
(2, '¡Servicio excelente!', 4.5, '2024-05-01'),
(3, 'Muy bueno, pero podría ser más rápido.', 4.0, '2024-05-02'),
(4, 'Experiencia regular.', 3.0, '2024-05-03'),
(5, 'Excelente atención al cliente.', 5.0, '2024-05-04'),
(6, 'Rápido y fiable.', 4.8, '2024-05-05');

INSERT INTO REPORTS (DATE, TOTAL_INCOME, AVERAGE_RATING, NUMBER_OF_DELIVERIES) VALUES 
('2024-05-15', 1100.00, 4.26, 3),
('2024-06-15', 1450.00, 4.3, 4),
('2024-07-15', 1980.00, 4.42, 5);
