-- Create the PetFit database
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
    daily_tasks_completed INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Track user registration
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Track updates
);

-- Table for storing pets (linked to users)
CREATE TABLE Pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type ENUM('Dog', 'Cat', 'Rabbit', 'Dragon', 'Pig', 'Bear') NOT NULL, -- Enforce predefined pet types
    hunger INT DEFAULT 100 CHECK (hunger BETWEEN 0 AND 100), -- Valid range
    happiness INT DEFAULT 100 CHECK (happiness BETWEEN 0 AND 100), -- Valid range
    health INT DEFAULT 100 CHECK (health BETWEEN 0 AND 100), -- Valid range
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Table for tracking inventory items (linked to users)
CREATE TABLE Inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    item_name VARCHAR(50) NOT NULL,
    quantity INT DEFAULT 1 CHECK (quantity >= 0), -- Ensure non-negative quantity
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Table for logging workouts (linked to users, includes user-defined workouts)
CREATE TABLE WorkoutLogs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    workout_date DATETIME NOT NULL,
    workout_name VARCHAR(100), -- Name of the workout (custom workouts created by users)
    workout_type ENUM('UserDefined') NOT NULL, -- Differentiates workout types (user-defined only)
    duration_minutes INT NOT NULL CHECK (duration_minutes > 0), -- Positive duration
    calories_burned INT NOT NULL CHECK (calories_burned >= 0), -- Non-negative calories
    completed BOOLEAN DEFAULT FALSE, -- Indicates whether the workout was completed
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Table for storing user-defined workouts (custom workouts created by users)
CREATE TABLE UserDefinedWorkouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    workout_name VARCHAR(100) NOT NULL,
    workout_details TEXT, -- Description of the custom workout
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Table for tracking daily tasks (linked to users)
CREATE TABLE DailyTasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    task_name VARCHAR(100) NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE, -- Task completion status
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Add indexes to optimize foreign key searches
CREATE INDEX idx_user_id_pets ON Pets(user_id);
CREATE INDEX idx_user_id_inventory ON Inventory(user_id);
CREATE INDEX idx_user_id_workoutlogs ON WorkoutLogs(user_id);
CREATE INDEX idx_user_id_userdefinedworkouts ON UserDefinedWorkouts(user_id);
CREATE INDEX idx_user_id_dailytasks ON DailyTasks(user_id);

-- Test retrieval of user-specific data
SELECT * FROM Users;
SELECT * FROM Pets;
SELECT * FROM Inventory;
SELECT * FROM WorkoutLogs;
SELECT * FROM UserDefinedWorkouts;
SELECT * FROM DailyTasks;
