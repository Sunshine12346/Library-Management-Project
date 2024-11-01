package Library;

import Library.Books.Book;
import Library.Borrowings.Borrowing;
import Library.Orders.Order;
import Library.Users.Admin;
import Library.Users.NormalUser;
import Library.Users.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {

    // Collections for storing library data
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<String> bookNames = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Borrowing> borrowings = new ArrayList<>();

    // File paths for data storage
    private final String usersFile = "Data/Users";
    private final String booksFile = "Data/Books";
    private final String ordersFile = "Data/Orders";
    private final String borrowingsFile = "Data/Borrowings";

    // Constructor to initialize the database from file data
    public Database() {
        getUsers();
        getBooks();
        getOrders();
        getBorrowings();
    }

    // User operations
    public void addUser(User user) {
        users.add(user);
        usernames.add(user.getName());
        saveUsers();
    }

    public int login(String phoneNumber, String email) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber) && user.getEmail().equals(email)) {
                return users.indexOf(user);
            }
        }
        return -1; // User not found
    }

    public User getUser(int userIndex) {
        return users.get(userIndex);
    }

    // Book operations
    public void addBook(Book book) {
        books.add(book);
        bookNames.add(book.getName());
        saveBooks();
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public int getBook(String bookName) {
        for (Book book : books) {
            if (book.getName().equals(bookName)) {
                return books.indexOf(book);
            }
        }
        return -1; // Book not found
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public void deleteBook(int index) {
        orders.removeIf(order -> order.getBook().getName().matches(books.get(index).getName()));
        borrowings.removeIf(borrowing -> borrowing.getBook().getName().matches(books.get(index).getName()));

        books.remove(index);
        bookNames.remove(index);
        saveBooks();
        saveOrders();
    }

    // Order operations
    public void addOrder(Order order, Book book, int bookIndex) {
        orders.add(order);
        books.set(bookIndex, book);
        saveOrders();
        saveBooks();
    }

    public ArrayList<Order> getAllOrders() {
        return orders;
    }

    // Borrowing operations
    public void borrowBook(Borrowing brw, Book book, int bookIndex) {
        borrowings.add(brw);
        books.set(bookIndex, book);
        saveBorrowings();
        saveBooks();
    }

    public ArrayList<Borrowing> getBrws() {
        return borrowings;
    }

    public void returnBook(Borrowing brw, Book book, int bookIndex) {
        borrowings.remove(brw);
        books.set(bookIndex, book);
        saveBorrowings();
        saveBooks();
    }

    // Data retrieval methods
    private void getUsers() {
        StringBuilder text = readFile(usersFile);
        if (!text.toString().isEmpty()) {
            for (String userData : text.toString().split("<NewUser/>")) {
                String[] details = userData.split("<N/>");
                User user = details[3].equals("Admin")
                        ? new Admin(details[0], details[1], details[2])
                        : new NormalUser(details[0], details[1], details[2]);
                users.add(user);
                usernames.add(user.getName());
            }
        }
    }

    private void getBooks() {
        StringBuilder text = readFile(booksFile);
        if (!text.toString().isEmpty()) {
            for (String bookData : text.toString().split("<NewBook/>")) {
                Book book = parseBook(bookData);
                books.add(book);
                bookNames.add(book.getName());
            }
        }
    }

    private void getOrders() {
        StringBuilder text = readFile(ordersFile);
        if (!text.toString().isEmpty()) {
            for (String orderData : text.toString().split("<NewOrder/>")) {
                System.out.println(orderData);
                orders.add(parseOrder(orderData));
            }
        }
    }

    private void getBorrowings() {
        StringBuilder text = readFile(borrowingsFile);
        if (!text.toString().isEmpty()) {
            for (String borrowingData : text.toString().split("<NewBorrowing/>")) {
                borrowings.add(parseBorrowing(borrowingData));
            }
        }
    }

    // Parsing methods
    private Book parseBook(String data) {
        String[] details = data.split("<N/>");
        Book book = new Book();
        book.setName(details[0]);
        book.setAuthor(details[1]);
        book.setPublisher(details[2]);
        book.setAddress(details[3]);
        book.setQty(Integer.parseInt(details[4]));
        book.setPrice(Double.parseDouble(details[5]));
        book.setBrwcopies(Integer.parseInt(details[6]));
        return book;
    }

    private Order parseOrder(String data) {
        String[] details = data.split("<N/>");
        return new Order(getBook(getBook(details[0])), getUserByName(details[1]),
                Double.parseDouble(details[2]), Integer.parseInt(details[3]));
    }

    private Borrowing parseBorrowing(String data) {
        String[] details = data.split("<N/>");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(details[0], formatter);
        LocalDate finish = LocalDate.parse(details[1], formatter);
        Book book = getBook(getBook(details[3]));
        User user = getUserByName(details[4]);
        return new Borrowing(start, finish, book, user);
    }

    // File I/O helpers
    private StringBuilder readFile(String filePath) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e);
        }
        return text;
    }

    private void writeFile(String filePath, StringBuilder data) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print(data);
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    private void saveUsers() {
        StringBuilder data = new StringBuilder();
        for (User user : users) {
            data.append(user.toString()).append("<NewUser/>\n");
        }
        writeFile(usersFile, data);
    }

    private void saveBooks() {
        StringBuilder data = new StringBuilder();
        for (Book book : books) {
            data.append(book.toFileString()).append("<NewBook/>\n");
        }
        writeFile(booksFile, data);
    }

    private void saveOrders() {
        StringBuilder data = new StringBuilder();
        for (Order order : orders) {
            data.append(order.toFileString()).append("<NewOrder/>\n");
        }
        writeFile(ordersFile, data);
    }

    private void saveBorrowings() {
        StringBuilder data = new StringBuilder();
        for (Borrowing borrowing : borrowings) {
            data.append(borrowing.toFileString()).append("<NewBorrowing/>\n");
        }
        writeFile(borrowingsFile, data);
    }

    // Utility methods
    public boolean userExists(String name) {
        return usernames.contains(name.toLowerCase());
    }

    private User getUserByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(new NormalUser(""));
    }

    // Data clearing
    public void deleteAllData() {
        writeFile(usersFile, new StringBuilder());
        writeFile(booksFile, new StringBuilder());
        writeFile(ordersFile, new StringBuilder());
        writeFile(borrowingsFile, new StringBuilder());
    }
}
