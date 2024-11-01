package Library.Operations;

import Library.Books.Book;
import Library.Borrowings.Borrowing;
import Library.Database;
import Library.Users.User;

import java.util.ArrayList;
import java.util.Scanner;

// Class representing the operation to borrow a book from the library
public class BorrowBook implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Retrieve all books from the database
        ArrayList<Book> books = database.getAllBooks();

        // Display the list of available books
        System.out.println("Books:");
        for (Book book : books) {
            System.out.println(book.getName()); // Print each book's name
        }

        // Prompt user to enter the name of the book they wish to borrow
        System.out.println("Enter book name: ");
        String bookName = scanner.next(); // Read the book name input from the user

        // Check if the book exists in the database
        int bookIndex = database.getBook(bookName);

        // If the book exists
        if (bookIndex > -1) {
            Book book = database.getBook(bookIndex); // Get the book object
            boolean hasBorrowedBefore = false; // Flag to check if the user has already borrowed the book

            // Check if the user has already borrowed this book
            for (Borrowing borrowing : database.getBrws()) {
                if (borrowing.getBook().getName().matches(bookName) && borrowing.getUser().getName().matches(user.getName())) {
                    hasBorrowedBefore = true; // User has already borrowed the book
                    System.out.println("You have borrowed this book before!");
                    break; // Exit loop as we found the borrowing
                }
            }

            // If the user has not borrowed the book before
            if (!hasBorrowedBefore) {
                // Check if there are copies available for borrowing
                if (book.getBrwcopies() > 0) { // Changed to > 0 for correct availability check
                    // Create a new Borrowing object for the user
                    Borrowing borrowing = new Borrowing(book, user);
                    book.setBrwcopies(book.getBrwcopies() - 1); // Decrease the available borrowing copies
                    database.borrowBook(borrowing, book, bookIndex); // Record the borrowing in the database

                    // Inform the user of the borrowing details
                    System.out.println("You must return the book before 14 days from now.\n" +
                            "Expiry Date: " + borrowing.getFinish() + ". Enjoy!");
                } else {
                    // Inform user that the book is not available for borrowing
                    System.out.println("This book isn't available for borrowing!");
                }
            }
        } else {
            // Inform user that the book does not exist
            System.out.println("Book doesn't exist!");
        }

        // Return to the user menu
        user.menu(database, user);
    }
}
