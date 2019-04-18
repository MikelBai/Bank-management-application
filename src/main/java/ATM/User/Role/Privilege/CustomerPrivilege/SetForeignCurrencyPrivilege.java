package ATM.User.Role.Privilege.CustomerPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to set the currency of a User's ForeignCurrencyAccount.
 */
public class SetForeignCurrencyPrivilege extends Privilege {

    /**
     * Initialize this SetForeignCurrencyPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public SetForeignCurrencyPrivilege(String username, Bank bank) {
        super("Set foreign currency", username, bank);
    }

    /**
     * Prompt the user to set the currency of a ForeignCurrencyAccount.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        if (accountManager.getListOfForeignCurrencyAccounts(username).size() == 0) {
            messageDisplay.showErrorMessage("You currently do not have a Foreign Currency account", "No account of this type");
        } else {
            int accountIndex = inputReader.getSelectionFromOptions(accountManager.getListOfForeignCurrencyAccounts(username), "Select Account");
            String accountID = accountManager.getListOfForeignCurrencyAccounts(username).get(accountIndex).getID();
            int currencyIndex = inputReader.getSelectionFromOptions(accountManager.getAccountsCurrencyExchange().getSupportedCurrencies(), "Select Currency");
            String currency = accountManager.getAccountsCurrencyExchange().getSupportedCurrencies().get(currencyIndex);
            boolean success = accountManager.setCurrencyType(accountID, currency);
            if (success) {
                messageDisplay.showMessage("Successfully changed account currency type", "Successfully completed");
            } else {
                messageDisplay.showErrorMessage("Failed to set currency\nCurrency type has been previously set", "Completed");
            }
        }
    }
}
