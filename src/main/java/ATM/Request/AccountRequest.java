package ATM.Request;

import ATM.Account.Account;
import ATM.Account.AccountFactory;

public class AccountRequest extends Request {

    private final String accountType;
    private final AccountFactory accountFactory;

    /**
     * Initializes a account request
     *
     * @param username       the user who requested a new account
     * @param accountType    the type of account desired
     * @param accountFactory the account factory
     */
    public AccountRequest(String username, String accountType, AccountFactory accountFactory) {
        super(username);
        this.accountType = accountType;
        this.accountFactory = accountFactory;
    }

    /**
     * Executes the request
     *
     * @return a boolean of whether the request was successfully executed
     */
    @Override
    boolean execute() {
        Account newAccount = accountFactory.getAccount(accountType, username);
        if (newAccount != null) {
            accountManager.addAccount(newAccount);
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the request
     *
     * @return string representation of the request
     */
    @Override
    public String toString() {
        return "Customer " + username + " requested a new " + accountType;
    }
}
