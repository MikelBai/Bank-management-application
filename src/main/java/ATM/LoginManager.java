package ATM;

import ATM.User.User;
import ATM.User.UserManager;

/**
 * A class used to authenticate a username and password.
 */
public class LoginManager {

    private final UserManager userManager;

    /**
     * Initialize this LoginManager with a UserManager.
     *
     * @param userManager The UserManager containing the desired users.
     */
    public LoginManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Authenticate a username and password.
     *
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return true if the username and password are valid, false otherwise.
     */
    public boolean login(String username, String password) {
        User user = userManager.getUser(username);
        if (user != null) {
            return user.isValidPassword(password);
        }
        return false;
    }
}
