package ATM.User.Role.Privilege.CustomerPrivilege.ViewAccountPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to display a summary of a specific account.
 */
public class ViewAccountInformationPrivilege extends Privilege {

    /**
     * Initialize this ViewAccountInformationPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ViewAccountInformationPrivilege(String username, Bank bank) {
        super("View account information", username, bank);
    }

    /**
     * Prompt the user to display information for a specific account.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountID = selectAccount(inputReader, "Select an account to view");
        String accountSummary = accountManager.getAccountSummaryString(accountID);
        messageDisplay.showMessage(accountSummary, "Account summary");
    }
}
