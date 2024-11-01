package Library.Users;

import Library.Database;
import Library.Operations.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a normal user in the library system with specific permissions.
 */
public class NormalUser extends User {

    // Constructors initialize the user's operations
    public NormalUser(String name) {
        super(name);
        initializeOperations();
    }

    public NormalUser(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
        initializeOperations();
    }

    // Initialize available operations for a normal user
    private void initializeOperations() {
        this.operations = new IOOperation[] {
                new ViewBooks(),
                new Search(),
                new PlaceOrder(),
                new BorrowBook(),
                new CalculateFine(),
                new ReturnBook(),
                new Exit()
        };
    }

    @Override
    public String toString() {
        return name + "<N/>" + email + "<N/>" + phonenumber + "<N/>" + "Normal";
    }

    /**
     * Displays the menu and processes user choices in a loop until 'Exit' is chosen.
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
        System.out.println("\n--- Normal User Menu ---");
        System.out.println("1. View Books");
        System.out.println("2. Search");
        System.out.println("3. Place Order");
        System.out.println("4. Borrow Book");
        System.out.println("5. Calculate Fine");
        System.out.println("6. Return Book");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }
}
