package ATM.Transaction;

import ATM.Account.Account;
import ATM.CurrencyExchange;

import java.util.Date;

/**
 * A class representing a transfer from one user to another.
 */
public class TransferToUserTransaction extends TransferTransaction {

    private final String username;

    /**
     * Initialize this TransferToUserTransaction with a primary adn secondary
     * account, an amount, and a date.
     *
     * @param primary   The account from which money is to be transferred.
     * @param secondary The account to which money is to be transferred.
     * @param amount    The amount to transfer.
     * @param date      The date of the transfer.
     * @param username  The username to the user receiving the transfer.
     */
    public TransferToUserTransaction(Account primary, Account secondary, double amount, Date date, String username, CurrencyExchange currencyExchange) {
        super(primary, secondary, amount, date, currencyExchange);
        this.username = username;
    }

    @Override
    String transactionInfo() {
        String string = "Transfer of " + primary.getCurrencySymbol();
        return string + String.format("%.2f", amount) + " from: " + primary + " to: " + username;
    }
}
