package Library.Operations;

import Library.Books.Book;
import Library.Database;
import Library.Users.User;

import java.util.ArrayList;

public class ViewBooks implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Retrieve the list of all books from the database
        ArrayList<Book> bookList = database.getAllBooks();

        // Check if there are no books available
        if (bookList.isEmpty()) {
            System.out.println("No books are there at the moment!");
            user.menu(database, user); // Return to user menu
            return; // Exit the method
        }

        // Print the header for the book details table
        System.out.printf("%-20s %-20s %-20s %-10s %-10s %-10s %-10s %-10s %n",
                "Name", "Author", "Publisher", "CLA", "Status", "Quantity", "Price", "Brw cps");

        // Iterate through the list of books and print their details
        for (Book book : bookList) {
            System.out.printf("%-20s %-20s %-20s %-10s %-10s %-10d %-10.2f %-10d %n",
                    book.getName(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getAddress(),
                    book.getStatus(),
                    book.getQty(),
                    book.getPrice(),
                    book.getBrwcopies());
        }

        // Return to the user's menu after displaying the books
        user.menu(database, user);
    }
}
