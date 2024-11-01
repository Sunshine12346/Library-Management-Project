package Library.Operations;

import Library.Borrowings.Borrowing;
import Library.Database;
import Library.Users.User;

import java.util.Scanner;

// Class representing the operation to calculate fines for overdue books
public class CalculateFine implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the name of the book
        System.out.print("Enter book name: ");
        String bookName = scanner.nextLine(); // Read the book name input from the user

        boolean isBookBorrowed = false; // Flag to check if the user has borrowed the book

        // Iterate through the list of borrowings in the database
        for (Borrowing borrowing : database.getBrws()) {
            // Check if the current borrowing matches the book and user
            if (borrowing.getBook().getName().matches(bookName) && borrowing.getUser().getName().matches(user.getName())) {
                // If the book is overdue
                if (borrowing.getDaysLeft() < 0) {
                    // Calculate and display the fine based on the number of overdue days
                    int fineAmount = Math.abs(borrowing.getDaysLeft()) * 50; // Calculate fine (days overdue * fine per day)
                    System.out.println("You are late! You have to pay " + fineAmount + " as fine.");
                } else {
                    // Inform the user that they don't owe any fine
                    System.out.println("You don't have to pay any fine.");
                }
                isBookBorrowed = true; // Book found, set flag to true
                break; // Exit the loop since the book has been found
            }
        }

        // If the user has not borrowed the book
        if (!isBookBorrowed) {
            // Inform the user that they haven't borrowed the book
            System.out.println("You didn't borrow the book!");
        }

        // Return to the user menu
        user.menu(database, user);
    }
}
