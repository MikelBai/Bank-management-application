package ATM.Transaction;

import ATM.ATM;
import ATM.Account.Account;

import java.util.Date;

/**
 * A class representing a withdrawal transaction, which can remove money from an account.
 */
public class WithdrawalTransaction extends Transaction {

    private final ATM atm;

    /**
     * Initialize this WithdrawalTransaction with a primary account, an amount, and a date.
     *
     * @param primary The account from which to withdraw money.
     * @param amount  The amount want to withdraw.
     * @param date    The date of the withdrawal.
     * @param atm     The ATM assigned for this withdrawal.
     */
    public WithdrawalTransaction(Account primary, double amount, Date date, ATM atm) {
        super(primary, amount, date);
        this.atm = atm;
    }

    @Override
    boolean canExecute() {
        boolean accountCanWithdraw = primary.canWithdraw(amount);
        boolean atmCanWithdraw = atm.canWithdraw((int) amount);
        return accountCanWithdraw && atmCanWithdraw && isExecutable();
    }

    @Override
    public boolean canRevert() {
        boolean canDeposit = primary.canDeposit(amount);
        return canDeposit && isRevertible();
    }

    @Override
    void performExecution() {
        atm.withdraw((int) amount);
        primary.withdraw(amount);
    }

    @Override
    void performReversion() {
        primary.deposit(amount);
    }

    @Override
    String transactionInfo() {
        return "Withdrawal of $" + String.format("%.2f", amount) + " from: " + primary;
    }
}
