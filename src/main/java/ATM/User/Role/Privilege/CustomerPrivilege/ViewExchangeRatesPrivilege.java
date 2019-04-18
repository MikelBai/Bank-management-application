package ATM.User.Role.Privilege.CustomerPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

import java.util.List;

/**
 * A class used to view the exchange rates between two currencies
 */

public class ViewExchangeRatesPrivilege extends Privilege {

    /**
     * Initialize this SetForeignCurrencyPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ViewExchangeRatesPrivilege(String username, Bank bank) {
        super("View exchange rates", username, bank);
    }

    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<String> currencyOptions = accountManager.getAccountsCurrencyExchange().getSupportedCurrencies();
        int fromCurrencyIndex = inputReader.getSelectionFromOptions(currencyOptions, "From currency");
        int toCurrencyIndex = inputReader.getSelectionFromOptions(currencyOptions, "To currency");
        String fromCurrency = currencyOptions.get(fromCurrencyIndex);
        String toCurrency = currencyOptions.get(toCurrencyIndex);
        messageDisplay.showMessage(accountManager.getAccountsCurrencyExchange().getResultsString(fromCurrency, toCurrency), "Result");
    }
}
