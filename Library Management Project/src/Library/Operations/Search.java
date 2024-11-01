package Library.Operations;

import Library.Database;
import Library.Users.User;

import java.util.Scanner;

public class Search implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter the name of the book they want to search for
        System.out.print("Enter book name: ");
        String bookName = scanner.nextLine();

        // Retrieve the index of the book from the database using its name
        int bookIndex = database.getBook(bookName);

        // Check if the book exists in the database
        if (bookIndex > -1) {
            // If the book is found, print its details
            System.out.println(database.getBook(bookIndex).toString());
        } else {
            // If the book is not found, inform the user
            System.out.println("Book doesn't exist");
        }

        // Return to the user's menu
        user.menu(database, user);
    }
}
