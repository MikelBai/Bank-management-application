package ATM.Account;

import ATM.ATM;
import ATM.CurrencyExchange;
import ATM.FinanceProduct.FinanceProduct;
import ATM.Transaction.*;

import java.io.Serializable;
import java.util.*;

/**
 * A class used for managing a collection of accounts.
 */
public class AccountManager implements Serializable {

    private final HashMap<String, Account> accounts;
    private final CurrencyExchange accountsCurrencyExchange;

    /**
     * Initialize this AccountManager with an empty collection of accounts.
     */
    public AccountManager() {
        this.accounts = new HashMap<>();
        accountsCurrencyExchange = new CurrencyExchange();
    }

    /**
     * Add a new Account to this AccountManager.
     *
     * @param account The Account to add.
     */
    public void addAccount(Account account) {
        accounts.put(account.getID(), account);
    }

    /**
     * Get a list of the accountIDs owned by a given user.
     *
     * @param username The username of the user.
     * @return a list of accountIDs associated with the user's owned accounts.
     */
    public List<String> getAccountIDs(String username) {
        List<String> accountIDs = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.ownedBy(username)) {
                accountIDs.add(account.getID());
            }
        }
        return accountIDs;
    }

    /**
     * Get the ID of the primary account for a given user.
     *
     * @param username The username of the user.
     * @return a String representing the ID of the user's primary account.
     * @throws NoSuchElementException if the user has no primary account in this AccountManager.
     */
    String getPrimaryAccountID(String username) throws NoSuchElementException {
        List<String> accountIDs = getAccountIDs(username);
        for (String accountID : accountIDs) {
            if (accounts.get(accountID).isPrimary()) {
                return accountID;
            }
        }
        throw new NoSuchElementException("primary account or user not found");
    }

    /**
     * Associate a product with its owners Primary account.
     *
     * @param product the product to be associated.
     */
    public void associateProductAccount(FinanceProduct product){
        String PrimaryID = getPrimaryAccountID(product.getUsername());
        Account Primary = accounts.get(PrimaryID);
        product.setAssociated(Primary);
    }

    /**
     * Pay a bill from an Account contained in this AccountManager.
     *
     * @param fromID The ID of the account from which to transfer.
     * @param amount The amount of the bill payment.
     * @param date   The date of the transaction.
     * @param payee  The name of the payee.
     * @return true if the payment was successful, false otherwise.
     */
    public boolean payBill(String fromID, double amount, Date date, String payee) {
        if (accounts.containsKey(fromID)) {
            Account fromAccount = accounts.get(fromID);
            Transaction t = new BillPaymentTransaction(fromAccount, amount, date, payee);
            return t.execute();
        }
        return false;
    }

    /**
     * Deposit an amount into an Account contained in this AccountManager.
     *
     * @param toID   The ID of the account receiving the deposit.
     * @param amount The amount to deposit.
     * @param date   The date of the deposit.
     * @return true if the deposit was successful, false otherwise.
     */
    public boolean deposit(String toID, double amount, Date date) {
        if (accounts.containsKey(toID)) {
            Account toAccount = accounts.get(toID);
            if (toAccount instanceof ForeignCurrencyAccount) {
                return false;
            } else {
                Transaction t = new DepositTransaction(toAccount, amount, date);
                return t.execute();
            }
        }
        return false;
    }

    /**
     * Withdraw an amount from an Account contained in this AccountManager.
     *
     * @param fromID The ID of the account from which to withdraw.
     * @param amount The amount to withdraw.
     * @param date   The date of the withdrawal.
     * @param atm    The ATM from which to withdraw.
     * @return true if the withdrawal was successful, false otherwise.
     */
    public boolean withdraw(String fromID, int amount, Date date, ATM atm) {
        if (accounts.containsKey(fromID)) {
            Account fromAccount = accounts.get(fromID);
            if (fromAccount instanceof ForeignCurrencyAccount) {
                return false;
            } else {
                Transaction t = new WithdrawalTransaction(fromAccount, amount, date, atm);
                return t.execute();
            }
        }
        return false;
    }

    /**
     * Transfer an amount between two accounts contained in this AccountManager.
     *
     * @param fromID The ID of the account from which to transfer.
     * @param toID   The ID of the account receiving the transfer.
     * @param amount The amount to transfer.
     * @param date   The date of the transfer.
     * @return true if the transfer was successful, false otherwise.
     */
    public boolean transfer(String fromID, String toID, double amount, Date date) {
        boolean containsAccounts = accounts.containsKey(fromID) && accounts.containsKey(toID);
        if (containsAccounts) {
            Account fromAccount = accounts.get(fromID);
            Account toAccount = accounts.get(toID);
            Transaction t = new TransferTransaction(fromAccount, toAccount, amount, date, accountsCurrencyExchange);
            return t.execute();
        }
        return false;
    }

    /**
     * Transfer from an account contained in this AccountManager to the primary
     * chequing account of another user.
     *
     * @param fromID The ID of the account from which to transfer.
     * @param toUser The username of the user receiving the transfer.
     * @param amount The amount to transfer.
     * @param date   The date of the transfer.
     * @return true if the transfer was successful, false otherwise.
     */
    public boolean transferToUser(String fromID, String toUser, double amount, Date date) {
        Account toAccount;
        try {
            String toID = getPrimaryAccountID(toUser);
            toAccount = accounts.get(toID);
        } catch (NoSuchElementException e) {
            return false;
        }
        if (accounts.containsKey(fromID)) {
            Account fromAccount = accounts.get(fromID);
            Transaction t = new TransferToUserTransaction(fromAccount, toAccount, amount, date, toUser, accountsCurrencyExchange);
            return t.execute();
        }
        return false;
    }

    /**
     * Return the toString for the Account with a given ID.
     *
     * @param accountID The ID of an Account.
     * @return the toString of the Account with the given ID.
     */
    public String getAccountString(String accountID) {
        return accounts.get(accountID).toString();
    }

    /**
     * Return all the information of an account as a string
     *
     * @param accountID The ID of an Account.
     * @return the information of the Account with the given ID
     */
    public String getAccountSummaryString(String accountID) {
        StringBuilder accountSummary = new StringBuilder();
        accountSummary.append(getAccountString(accountID));
        accountSummary.append("\n" + "Date created ");
        accountSummary.append(accounts.get(accountID).getCreationDate());
        accountSummary.append("\n" + "Most recent transactions:");
        for (Transaction t : getRecentAccountTransactions(accountID, 5)) {
            accountSummary.append("\n");
            accountSummary.append(t);
        }
        return accountSummary.toString();
    }

    /**
     * Return the summary of all the accounts that a user owns
     *
     * @param username the username of the user
     * @return the information of all of the user's accounts
     */
    public String getAccountsOwnedSummaryString(String username) {
        StringBuilder accountsOwnedSummary = new StringBuilder();
        accountsOwnedSummary.append("Total balance: $");
        accountsOwnedSummary.append(String.format("%.2f", getTotalBalance(username)));
        accountsOwnedSummary.append("\n\nAccounts:");
        for (String accountID : getAccountIDs(username)) {
            accountsOwnedSummary.append("\n");
            accountsOwnedSummary.append(getAccountString(accountID));
        }
        return accountsOwnedSummary.toString();
    }

    /**
     * Call the update method on all Accounts in this AccountManager.
     */
    public void updateAll() {
        for (Account account : accounts.values()) {
            account.update();
        }
    }

    /**
     * Get a Transaction from an Account in this AccountManager at a particular index.
     *
     * @param accountID      The ID of the account to retrieve the Transaction.
     * @param transactionNum The index of the Transaction.
     * @return the Transaction.
     */
    public Transaction getTransactionByIndex(String accountID, int transactionNum) {
        Account account = accounts.get(accountID);
        List<Transaction> transactions = account.getTransactionsList();
        return transactions.get(transactionNum);
    }

    /**
     * Get the n number of recent transactions
     *
     * @param accountID                the account's ID
     * @param numTransactionsToDisplay the amount of transactions to display
     * @return list of transactions
     */
    public List<Transaction> getRecentAccountTransactions(String accountID, int numTransactionsToDisplay) {
        Account account = accounts.get(accountID);
        List<Transaction> transactions = account.getTransactionsList();
        if (numTransactionsToDisplay >= transactions.size()) {
            return transactions;
        }
        return transactions.subList(0, numTransactionsToDisplay);
    }

    /**
     * Get the sum of the balances of a given User's accounts.
     *
     * @param username The username of the User.
     * @return the sum of the User's account balances.
     */
    private double getTotalBalance(String username) {
        double sum = 0;
        for (String accountID : getAccountIDs(username)) {
            sum += accounts.get(accountID).getBalance();
        }
        return sum;
    }

    /**
     * Sets the currency type of a Foreign Currency Account, also converts the original balance to the new type
     *
     * @param accountID    ID of the account
     * @param currencyCode the new type of currency
     * @return the success or fail of the operation
     */
    public boolean setCurrencyType(String accountID, String currencyCode) {
        Account account = accounts.get(accountID);
        if (account instanceof ForeignCurrencyAccount) {
            String originalCurrency = ((ForeignCurrencyAccount) account).getCurrencyCode();
            double originalBalance = account.getBalance();
            account.setBalance(accountsCurrencyExchange.getActualConvertedAmount(originalBalance, originalCurrency, currencyCode));
            return ((ForeignCurrencyAccount) account).setCurrencyCode(currencyCode);
        }
        return false;
    }

    public List<ForeignCurrencyAccount> getListOfForeignCurrencyAccounts(String username) {
        List<String> accountID = getAccountIDs(username);
        List<ForeignCurrencyAccount> foreignCurrencyAccounts = new ArrayList<>();
        for (String id : accountID) {
            if (accounts.get(id) instanceof ForeignCurrencyAccount) {
                ForeignCurrencyAccount e = (ForeignCurrencyAccount) accounts.get(id);
                foreignCurrencyAccounts.add(e);
            }
        }
        return foreignCurrencyAccounts;
    }

    public CurrencyExchange getAccountsCurrencyExchange() {
        return accountsCurrencyExchange;
    }

    public void addUser(String accountID, String username) {
        accounts.get(accountID).addUser(username);
    }
}
