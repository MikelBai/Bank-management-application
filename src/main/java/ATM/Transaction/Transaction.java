package ATM.Transaction;

import ATM.Account.Account;

import java.io.Serializable;
import java.util.Date;

/**
 * A class representing a transaction, which performs and reverts money
 * transactions to, from, or between accounts.
 */
public abstract class Transaction implements Serializable {

    final Account primary;
    final double amount;
    private final Date date;
    private boolean revertible;
    private boolean executable;
    private boolean reverted;
    private boolean alreadyRequested;

    /**
     * Initialize this Transaction with a primary Account, an amount, and a date.
     *
     * @param primary The primary account associated with this Transaction.
     * @param amount  The amount of money associated with this Transaction.
     * @param date    The date on which this Transaction was created.
     */
    Transaction(Account primary, double amount, Date date) {
        this.primary = primary;
        this.amount = amount;
        this.date = date;
        setExecutable(true);
        setRevertible(false);
        setReverted(false);
        setAlreadyRequested(false);
    }

    /**
     * Returns whether or not this transaction has been requested to be reverted already
     *
     * @return boolean
     */
    public boolean isAlreadyRequested() {
        return alreadyRequested;
    }

    /**
     * Updates this request's status of its request history
     *
     * @param alreadyRequested whether or not this transaction has been requested
     */
    public void setAlreadyRequested(boolean alreadyRequested) {
        this.alreadyRequested = alreadyRequested;
    }

    /**
     * Get whether this Transaction is executable.
     *
     * @return true if this Transaction may be executed, false otherwise.
     */
    boolean isExecutable() {
        return executable;
    }

    /**
     * Set whether this Transaction is executable.
     *
     * @param executable Whether this Transaction is executable.
     */
    private void setExecutable(boolean executable) {
        this.executable = executable;
    }

    /**
     * Get whether this Transaction is revertible.
     *
     * @return True if this Transaction may be reverted, false otherwise.
     */
    boolean isRevertible() {
        return revertible;
    }

    /**
     * Set whether this Transaction is revertible.
     *
     * @param revertible Whether this Transaction is revertible.
     */
    private void setRevertible(boolean revertible) {
        this.revertible = revertible;
    }

    /**
     * Get whether this Transaction has been reverted.
     *
     * @return true if this Transaction was reverted, false otherwise.
     */
    private boolean isReverted() {
        return reverted;
    }

    /**
     * Set whether this Transaction has been reverted
     *
     * @param reverted Whether this Transaction has been reverted.
     */
    private void setReverted(boolean reverted) {
        this.reverted = reverted;
    }

    /**
     * Get the date of this Transaction.
     *
     * @return The date.
     */
    Date getDate() {
        return date;
    }

    /**
     * Return whether both this Transaction and its associated Account(s) can
     * safely execute the Transaction.
     *
     * @return true if the Transaction can safely proceed, false otherwise.
     */
    abstract boolean canExecute();

    /**
     * Return whether both this Transaction and its associated Account(s) can
     * safely revert the Transaction.
     *
     * @return true if the Transaction can be safely reverted, false otherwise.
     */
    public abstract boolean canRevert();

    /**
     * Perform the operations required to execute this Transaction.
     */
    abstract void performExecution();

    /**
     * Perform the operations required to revert this Transaction.
     */
    abstract void performReversion();

    /**
     * Add a record of this Transaction to its associated Account(s).
     */
    void recordTransaction() {
        primary.addTransaction(this);
    }

    /**
     * Check whether this Transaction can be executed, then execute it.
     *
     * @return true if the Transaction was executed successfully, false otherwise.
     */
    public boolean execute() {
        boolean canExecute = canExecute();
        if (canExecute) {
            performExecution();
            recordTransaction();
            setExecutable(false);
            setRevertible(true);
        }
        return canExecute;
    }

    /**
     * Check whether this Transaction can be reverted, then revert it.
     *
     * @return true if the Transaction was reverted successfully, false otherwise.
     */
    public boolean revert() {
        boolean canRevert = canRevert();
        if (canRevert) {
            performReversion();
            setRevertible(false);
            setReverted(true);
        }
        return canRevert;
    }

    /**
     * Get a String containing information about this Transaction. This is a
     * helper method used in the toString method.
     *
     * @return a String containing information about this Transaction.
     */
    abstract String transactionInfo();

    @Override
    public String toString() {
        if (isReverted()) {
            return "REVERTED: " + transactionInfo();
        }
        return transactionInfo();
    }
}
