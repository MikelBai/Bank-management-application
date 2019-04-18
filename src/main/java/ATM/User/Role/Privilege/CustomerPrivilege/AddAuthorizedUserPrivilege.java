package ATM.User.Role.Privilege.CustomerPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;
import ATM.User.Role.TransactionRole;

/**
 * A class used to add an additional user to a bank account.
 */
public class AddAuthorizedUserPrivilege extends Privilege {

    /**
     * Initialize this AddAuthorizedUserPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public AddAuthorizedUserPrivilege(String username, Bank bank) {
        super("Add an authorized user", username, bank);
    }

    /**
     * Prompt the user to add a new authorized user to an account.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountID = selectAccount(inputReader, "Select an account to add a user");
        String username = inputReader.getString("Enter the user you wish to add", "Enter username");

        if (userManager.containsUser(username) && userManager.getUser(username).containsRole(TransactionRole.name)) {
            accountManager.addUser(accountID, username);
            messageDisplay.showMessage("User added successfully", "Success");
        } else {
            messageDisplay.showErrorMessage("Invalid user", "Failed to add");
        }
    }
}
