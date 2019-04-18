package ATM.User.Role.Privilege.CustomerPrivilege.TransactionPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to pay a bill.
 */
public class PayBillPrivilege extends Privilege {

    /**
     * Initialize this PayBillPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public PayBillPrivilege(String username, Bank bank) {
        super("Pay a bill", username, bank);
    }

    /**
     * Prompt the user to make a bill payment.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountID = selectAccount(inputReader, "Select an account to transfer from");
        String payee = inputReader.getString("Enter the payee name:", "Enter payee");
        double amount = inputReader.getPositiveDouble("Please enter an amount to transfer: ", "Amount");
        boolean success = accountManager.payBill(accountID, amount, timeManager.getCurrentTime(), payee);

        if (success) {
            messageDisplay.showMessage("Successfully transferred $" + amount + " to " + payee, "Successful transfer");
        } else {
            messageDisplay.showErrorMessage("Failed to transfer", "Transfer failed");
        }
    }
}
