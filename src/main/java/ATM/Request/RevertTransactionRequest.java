package ATM.Request;

import ATM.Transaction.Transaction;

public class RevertTransactionRequest extends Request {

    private final Transaction transaction;

    /**
     * creates a request to revert a transaction
     *
     * @param username    the username of the requester
     * @param transaction the transaction being requested
     */
    public RevertTransactionRequest(String username, Transaction transaction) {
        super(username);
        this.transaction = transaction;
    }

    /**
     * Returns whether or not the transaction has been requested before
     *
     * @return whether or not the transaction has been requested before
     */
    public boolean isRequested() {
        return transaction.isAlreadyRequested();
    }

    /**
     * set the transaction to be a requested one
     */
    public void setTransactionAsRequested() {
        transaction.setAlreadyRequested(true);
    }

    /**
     * check if this type of transaction is revertible
     *
     * @return a boolean of whether it is revertible
     */
    public boolean isValid() {
        return transaction.canRevert();
    }

    @Override
    public boolean execute() {
        return transaction.revert();
    }

    @Override
    public String toString() {
        return "Customer " + username + " requested to revert the transaction " + transaction;
    }
}
