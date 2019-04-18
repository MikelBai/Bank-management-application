package ATM.User.Role.Privilege.CustomerPrivilege.ViewAccountPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to display a summary of all accounts.
 */
public class ViewAccountSummaryPrivilege extends Privilege {

    /**
     * Initialize this ViewAccountSummaryPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ViewAccountSummaryPrivilege(String username, Bank bank) {
        super("View account summary", username, bank);
    }

    /**
     * Displays a summary of this User's accounts in a String.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountsOwnedSummary = accountManager.getAccountsOwnedSummaryString(username);
        messageDisplay.showMessage(accountsOwnedSummary, "Account summary");
    }
}
