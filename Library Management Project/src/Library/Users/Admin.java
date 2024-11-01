package Library.Users;

import Library.Database;
import Library.Operations.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents an admin user with advanced permissions in the library system.
 */
public class Admin extends User {

    // Constructors initialize the admin's operations
    public Admin(String name) {
        super(name);
        initializeOperations();
    }

    public Admin(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
        initializeOperations();
    }

    // Initialize available operations for an admin
    private void initializeOperations() {
        this.operations = new IOOperation[] {
                new ViewBooks(),
                new AddBook(),
                new DeleteBook(),
                new Search(),
                new DeleteAllData(),
                new ViewOrders(),
                new Exit()
        };
    }

    @Override
    public String toString() {
        return name + "<N/>" + email + "<N/>" + phonenumber + "<N/>" + "Admin";
    }

    /**
     * Displays the menu and processes admin choices in a loop until 'Exit' is chosen.
     * @param database the library database to operate on.
     * @param user the current user.
     */
    @Override
    public void menu(Database database, User user) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 7) {
            displayMenu();
            try {
                choice = sc.nextInt();

                if (choice < 1 || choice > 7) {
                    System.out.println("Invalid choice! Please select a valid option.");
                } else {
                    operations[choice - 1].oper(database, user);
                }

            } catch (InputMismatchException e) {
                System.out.println("Error! Please enter a valid number.");
                sc.next(); // Clear invalid input
            }
        }

        System.out.println("Exiting the menu. Goodbye!");
    }

    // Helper method to display the menu options
    private void displayMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View Books");
        System.out.println("2. Add Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Search");
        System.out.println("5. Delete All Data");
        System.out.println("6. View Orders");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }
}
