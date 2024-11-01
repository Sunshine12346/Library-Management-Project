package Library.Operations;

import Library.Books.Book;
import Library.Database;
import Library.Users.User;

import java.util.Scanner;

// Class representing the operation to add a new book to the library database
public class AddBook implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Create a new Book object
        Book book = new Book();

        // Prompt user to enter the book name
        System.out.print("Enter book name: ");
        String name = scanner.nextLine();

        // Check if a book with the same name already exists in the database
        if (database.getBook(name) > -1) {
            System.out.println("There is a book with this name!");
            user.menu(database, user); // Return to user menu if the book exists
            return; // Exit the method
        } else {
            // Set the book name if it doesn't exist
            book.setName(name);
        }

        // Prompt user to enter book details
        System.out.print("Enter book author: ");
        book.setAuthor(scanner.nextLine());

        System.out.print("Enter book publisher: ");
        book.setPublisher(scanner.nextLine());

        System.out.print("Enter book collection address: ");
        book.setAddress(scanner.nextLine());

        System.out.print("Enter quantity: ");
        book.setQty(scanner.nextInt());

        System.out.print("Enter price: ");
        book.setPrice(scanner.nextDouble());

        System.out.print("Enter borrowing copies: ");
        book.setBrwcopies(scanner.nextInt());

        // Add the book to the database
        database.addBook(book);

        // Confirm that the book has been added successfully
        System.out.println("Book added successfully!\n");

        // Return to the user menu
        user.menu(database, user);

        // Close the scanner to prevent resource leaks
        // The scanner is closed here but this can throw an exception
        // when the method is called multiple times due to scanner sharing.
        // Ideally, you would manage the scanner's lifecycle outside of this method.
        // scanner.close(); // Uncomment if this method will be the only one using the scanner
    }
}
