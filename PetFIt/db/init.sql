CREATE DATABASE IF NOT EXISTS PetFit;
USE PetFit;

-- Table for storing user details
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Passwords should ideally be hashed
    coins INT DEFAULT 0,
    points INT DEFAULT 0,
    streak INT DEFAULT 0,
    workouts_completed INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Track user registration
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Track updates
);

-- Table for storing pets
CREATE TABLE Pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type ENUM('Dog', 'Cat', 'Rabbit', 'Dragon', 'Pig', 'Bear') NOT NULL, 
    hunger INT DEFAULT 100 CHECK (hunger BETWEEN 0 AND 100), 
    happiness INT DEFAULT 100 CHECK (happiness BETWEEN 0 AND 100), 
    health INT DEFAULT 100 CHECK (health BETWEEN 0 AND 100), 
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Indexes to optimize foreign key searches
CREATE INDEX idx_user_id_pets ON Pets(user_id);

SELECT * FROM Users;
SELECT * FROM Pets;

-- INSERT INITIAL DATA
INSERT INTO Users (username, password, coins, points, streak, workouts_completed)
VALUES
('testuser1', 'password1', 100, 500, 5, 10),
('testuser2', 'password2', 200, 1000, 10, 20),
('infinite', 'money', 99999, 9999, 9999, 9999);

INSERT INTO Pets (user_id, type, hunger, happiness, health)
VALUES
(1, 'Dog', 80, 90, 85),  
(2, 'Rabbit', 50, 85, 75);
