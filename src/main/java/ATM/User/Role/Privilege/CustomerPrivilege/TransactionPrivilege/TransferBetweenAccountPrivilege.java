package ATM.User.Role.Privilege.CustomerPrivilege.TransactionPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to transfer funds between two accounts.
 */
public class TransferBetweenAccountPrivilege extends Privilege {

    /**
     * Initialize this TransferBetweenAccountPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public TransferBetweenAccountPrivilege(String username, Bank bank) {
        super("Make a transfer", username, bank);
    }

    /**
     * Prompt the user to transfer funds between accounts.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String fromID = selectAccount(inputReader, "Select an account to transfer from");
        String toID = selectAccount(inputReader, "Select an account to transfer to");
        double amount = inputReader.getPositiveDouble("Please enter an amount to transfer: ", "Enter transfer amount");
        boolean success = accountManager.transfer(fromID, toID, amount, timeManager.getCurrentTime());

        if (success) {
            messageDisplay.showMessage("Successfully transferred $" + amount, "Successful transfer");
        } else {
            messageDisplay.showErrorMessage("Failed to transfer", "Transfer failed");
        }
    }
}
