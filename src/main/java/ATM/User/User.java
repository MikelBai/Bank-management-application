package ATM.User;

import ATM.User.Role.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a bank user, which can log in and interact with the bank.
 */
public class User implements Serializable {

    private final String username;
    private String password;
    private final List<Role> roles;

    /**
     * Initialize this User with a username, password, and bank.
     *
     * @param username The username of this user.
     * @param password The password of this user.
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Checks if the password argument matches this user's password.
     *
     * @param password The password input.
     * @return a boolean displaying whether or not the input password equals this user's password.
     */
    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Set a new password for this User.
     *
     * @param newPassword the new password.
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    /**
     * Get this User's username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Check whether this User contains a certain Role.
     *
     * @param name The name of the Role.
     * @return true if this User contains the given Role, false otherwise.
     */
    public boolean containsRole(String name) {
        for (Role role : roles) {
            if (role.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
