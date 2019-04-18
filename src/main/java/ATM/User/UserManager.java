package ATM.User;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A class used for managing a collection of accounts.
 */
public class UserManager implements Serializable {

    private final HashMap<String, User> users;

    /**
     * Initialize this AccountManager with an empty collection of accounts.
     */
    public UserManager() {
        this.users = new HashMap<>();
    }

    /**
     * Add a new User to this UserManager.
     *
     * @param user The User to add.
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Get a particular user.
     *
     * @param username The username of the user.
     * @return The user.
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * See if a user is in this UserManager.
     *
     * @param username The username of the user.
     * @return true when this user is within.
     */
    public boolean containsUser(String username) {
        return users.containsKey(username);
    }

}
