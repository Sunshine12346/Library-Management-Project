package Library.Users;

import Library.Database;
import Library.Operations.IOOperation;

/**
 * Abstract User class representing a generic user in the library system.
 * Concrete subclasses will define specific types of users, such as admins and normal users.
 */
public abstract class User {

    protected String name;
    protected String email;
    protected String phonenumber;
    protected IOOperation[] operations; // Operations available to the user

    // Default constructor
    public User() {}

    // Constructor with name
    public User(String name) {
        this.name = name;
    }

    // Constructor with name, email, and phone number
    public User(String name, String email, String phonenumber) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    // Constructor with name, email, phone number, and operations
    public User(String name, String email, String phonenumber, IOOperation[] operations) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.operations = operations;
    }

    /**
     * Gets the user's name.
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's email.
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's phone number.
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phonenumber;
    }

    /**
     * Abstract method to convert the user to a string representation.
     * @return a string representation of the user.
     */
    public abstract String toString();

    /**
     * Abstract method to display and interact with the menu for the user.
     * This method should be implemented by each subclass according to specific user types.
     * @param database the library database.
     * @param user the current user.
     */
    public abstract void menu(Database database, User user);
}
