package Library.Operations;

import Library.Database;
import Library.Users.Admin;
import Library.Users.NormalUser;
import Library.Users.User;

import java.util.Scanner;

// Class representing the exit operation for the library system
public class Exit implements IOOperation {

    private Database database; // Database reference to interact with user data
    private User user; // Current user reference

    private static Scanner scanner; // Scanner for user input

    @Override
    public void oper(Database database, User user) {
        this.database = database; // Assigning the passed database
        this.user = user; // Assigning the passed user

        scanner = new Scanner(System.in); // Initializing the scanner

        // Prompt the user to confirm exit
        System.out.println("Are you sure that you want to exit?\n1. Yes\n2. No");

        int choice = -1; // Variable to hold user choice

        // Loop until the user provides a valid choice
        while (choice != 1 && choice != 2) { // Changed to AND condition to check for valid input
            System.out.print("Enter: ");
            choice = scanner.nextInt(); // Read user choice

            switch (choice) {
                case 1: // User wants to exit
                    handleExit(); // Handle the exit flow
                    break;
                case 2: // User doesn't want to exit
                    user.menu(database, user); // Return to the user menu
                    break;
                default:
                    System.out.println("Invalid Choice!"); // Invalid input message
            }
        }
    }

    // Handles the exit flow when the user confirms to exit
    private void handleExit() {
        System.out.println("0. Exit\n1. Login\n2. New User");
        System.out.print("Enter: ");

        int nextChoice;
        // Check if the next input is an integer
        if (scanner.hasNextInt()) {
            nextChoice = scanner.nextInt(); // Read the next choice
        } else {
            nextChoice = 0; // Default choice if input is invalid
        }

        // Handle the choice for next actions
        switch (nextChoice) {
            case 0:
                System.out.println("Exiting..."); // Exit message
                return; // Exit the program
            case 1:
                login(); // Call login method
                return; // Exit after login
            case 2:
                newUser(); // Call new user registration method
                return; // Exit after registration
            default:
                System.out.println("Error! Invalid Choice."); // Invalid choice message
        }
    }

    // Method for user login
    private void login() {
        System.out.println("LOGIN");

        scanner.nextLine(); // Clear the buffer

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine(); // Read phone number

        System.out.print("Enter Email: ");
        String email = scanner.nextLine(); // Read email

        int userIndex = database.login(phoneNumber, email); // Attempt to login
        if (userIndex != -1) {
            User user = database.getUser(userIndex); // Retrieve user from database
            if (user != null) {
                user.menu(database, user); // Go to user menu
            }
        } else {
            System.out.println("User not found!"); // User not found message
        }
    }

    // Method for new user registration
    private void newUser() {
        System.out.println("REGISTER");

        scanner.nextLine(); // Clear the buffer

        System.out.print("Enter Name: ");
        String name = scanner.nextLine(); // Read user name

        // Check if the user already exists
        if (database.userExists(name)) {
            System.out.println("User exists!"); // Message if user exists
            newUser(); // Prompt to register again
        }

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine(); // Read phone number

        System.out.print("Enter Email: ");
        String email = scanner.nextLine(); // Read email

        System.out.print("1. Admin\n2. Normal User\nEnter: ");
        int userTypeChoice = scanner.nextInt(); // Read user type choice

        User newUser;
        // Create user based on choice
        if (userTypeChoice == 1) {
            newUser = new Admin(name, email, phoneNumber); // Create admin user
        } else {
            newUser = new NormalUser(name, email, phoneNumber); // Create normal user
        }
        database.addUser(newUser); // Add user to the database
        System.out.println("User created successfully!"); // Confirmation message

        newUser.menu(database, newUser); // Go to user menu
    }
}
