package ATM.User.Role.Privilege.CustomerPrivilege.ViewAccountPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to display information about owned financial products.
 */
public class ViewProductInfoPrivilege extends Privilege {

    /**
     * Initialize this ViewProductInfoPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ViewProductInfoPrivilege(String username, Bank bank) {
        super("View financial products information", username, bank);
    }

    /**
     * Displays a summary of this User's financial products in a String.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String productInfo = productManager.getOwnerProductInfo(username);
        messageDisplay.showMessage(productInfo, "All product info");
    }
}
