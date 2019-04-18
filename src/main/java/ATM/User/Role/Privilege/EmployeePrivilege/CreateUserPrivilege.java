package ATM.User.Role.Privilege.EmployeePrivilege;

import ATM.Account.Account;
import ATM.Account.AccountFactory;
import ATM.Account.ChequingAccount;
import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;
import ATM.User.Role.TransactionRole;
import ATM.User.User;
import ATM.User.UserFactory;

import java.util.List;

/**
 * A class used to create a new user.
 */
public class CreateUserPrivilege extends Privilege {

    private final UserFactory userFactory;
    private final AccountFactory accountFactory;

    /**
     * Initialize this CreateUserPrivilege with a username, a bank, and factories for user and account creation.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     * @param userFactory A factory used to create new users.
     * @param accountFactory A factory used to create default accounts for new users.
     */
    public CreateUserPrivilege(String username, Bank bank, UserFactory userFactory, AccountFactory accountFactory) {
        super("Create a new user", username, bank);
        this.userFactory = userFactory;
        this.accountFactory = accountFactory;
    }

    /**
     * Prompt the creation of a new user.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<String> userTypes = UserFactory.getAvailableUserTypes();
        int userTypeIndex = inputReader.getSelectionFromOptions(userTypes, "Select a user type");
        String userType = userTypes.get(userTypeIndex);
        String username = inputReader.getString("New customer username:", "Enter username");
        String password = inputReader.getString("New customer password:", "Enter password");

        if (userManager.containsUser(username)) {
            messageDisplay.showErrorMessage("User " + username + " already exists", "Failed to create user");
        } else {
            User user = userFactory.getUser(userType, username, password);
            userManager.addUser(user);
            messageDisplay.showMessage("Successfully created user " + username, "User created successfully");

            // Add a default chequing account
            if (user.containsRole(TransactionRole.name)) {
                Account defaultAccount = accountFactory.getAccount("Chequing Account", username);
                ((ChequingAccount) defaultAccount).setPrimary(true);
                accountManager.addAccount(defaultAccount);
            }
        }
    }
}
