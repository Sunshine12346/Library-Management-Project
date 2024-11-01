package Library.Operations;

import Library.Database;
import Library.Users.User;

import java.util.Scanner;

// Class representing the operation to delete all data from the library system
public class DeleteAllData implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for confirmation to delete all data
        System.out.println("Are you sure that you want to delete all data?\n1. Continue\n2. Main Menu");
        System.out.print("Enter your choice: ");
        int userChoice = scanner.nextInt(); // Read user input for confirmation

        // Check the user's choice
        if (userChoice == 1) {
            // If the user confirms, delete all data from the database
            database.deleteAllData();
            System.out.println("All data has been successfully deleted."); // Confirmation message
        } else {
            // If the user chooses not to proceed, return to the main menu
            user.menu(database, user);
        }
    }
}
