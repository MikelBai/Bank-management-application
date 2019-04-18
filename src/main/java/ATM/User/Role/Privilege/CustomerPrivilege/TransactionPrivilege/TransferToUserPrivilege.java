package ATM.User.Role.Privilege.CustomerPrivilege.TransactionPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

public class TransferToUserPrivilege extends Privilege {

    /**
     * Initialize this TransferToUserPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public TransferToUserPrivilege(String username, Bank bank) {
        super("Transfer to another user", username, bank);
    }

    /**
     * Prompt the user to transfer funds to another customer.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountID = selectAccount(inputReader, "Select an account to transfer from");
        String otherUser = inputReader.getString("Enter the name of the user you wish to transfer to:", "Send to");
        double amount = inputReader.getPositiveDouble("Please enter an amount to transfer: ", "Enter transfer amount");
        boolean success = accountManager.transferToUser(accountID, otherUser, amount, timeManager.getCurrentTime());

        if (success) {
            messageDisplay.showMessage("Successfully transferred $" + amount + " to " + otherUser, "Successful transfer");
        } else {
            messageDisplay.showErrorMessage("Failed to transfer", "Transfer failed");
        }
    }
}
