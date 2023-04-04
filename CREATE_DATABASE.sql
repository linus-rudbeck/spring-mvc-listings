CREATE DATABASE listings_db;

USE listings_db;

CREATE TABLE listings (
	listing_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image_url VARCHAR(2048) NOT NULL,
    PRIMARY KEY (listing_id)
);

INSERT INTO listings (title, description, image_url) VALUES
    ('Some cat', 'This is a cat', 'https://placehold.it/500?text=Cat'),
    ('Some dog', 'This is a dog', 'https://placehold.it/500?text=Dog'),
    ('Some parrot', 'This is a parrot', 'https://placehold.it/500?text=Parrot');

SELECT * FROM listings;
