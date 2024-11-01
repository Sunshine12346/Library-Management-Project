package Library.Operations;

import Library.Books.Book;
import Library.Database;
import Library.Orders.Order;
import Library.Users.User;

import java.util.Scanner;

public class PlaceOrder implements IOOperation {
    @Override
    public void oper(Database database, User user) {

        // Create a new Order object
        Order order = new Order();

        // Create a scanner for user input
        Scanner sc = new Scanner(System.in);

        // Prompt user for the book name
        System.out.print("Enter book name: ");
        String bookName = sc.next(); // Use a more descriptive variable name

        // Check if the book exists in the database
        int bookIndex = database.getBook(bookName); // Renamed for clarity
        if (bookIndex <= -1) {
            // Notify user if the book does not exist
            System.out.println("Book doesn't exist!");
        } else {
            // Retrieve the book and set it in the order
            Book book = database.getBook(bookIndex);
            order.setBook(book);
            order.setUser(user);

            // Prompt user for the quantity of books to order
            System.out.print("Enter quantity: ");
            int quantity = sc.nextInt(); // Renamed for clarity
            order.setQty(quantity);

            // Calculate the total price for the order
            order.setPrice(book.getPrice() * quantity);

            // Update the book quantity in the database
            book.setQty(book.getQty() - quantity); // Decrease available quantity
            database.addOrder(order, book, bookIndex); // Add the order to the database

            // Confirm successful order placement
            System.out.println("Order placed successfully!");
        }

        // Return to the user's menu
        user.menu(database, user);
    }
}
