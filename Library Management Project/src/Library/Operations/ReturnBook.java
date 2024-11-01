package Library.Operations;

import Library.Books.Book;
import Library.Borrowings.Borrowing;
import Library.Database;
import Library.Users.User;

import java.util.Scanner;

public class ReturnBook implements IOOperation {
    @Override
    public void oper(Database database, User user) {

        // Create a scanner for user input
        Scanner sc = new Scanner(System.in);

        // Prompt the user for the name of the book to return
        System.out.print("Enter book name: ");
        String bookName = sc.nextLine();

        // Check if the user has any borrowings
        if (!database.getBrws().isEmpty()) {
            // Iterate through all borrowings to find the matching book
            for (Borrowing borrowing : database.getBrws()) {
                // Check if the current borrowing matches the user's book and name
                if (borrowing.getBook().getName().matches(bookName) && borrowing.getUser().getName().matches(user.getName())) {
                    // Get the book associated with the borrowing
                    Book book = borrowing.getBook();

                    // Find the index of the book in the database
                    int bookIndex = database.getAllBooks().indexOf(book);

                    // Check if the book is overdue
                    if (borrowing.getDaysLeft() < 0) {
                        // Calculate and display the fine for overdue books
                        System.out.println("You are late! You have to pay " + Math.abs(borrowing.getDaysLeft() * 50) + " as fine");
                    }

                    // Increase the available copies of the book
                    book.setBrwcopies(book.getBrwcopies() + 1);

                    // Return the book in the database
                    database.returnBook(borrowing, book, bookIndex);

                    // Confirm successful return
                    System.out.println("Book returned successfully!\nThank you!");
                    break; // Exit the loop after successfully returning the book
                }
            }
        } else {
            // Notify the user if there are no borrowings
            System.out.println("You didn't borrow this book!");
        }

        // Return to the user's menu
        user.menu(database, user);
    }
}
