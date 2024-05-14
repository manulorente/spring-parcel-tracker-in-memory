-- Define the AddressInfo table schema
CREATE TABLE IF NOT EXISTS AddressInfo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    street VARCHAR(255),
    city VARCHAR(255),
    zipCode VARCHAR(10),
    country VARCHAR(255)
);
