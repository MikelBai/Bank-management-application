package ATM.User.Role.Privilege;

import ATM.ATM;
import ATM.Account.AccountManager;
import ATM.Bank;
import ATM.BankTimeManager;
import ATM.FinanceProduct.ProductManager;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.RequestManager;
import ATM.Session.OptionsTree;
import ATM.User.UserManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing an individual privilege or action that a user can perform.
 */
public abstract class Privilege implements Serializable {

    private final String name;
    protected final String username;
    protected final ATM atm;
    protected final AccountManager accountManager;
    protected final ProductManager productManager;
    protected final UserManager userManager;
    protected final RequestManager requestManager;
    protected final BankTimeManager timeManager;

    /**
     * Initialize this privilege with a name, a username, a bank, and GUI input and output objects.
     *
     * @param name     The name of this Privilege.
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    protected Privilege(String name, String username, Bank bank) {
        this.name = name;
        this.username = username;
        this.atm = bank.getATM();
        this.accountManager = bank.getAccountManager();
        this.productManager = bank.getProductManager();
        this.userManager = bank.getUserManager();
        this.requestManager = bank.getRequestManager();
        this.timeManager = bank.getBankTimeManager();
    }

    /**
     * Perform this privilege.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    protected abstract void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay);

    /**
     * Return an OptionsTree used to perform this privilege.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    public OptionsTree getOptionsTree(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        return new OptionsTree(name, () -> perform(inputReader, messageDisplay));
    }

    /**
     * Prompt the user to select one of their accounts.
     *
     * @param title The title bar text of the user prompt.
     * @return the ID of the account selected by the user.
     */
    protected String selectAccount(GraphicalInputReader inputReader, String title) {
        List<String> options = new ArrayList<>();
        List<String> accountIDs = accountManager.getAccountIDs(username);
        for (String id : accountIDs) {
            options.add(accountManager.getAccountString(id));
        }
        int index = inputReader.getSelectionFromOptions(options, title);
        return accountIDs.get(index);
    }
}
