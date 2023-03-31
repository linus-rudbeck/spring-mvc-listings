CREATE DATABASE listings_db;

USE listings_db;

CREATE TABLE listings (
	listing_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image_url VARCHAR(2048) NOT NULL,
    PRIMARY KEY (listing_id)
);

SELECT * FROM listings;