CREATE DATABASE IF NOT EXISTS PetFit;
USE PetFit;


CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, 
    coins INT DEFAULT 0,
    points INT DEFAULT 0,
    streak INT DEFAULT 0,
    workouts_completed INT DEFAULT 0,
    daily_tasks_completed INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, 
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 
);

CREATE TABLE Pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type ENUM('Dog', 'Cat', 'Rabbit', 'Dragon', 'Pig', 'Bear') NOT NULL, 
    hunger INT DEFAULT 100 CHECK (hunger BETWEEN 0 AND 100), 
    happiness INT DEFAULT 100 CHECK (happiness BETWEEN 0 AND 100), 
    health INT DEFAULT 100 CHECK (health BETWEEN 0 AND 100), 
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    item_name VARCHAR(50) NOT NULL,
    quantity INT DEFAULT 1 CHECK (quantity >= 0), -- Ensure non-negative quantity
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE WorkoutLogs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    workout_date DATETIME NOT NULL,
    workout_name VARCHAR(100), 
    workout_type ENUM('UserDefined') NOT NULL,
    duration_minutes INT NOT NULL CHECK (duration_minutes > 0), 
    calories_burned INT NOT NULL CHECK (calories_burned >= 0), 
    completed BOOLEAN DEFAULT FALSE, 
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE UserDefinedWorkouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    workout_name VARCHAR(100) NOT NULL,
    workout_details TEXT, 
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE DailyTasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    task_name VARCHAR(100) NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE, 
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_id_pets ON Pets(user_id);
CREATE INDEX idx_user_id_inventory ON Inventory(user_id);
CREATE INDEX idx_user_id_workoutlogs ON WorkoutLogs(user_id);
CREATE INDEX idx_user_id_userdefinedworkouts ON UserDefinedWorkouts(user_id);
CREATE INDEX idx_user_id_dailytasks ON DailyTasks(user_id);

SELECT * FROM Users;
SELECT * FROM Pets;
SELECT * FROM Inventory;
SELECT * FROM WorkoutLogs;
SELECT * FROM UserDefinedWorkouts;
SELECT * FROM DailyTasks;
