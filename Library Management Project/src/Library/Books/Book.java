package Library.Books;

/**
 * Represents a book in the library, with details for borrowing and purchase.
 */
public class Book {

    private String name; // title
    private String author; // author
    private String publisher; // publisher
    private String address; // Collection Location
    private String status = "Available"; // Default Borrowing Status
    private int qty; // Copies for Sale
    private double price; // Price
    private int brwcopies; // Copies for Borrowing

    // Default constructor
    public Book() {}

    // Full-parameter constructor
    public Book(String name, String author, String publisher, String address, int qty, double price, int brwcopies) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.address = address;
        setQty(qty);
        setPrice(price);
        setBrwcopies(brwcopies);
    }

    @Override
    public String toString() {
        return "Book Name: " + name + "\n" +
                "Author: " + author + "\n" +
                "Collection Address: " + address + "\n" +
                "Quantity Available for Sale: " + qty + "\n" +
                "Price: $" + price + "\n" +
                "Borrowing Copies: " + brwcopies;
    }

    public String toFileString() {
        return name + "<N/>" + author + "<N/>" + publisher + "<N/>" + address + "<N/>" + qty + "<N/>" + price + "<N/>" + brwcopies;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public int getBrwcopies() {
        return brwcopies;
    }

    public void setBrwcopies(int brwcopies) {
        if (brwcopies < 0) {
            throw new IllegalArgumentException("Borrowing copies cannot be negative.");
        }
        this.brwcopies = brwcopies;
    }
}
