package Library.Operations;

import Library.Database;
import Library.Orders.Order;
import Library.Users.User;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewOrders implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        Scanner sc = new Scanner(System.in);

        // Prompt the user to enter the book name for which orders are to be viewed
        System.out.print("Enter book name: ");
        String bookName = sc.next();

        // Check if the book exists in the database
        int bookIndex = database.getBook(bookName);

        if (bookIndex > -1) {
            // Create a list to store orders related to the specified book
            ArrayList<Order> orders = new ArrayList<>();
            System.out.printf("%-10s %-10s %-15s %-10s %n", "Book", "User", "Quantity", "Price");

            // Iterate through all orders and display those related to the specified book
            for (Order order : database.getAllOrders()) {
                if (order.getBook().getName().matches(bookName)) {
                    System.out.printf("%-10s %-10s %-15s %-10s %n",
                            order.getBook().getName(),
                            order.getUser().getName(),
                            order.getQty(),
                            order.getPrice());
                }
            }
            System.out.println(); // Print a new line after displaying orders
        } else {
            // Inform the user if the book does not exist
            System.out.println("Book doesn't exist!");
        }

        // Return to the user's menu
        user.menu(database, user);
    }
}
