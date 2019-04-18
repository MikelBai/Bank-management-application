package ATM.User.Role.Privilege.CustomerPrivilege.TransactionPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to make a cash withdrawal from an account.
 */
public class WithdrawPrivilege extends Privilege {

    /**
     * Initialize this WithdrawPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public WithdrawPrivilege(String username, Bank bank) {
        super("Make a withdrawal", username, bank);
    }

    /**
     * Prompt the user to make a cash withdrawal from the ATM.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountID = selectAccount(inputReader, "Select an account to withdraw from");
        double amount = inputReader.getPositiveDouble("Please enter an amount \n (input will be rounded to a multiple of 5):", "Enter withdrawal amount");
        amount = 5 * (Math.floor(Math.abs(amount / 5)));
        boolean success = accountManager.withdraw(accountID, (int) amount, timeManager.getCurrentTime(), atm);

        if (success) {
            messageDisplay.showMessage("Successfully withdrew $" + amount, "Successful withdrawal");
        } else {
            messageDisplay.showErrorMessage("Failed to withdraw.\nBank employees have been notified if the ATM needs restocking.", "Withdrawal failed");
        }
    }
}
