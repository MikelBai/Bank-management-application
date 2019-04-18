package ATM.Transaction;

import ATM.Account.Account;
import ATM.Account.ForeignCurrencyAccount;
import ATM.CurrencyExchange;

import java.util.Date;

/**
 * A class representing a transfer transaction, which moves money between two accounts.
 */
public class TransferTransaction extends Transaction {

    private final Account secondary;
    private final CurrencyExchange currencyExchange;

    /**
     * Initialize this TransferTransaction with a primary adn secondary account, an amount, and a date.
     *
     * @param primary   The account from which money is to be transferred.
     * @param secondary The account to which money is to be transferred.
     * @param amount    The amount to transfer.
     * @param date      The date of the transfer.
     */
    public TransferTransaction(Account primary, Account secondary, double amount, Date date, CurrencyExchange currencyExchange) {
        super(primary, amount, date);
        this.secondary = secondary;
        this.currencyExchange = currencyExchange;
    }

    @Override
    boolean canExecute() {
        boolean canTransferOut = primary.canTransferOut(amount);
        boolean canDeposit = secondary.canDeposit(amount);
        if (primary instanceof ForeignCurrencyAccount) {
            canDeposit = secondary.canDeposit(convertedAmountOriginalTypeIsForeign());
        } else if (secondary instanceof ForeignCurrencyAccount) {
            canDeposit = secondary.canDeposit(convertedAmountSecondaryTypeIsForeign());
        }
        return canTransferOut && canDeposit && isExecutable();
    }

    /**
     * Checks if the transaction is revertible, takes into account of exchange rates
     * @return a boolean of whether is can be reverted
     */
    @Override
    public boolean canRevert() {
        boolean canDeposit = primary.canDeposit(amount);
        boolean canWithdraw = secondary.canWithdraw(amount);
        if (primary instanceof ForeignCurrencyAccount) {
            canWithdraw = secondary.canWithdraw(convertedAmountOriginalTypeIsForeign());
        } else if (secondary instanceof ForeignCurrencyAccount) {
            canWithdraw = secondary.canWithdraw(convertedAmountSecondaryTypeIsForeign());
        }
        return canDeposit && canWithdraw && isRevertible();
    }

    /**
     * Executes the transaction, takes into account the exchange rates between currency types
     * and will convert "amount" to the proper quantity and currency
     */
    @Override
    void performExecution() {
        primary.withdraw(amount);
        if (primary instanceof ForeignCurrencyAccount) {
            secondary.deposit(convertedAmountOriginalTypeIsForeign());
        } else if (secondary instanceof ForeignCurrencyAccount) {
            secondary.deposit(convertedAmountSecondaryTypeIsForeign());
        } else {
            secondary.deposit(amount);
        }
    }

    /**
     * Reverts the transaction, takes into account the exchange rates between currency types
     * and will convert "amount" to the proper quantity and currency
     */
    @Override
    void performReversion() {
        if (primary instanceof ForeignCurrencyAccount) {
            secondary.withdraw(convertedAmountOriginalTypeIsForeign());
        } else if (secondary instanceof ForeignCurrencyAccount) {
            secondary.withdraw(convertedAmountSecondaryTypeIsForeign());
        } else {
            secondary.withdraw(amount);
        }
        primary.deposit(amount);
    }

    /**
     * If the primary account is a foreign currency account, convert amount from
     * currency type 1 to currency type 2
     * @return the converted amount
     */
    private double convertedAmountOriginalTypeIsForeign() {
        String origCurrency = ((ForeignCurrencyAccount) primary).getCurrencyCode();
        String toCurrency = "CAD";
        if (secondary instanceof ForeignCurrencyAccount) {
            toCurrency = ((ForeignCurrencyAccount) secondary).getCurrencyCode();
        }
        return currencyExchange.getActualConvertedAmount(amount, origCurrency, toCurrency);
    }

    /**
     * If only the secondary account is a foreign currency account, convert from CAD to
     * currency type 2
     * @return the converted amount
     */
    private double convertedAmountSecondaryTypeIsForeign() {
        String origCurrency = "CAD";
        String toCurrency = ((ForeignCurrencyAccount) secondary).getCurrencyCode();
        return currencyExchange.getActualConvertedAmount(amount, origCurrency, toCurrency);
    }

    @Override
    void recordTransaction() {
        super.recordTransaction();
        secondary.addTransaction(this);
    }

    @Override
    String transactionInfo() {
        String string = "Transfer of " + primary.getCurrencySymbol();
        return string + String.format("%.2f", amount) + " from: " + primary + " to: " + secondary;
    }
}