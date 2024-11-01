// Library Management System
package Library;

import Library.Users.Admin;
import Library.Users.NormalUser;
import Library.Users.User;

import java.util.Scanner;

public class Main {
    static Database database; // Database instance to store user data
    static Scanner sc; // Scanner for user input

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        database = new Database();

        System.out.println("Welcome to the Library Management System!");

        int choice;
        do {
            // Display menu options
            System.out.println("0. Exit\n1. Login\n2. New User");
            System.out.print("Enter choice: ");

            // Read user choice, defaulting to 0 if non-integer input
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                choice = 0;
            }

            // Execute based on user choice
            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    newUser();
                    break;
                default:
                    System.out.println("Error! Invalid Choice.");
            }
        } while (choice != 0); // Loop until user chooses to exit
    }

    // Handle user login process
    private static void login() {
        System.out.println("LOGIN");
        sc.nextLine(); // Clear any leftover input

        System.out.print("Enter Phone Number: ");
        String phoneNumber = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        // Verify user credentials
        int userIndex = database.login(phoneNumber, email);
        if (userIndex != -1) {
            User user = database.getUser(userIndex);
            if (user != null) {
                user.menu(database, user); // Show user-specific menu
            }
        } else {
            System.out.println("User not found!");
        }
    }

    // Handle new user registration
    private static void newUser() {
        System.out.println("REGISTER");
        sc.nextLine(); // Clear any leftover input

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        // Check if user already exists
        if (database.userExists(name)) {
            System.out.println("User exists!");
            newUser(); // Recursively call newUser if user exists
            return;
        }

        System.out.print("Enter Phone Number: ");
        String phoneNumber = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("1. Admin\n2. Normal User\nEnter choice: ");
        int choice2 = sc.nextInt();

        // Create user as Admin or NormalUser based on selection
        User user = (choice2 == 1) ? new Admin(name, email, phoneNumber)
                : new NormalUser(name, email, phoneNumber);

        database.addUser(user); // Add user to database
        System.out.println("User created successfully!");

        user.menu(database, user); // Show new user-specific menu
    }
}
