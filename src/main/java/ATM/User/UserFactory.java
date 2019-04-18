package ATM.User;

import ATM.Bank;
import ATM.User.Role.RoleFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used to instantiate new users.
 */
public class UserFactory implements Serializable {

    private final Bank bank;
    private final RoleFactory roleFactory;

    /**
     * Initialize this UserFactory with a Bank and a RoleFactory.
     *
     * @param bank        The Bank which this User belongs to.
     * @param roleFactory A factory used to create each User's Roles.
     */
    public UserFactory(Bank bank, RoleFactory roleFactory) {
        this.bank = bank;
        this.roleFactory = roleFactory;
    }

    public static List<String> getAvailableUserTypes() {
        List<String> returnList = new ArrayList<>();
        returnList.add("Customer");
        returnList.add("Youth");
        returnList.add("Bank Employee");
        returnList.add("Bank Manager");
        returnList.add("ATM Technician");
        return returnList;
    }

    /**
     * Instantiate a new user.
     *
     * @param userType The user type.
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return The new user.
     */
    public User getUser(String userType, String username, String password) {
        User user = new User(username, password);
        switch (userType) {
            case "Customer":
                user.addRole(roleFactory.getRole("Transaction", username, bank));
                user.addRole(roleFactory.getRole("View Account", username, bank));
                user.addRole(roleFactory.getRole("Request", username, bank));
                user.addRole(roleFactory.getRole("Account Management", username, bank));
                break;
            case "Bank Employee":
                user.addRole(roleFactory.getRole("Employee", username, bank));
                user.addRole(roleFactory.getRole("ATM Maintenance", username, bank));
                break;
            case "Bank Manager":
                user.addRole(roleFactory.getRole("Employee", username, bank));
                user.addRole(roleFactory.getRole("ATM Maintenance", username, bank));
                user.addRole(roleFactory.getRole("Admin", username, bank));
                break;
            case "ATM Technician":
                user.addRole(roleFactory.getRole("ATM Maintenance", username, bank));
                user.addRole(roleFactory.getRole("Transaction", username, bank));
                user.addRole(roleFactory.getRole("View Account", username, bank));
                user.addRole(roleFactory.getRole("Request", username, bank));
                user.addRole(roleFactory.getRole("Account Management", username, bank));
                break;
            case "Youth":
                user.addRole(roleFactory.getRole("Transaction", username, bank));
                user.addRole(roleFactory.getRole("View Account", username, bank));
                break;
        }
        user.addRole(roleFactory.getRole("User", username, bank));
        return user;
    }
}
