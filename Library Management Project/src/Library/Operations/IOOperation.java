package Library.Operations;

import Library.Database;
import Library.Users.User;

// Interface representing an input/output operation for the library system
public interface IOOperation {

    /**
     * Performs an operation involving the specified database and user.
     *
     * @param database the database instance to operate on
     * @param user     the user performing the operation
     */
    public void oper(Database database, User user);
}
