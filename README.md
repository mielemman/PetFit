# PetFit: A Virtual Pet and Fitness Tracking Application

## Project Description

**PetFit** is an interactive Java-based console application designed to motivate and engage users in maintaining a healthy lifestyle by combining fitness tracking with virtual pet care. The application provides an immersive experience where users can complete workouts, take care of virtual pets, and track their progress in real time.

The core concept behind **PetFit** is simple: users set fitness goals and complete workouts to earn coins, which can be used to purchase and care for virtual pets. The more consistent the user is in their workouts, the more progress they make, earning rewards and unlocking new pets. Each pet in the game requires food, playtime, and attention, creating an engaging way to encourage daily activity. Additionally, **PetFit** provides motivational tips, habit tracking, and a leaderboard system to further incentivize users.

This application is designed for users of all ages who are looking for an enjoyable way to stay active while also caring for virtual pets. Whether you’re a fitness enthusiast looking for motivation or someone who enjoys virtual pet games, **PetFit** combines both in a way that makes fitness fun and rewarding.

### Features:

- **Fitness Tracking**: Users can set and track their workout goals, marking progress with detailed reports and habit tracking.
- **Virtual Pet Care**: Users can adopt and care for a variety of pets, each with its own unique needs and benefits.
- **Coins and Rewards System**: Completing workouts earns coins, which can be used to feed and play with pets, unlocking new activities and rewards.
- **Progress Reports**: View cumulative statistics on workouts, pet care, and milestones.
- **Motivational Tips**: Receive daily encouragement to stay on track with your fitness goals.

### Technology Stack:

- **Java**: The primary programming language used to implement the core functionality of the application.
- **MySQL**: Used for data storage, including user information, pet data, coins, and workout progress.
- **JDBC**: Utilized to interact with the MySQL database, ensuring smooth data management and retrieval.
- **VSCode**: The integrated development environment (IDE) used for code development and testing.

---

## II. Explanation of how OOP principles were applied

The **PetFit** application is built with core **Object-Oriented Programming (OOP)** principles, ensuring the system is modular, scalable, and maintainable. OOP focuses on organizing software design around objects, which can be defined by their attributes and behaviors. The main OOP principles applied in the **PetFit** application include:

### 1. **Encapsulation**:
Encapsulation refers to the bundling of data (attributes) and methods (behaviors) that operate on that data within a single unit or class. In **PetFit**, this is evident in how both Pet and User objects encapsulate their respective states and behaviors.

- The Pet class holds attributes like name, hunger, happiness, and health. Each pet also has methods such as feed(), play(), and petAction() to change their state (e.g., updating hunger levels or happiness).

- The User class encapsulates the user's information such as username, password, fitnessLevel, and coins. It includes methods that allow interaction with the user's pets, workouts, and goals.
  
This encapsulation keeps the internal state of objects private and only accessible through well-defined public methods, making the code more secure and easier to manage.

### 2. **Abstraction**:
Abstraction simplifies complex systems by providing a high-level interface while hiding unnecessary details. In **PetFit**, this is applied through the use of base classes and overriding methods in subclasses.

- The Pet class acts as an abstract concept for all pets. While the Pet class defines common attributes and actions (like feeding or playing), it does not specify the exact behavior for different types of pets (e.g., how a **Bear** or a **Dog** plays). This level of abstraction ensures that the application can define behaviors for different pets without modifying the base structure.
  
- Each specific pet class (e.g., Bear, Dog, Dragon) overrides methods like feed(), play(), and petAction() to provide unique implementations specific to the pet type. For example, the Dog class might print a message like "is running around and playing fetch!" whereas the Bear class might say "is roaring and hunting in the forest!".

This abstraction allows for a clean, maintainable codebase where new types of pets can be added easily without altering existing code.

### 3. **Inheritance**:
Inheritance allows a class (subclass) to inherit attributes and behaviors from another class (superclass). This promotes reusability and modularity in the application. In **PetFit**, inheritance is used extensively:

- The Pet class is the superclass that provides common properties and methods shared by all pet types. It defines general attributes like hunger, happiness, health, and methods such as feed(), play(), and petAction(). 
- Specific pet classes like Bear, Dog, Dragon, Pig, and Rabbit inherit from the Pet class. Each subclass provides its own implementation for the methods like feed() and play(), making them unique to each pet type.

This inheritance structure allows the application to maintain a clean and hierarchical organization, ensuring that common functionality is defined once in the Pet class and then extended by individual pet types.

#### Example: The **Bear** class
java

public class Bear extends Pet {
    public Bear(String name) {
        super(name, "Bear ASCII Art");
        this.hunger = 50;
        this.happiness = 50;
        this.health = 100;
    }

    @Override
    public void feed() {
        this.hunger = 100; // Full after feeding
        System.out.println(name + " has been fed and is now full.");
    }

    @Override
    public void play() {
        this.happiness = 100; // Happy after play
        System.out.println(name + " is roaring and hunting in the forest!");
    }

    @Override
    public void petAction() {
        System.out.println(name + " is resting and enjoying the peace.");
    }
}

---
## III. Details of the Chosen SDG and Its Integration into the PetFit Project

The **PetFit** project is designed with the core aim of promoting **SDG 3: Good Health and Well-Being**, which emphasizes ensuring healthy lives and promoting well-being for all. By integrating fitness tracking with virtual pet care, PetFit addresses key aspects of physical and mental health through engaging, interactive features.

### 1. Alignment with SDG 3

**SDG 3: Good Health and Well-Being** focuses on achieving universal access to healthcare, reducing the burden of diseases, and promoting both physical and mental well-being for people of all ages. The goals of SDG 3 include:

- Promoting physical activity to reduce the risk of non-communicable diseases.
- Encouraging mental health and well-being through engaging and supportive activities.
- Ensuring access to affordable healthcare and reducing health inequalities.

PetFit aligns with these goals by offering a system that combines physical fitness tracking with a gamified pet care experience. The virtual pet care component provides users with a sense of responsibility and emotional well-being, while the fitness tracking motivates users to stay active and healthy.

### 2. Integration of SDG 3 into PetFit

The **PetFit** project integrates SDG 3 by promoting both physical health and mental well-being through the following features:

- **Fitness Tracking and Workouts**:  
  PetFit allows users to set fitness goals and track their workout progress, encouraging them to stay active. Completing workouts earns coins, which are used to care for virtual pets. This system incentivizes physical activity, reducing the risk of lifestyle diseases and promoting overall fitness.

- **Virtual Pet Care**:  
  The pet care aspect of PetFit encourages users to feed, play with, and take care of virtual pets. This not only motivates users to stay consistent in their workouts but also offers a sense of emotional fulfillment, which can contribute to better mental health. The act of caring for pets can reduce stress, promote relaxation, and foster a sense of purpose.

- **Motivational Tips and Goal Progression**:  
  PetFit offers motivational messages and progress tracking, which helps users stay committed to their fitness and pet care routines. These features encourage users to maintain consistency, boosting self-esteem and mental well-being by providing positive reinforcement.


Through these features, **PetFit** not only promotes physical health by encouraging regular exercise but also supports mental well-being by providing a rewarding and engaging experience. The combination of fitness and virtual pet care creates an environment that motivates users to improve both their physical and emotional health, in line with the objectives of **SDG 3**.

# PetFit Project

## IV. Instructions for Running the Program

# PetFit Project

## IV. Instructions for Running the Program

Follow the instructions below to set up your environment, configure dependencies, and run the **PetFit** application.

### Prerequisites

Before you begin, ensure the following are installed on your system:

- **Java Development Kit (JDK)** 8 or later (preferably JDK 11 or higher)
- **MySQL** server installed and running
- **MySQL Workbench** (optional, for easier database management)
- **VSCode** or any Java IDE (such as IntelliJ IDEA or Eclipse)

### Steps for Running the PetFit Application

#### 1. Clone the Repository

Clone the **PetFit** repository to your local machine.

```bash
git clone https://github.com/yourusername/petfit.git
```

2. Set Up the Database
PetFit uses a MySQL database to manage user data, pet stats, and progress. Follow the steps below to set up the database:

Create a MySQL Database:

Open MySQL Workbench or connect to your MySQL server via terminal.

Create a new database by running the following SQL command:
CREATE DATABASE petfit;

Import the Database Schema:

Inside the project folder, navigate to the db/ directory and locate the init.sql file.

Run the init.sql script to initialize the required tables and data:

bash
Copy code
mysql -u username -p petfit < db/init.sql
Replace username with your MySQL username. This will set up the necessary tables for user information, pet stats, and progress tracking.

3. Configure Database Connection
Update Database Connection Settings:

Navigate to the src/utilities/DatabaseConnection.java file.

Update the MySQL connection URL, username, and password with your MySQL server details:

java
Copy code
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/petfit";
    private static final String USERNAME = "your_mysql_username";
    private static final String PASSWORD = "your_mysql_password";
}
Ensure that the MySQL connector JAR file (mysql-connector-j-9.1.0.jar) is placed in the libs/ directory. If it's missing, download the JAR file from the official MySQL website and place it in the libs/ folder.

4. Set Up VSCode or IDE
VSCode Setup:

Open the project folder in VSCode.
Ensure that you have the Java Extension Pack installed for Java support, which includes syntax highlighting, Maven/Gradle, etc.
Check if your settings are configured by viewing or modifying the settings.json file located in the .vscode/ directory.
IDE Setup:

If using an IDE like IntelliJ IDEA or Eclipse, import the project as a Java project.
Make sure the project’s JDK version matches the version specified in the prerequisites.
5. Run the Application
Running from Main:

The main entry point for the PetFit application is Main.java.

Open src/main/Main.java in your IDE or VSCode and run the file.

If you're using VSCode, open the terminal and type:

bash
Copy code
javac src/main/Main.java
java src.main.Main
This will compile and run the application.

Running from Command Line:

Alternatively, you can run the application from the command line. Open a terminal window in the root directory of the project and use the following commands:

bash
Copy code
javac -d bin src/main/Main.java
java -cp bin src.main.Main
6. Application Interface
Once the application is running, follow the on-screen prompts:

The main menu will appear, where you can:
Create an account or log in.
Track workouts and fitness progress.
Take care of your virtual pet(s).
Visit the shop to purchase items for your pets.
View motivational messages and progress reports.
7. Interacting with the PetFit App
Workouts: Choose from predefined workouts or create your own. Each completed workout earns coins to care for your virtual pet.
Pet Care: Feed and interact with your virtual pet to keep it happy and healthy.
Leaderboards: Track your progress against other users through the global leaderboard.
Progress Tracking: View your overall progress and set fitness goals.
8. Troubleshooting
Database Connection Issues:

Double-check your MySQL credentials in the DatabaseConnection.java file.
Ensure your MySQL server is running.
Verify that the database schema was correctly set up using the init.sql script.
Missing JAR File:

If the MySQL connector JAR file is missing, download it from the MySQL website and place it in the libs/ directory.
Application Not Starting:

Ensure that Java is properly installed and configured on your system.
Verify that all files are correctly compiled (use javac to compile if needed).
Review error logs for any missing dependencies or incorrect configurations.
