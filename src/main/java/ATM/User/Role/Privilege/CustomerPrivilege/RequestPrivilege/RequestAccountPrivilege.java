package ATM.User.Role.Privilege.CustomerPrivilege.RequestPrivilege;

import ATM.Account.AccountFactory;
import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.AccountRequest;
import ATM.User.Role.Privilege.Privilege;

import java.util.List;

/**
 * A class used to request a new account.
 */
public class RequestAccountPrivilege extends Privilege {

    private final AccountFactory accountFactory;

    /**
     * Initialize this RequestAccountPrivilege with a username, a bank, and an account factory.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     * @param accountFactory A factory used to generate the new accounts.
     */
    public RequestAccountPrivilege(String username, Bank bank, AccountFactory accountFactory) {
        super("Request a new account", username, bank);
        this.accountFactory = accountFactory;
    }

    /**
     * Prompt the user to request the creation of a new account.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<String> accountTypes = AccountFactory.getAvailableAccountTypes();
        int accountTypeIndex = inputReader.getSelectionFromOptions(accountTypes, "Select an account type");
        String accountType = accountTypes.get(accountTypeIndex);
        requestManager.addRequest(new AccountRequest(username, accountType, accountFactory));
        messageDisplay.showMessage("Requested a new " + accountType, "Successful request");
    }
}
