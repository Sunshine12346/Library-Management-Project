package Library.Borrowings;

import Library.Books.Book;
import Library.Users.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Represents a borrowing transaction of a book by a user with details on borrowing period.
 */
public class Borrowing {

    private LocalDate start;
    private LocalDate finish;
    private Book book;
    private User user;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    // Constructor with 14-day default borrowing period
    public Borrowing(Book book, User user) {
        this.start = LocalDate.now();
        this.finish = start.plusDays(14);
        this.book = book;
        this.user = user;
    }

    // Constructor with specified start and end dates
    public Borrowing(LocalDate start, LocalDate finish, Book book, User user) {
        this.start = start;
        this.finish = finish;
        this.book = book;
        this.user = user;
    }

    public String getStart() {
        return FORMATTER.format(start);
    }

    public String getFinish() {
        return FORMATTER.format(finish);
    }

    public int getDaysLeft() {
        return Period.between(LocalDate.now(), finish).getDays();
    }

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

    @Override
    public String toString() {
        return "Borrowing Start Date: " + getStart() + "\n" +
                "Expiry Date: " + getFinish() + "\n" +
                "Days Left: " + getDaysLeft();
    }

    public String toFileString() {
        return getStart() + "<N/>" +
                getFinish() + "<N/>" +
                getDaysLeft() + "<N/>" +
                book.getName() + "<N/>" +
                user.getName();
    }
}
