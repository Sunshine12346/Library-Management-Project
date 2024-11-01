package Library.Orders;

import Library.Books.Book;
import Library.Users.User;

/**
 * Represents an order for a book by a user, including book, user, price, and quantity details.
 */
public class Order {

    private Book book;
    private User user;
    private double price;
    private int qty;

    // Full-parameter constructor
    public Order(Book book, User user, double price, int qty) {
        this.book = book;
        this.user = user;
        setPrice(price);
        setQty(qty);
    }

    // Default constructor
    public Order() {}

    // Getters and setters with validation for price and quantity
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Book name: " + book.getName() + "\n" +
                "Username: " + user.getName() + "\n" +
                "Price: $" + price + "\n" +
                "Quantity: " + qty;
    }

    // Method to save the order in a format for file storage
    public String toFileString() {
        return book.getName() + "<N/>" + user.getName() + "<N/>" + Double.toString(price) + "<N/>" + Integer.toString(qty);
    }
}
