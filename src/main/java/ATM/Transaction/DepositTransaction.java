package ATM.Transaction;

import ATM.Account.Account;

import java.util.Date;

/**
 * A class representing a deposit transaction, which can add money to an account.
 */
public class DepositTransaction extends Transaction {

    /**
     * Initialize this DepositTransaction with a primary account, an amount, and a date.
     *
     * @param primary The account into which money will be deposited.
     * @param amount  The amount to deposit.
     * @param date    The date of the deposit.
     */
    public DepositTransaction(Account primary, double amount, Date date) {
        super(primary, amount, date);
    }

    @Override
    boolean canExecute() {
        boolean canDeposit = primary.canDeposit(amount);
        return canDeposit && isExecutable();
    }

    @Override
    public boolean canRevert() {
        boolean canWithdraw = primary.canWithdraw(amount);
        return canWithdraw && isRevertible();
    }

    @Override
    void performExecution() {
        primary.deposit(amount);
    }

    @Override
    void performReversion() {
        primary.withdraw(amount);
    }

    @Override
    String transactionInfo() {
        return "Deposit of $" + String.format("%.2f", amount) + " to: " + primary;
    }
}
