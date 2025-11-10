CREATE DATABASE manhwa_store;

USE manhwa_store;

CREATE TABLE manhwa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL
);

INSERT INTO manhwa (title, author, price, stock) VALUES
('Solo Leveling', 'Chugong', 15000, 10),
('The Beginning After The End', 'TurtleMe', 17000, 5),
('Tower of God', 'SIU', 16000, 8);