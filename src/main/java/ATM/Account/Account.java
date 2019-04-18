package ATM.Account;

import ATM.Transaction.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A class representing a bank account.
 */
public abstract class Account implements Serializable {

    private final double overdraftLimit;
    private final Date creationDate;
    private final List<String> users;
    private final List<Transaction> transactions;
    private final String accountID;
    private double balance;

    /**
     * Initialize this Account with an initial user, an overdraft limit and a creation date.
     *
     * @param username       The username of the first owner of this Account.
     * @param overdraftLimit The maximum negative balance this Account is allowed to incur.
     * @param creationDate   The date on which this Account was created.
     */
    public Account(String username, Date creationDate, double overdraftLimit) {
        this.accountID = UUID.randomUUID().toString();
        this.overdraftLimit = overdraftLimit;
        this.creationDate = creationDate;
        this.transactions = new ArrayList<>();
        this.users = new ArrayList<>();
        addUser(username);
        setBalance(0);
    }

    /**
     * Get this Account's unique ID.
     *
     * @return a String representing this Account's ID.
     */
    public String getID() {
        return accountID;
    }

    /**
     * Increase this Account's balance by an amount.
     *
     * @param amount The amount to increase the balance.
     */
    private void increaseBalance(double amount) {
        setBalance(balance + amount);
    }

    /**
     * Decrease this Account's balance by an amount.
     *
     * @param amount The amount to decrease the balance.
     */
    private void decreaseBalance(double amount) {
        setBalance(balance - amount);
    }

    /**
     * Get this Account's balance.
     *
     * @return the balance.
     */
    double getBalance() {
        return balance;
    }

    /**
     * Set this Account's balance.
     *
     * @param balance The balance to set.
     */
    void setBalance(double balance) {
        this.balance = balance;
        roundBalance();
    }

    /**
     * Get this Account's creation date.
     *
     * @return the date of creation.
     */
    Date getCreationDate() {
        return creationDate;
    }

    /**
     * Add a new user to this Account's list of owners.
     *
     * @param username The username of the new user.
     */
    void addUser(String username) {
        if (!ownedBy(username)) {
            users.add(username);
        }
    }

    /**
     * Check if this account is owned by a given User.
     *
     * @param username The User's username.
     * @return true if this Account is owned by the User, false otherwise.
     */
    boolean ownedBy(String username) {
        return users.contains(username);
    }

    /**
     * Add a transaction to this Account's list of transactions.
     *
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(0, transaction);
    }

    /**
     * Check whether an amount can be safely withdrawn from this Account.
     *
     * @param amount The amount to withdraw.
     * @return true if the amount can be withdrawn safely, false otherwise.
     */
    public boolean canWithdraw(double amount) {
        return balance - amount >= overdraftLimit && amount > 0;
    }

    /**
     * Check whether an amount can be safely deposited into this Account.
     *
     * @param amount The amount to deposit.
     * @return true if the amount can be deposited safely, false otherwise.
     */
    public boolean canDeposit(double amount) {
        return amount > 0;
    }

    /**
     * Check whether an amount can be transferred from this Account to another Account.
     *
     * @param amount The amount to transfer.
     * @return true if the amount can be safely transferred out, false otherwise.
     */
    public boolean canTransferOut(double amount) {
        return canWithdraw(amount);
    }

    /**
     * Withdraw an amount from this account.
     *
     * @param amount The amount to withdraw.
     * @return true if the withdrawal is successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            decreaseBalance(amount);
            return true;
        }
        return false;
    }

    /**
     * Deposit an amount into this account.
     *
     * @param amount The amount to deposit.
     * @return true if the deposit is successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (canDeposit(amount)) {
            increaseBalance(amount);
            return true;
        }
        return false;
    }

    /**
     * Check if this Account is the primary Account, i.e., the default deposit destination.
     *
     * @return true if this Account is primary, false otherwise.
     */
    boolean isPrimary() {
        return false;
    }

    /**
     * Round this Account's balance to two decimal places.
     */
    private void roundBalance() {
        balance = Math.round(balance * 100.) / 100.;
    }

    /**
     * Return a String representing the balance of this account for display purposes.
     *
     * @return a String representing the balance of this account.
     */
    abstract String getBalanceString();

    /**
     * Perform any actions required to update this account at the end of the month.
     */
    void update() {}

    List<Transaction> getTransactionsList() {
        return transactions;
    }

    public char getCurrencySymbol() {
        return '$';
    }
}
