package Library.Operations;

import Library.Books.Book;
import Library.Database;
import Library.Users.User;

import java.util.ArrayList;
import java.util.Scanner;

// Class representing the operation to delete a book from the library system
public class DeleteBook implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Retrieve the list of all books from the database
        ArrayList<Book> bookList = database.getAllBooks();

        // Display the list of books to the user
        System.out.println("Books:");
        for (Book book : bookList) {
            System.out.println(book.getName()); // Print each book name
        }

        // Prompt the user to enter the name of the book to delete
        System.out.print("Enter book name: ");
        String bookName = scanner.next(); // Read the book name input

        // Get the index of the book to delete
        int bookIndex = database.getBook(bookName);

        // Check if the book exists
        if (bookIndex > -1) {
            // If it exists, delete the book from the database
            database.deleteBook(bookIndex);
            System.out.println("Book deleted successfully!"); // Confirm deletion
        } else {
            // If it does not exist, notify the user
            System.out.println("Book doesn't exist.");
        }

        // Return to the user menu after the operation
        user.menu(database, user);
    }
}
