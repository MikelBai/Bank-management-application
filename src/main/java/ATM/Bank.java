package ATM;

import ATM.Account.AccountManager;
import ATM.FinanceProduct.ProductManager;
import ATM.Request.RequestManager;
import ATM.User.UserManager;

import java.io.Serializable;

/**
 * Represents a Bank, including its customers, employees, and ATM.
 */
public class Bank implements Serializable {

    private static final long serialVersionUID = 4L;

    private final ATM atm;
    private final AccountManager accountManager;
    private final ProductManager productManager;
    private final UserManager userManager;
    private final RequestManager requestManager;
    private final BankTimeManager bankTimeManager;

    /**
     * Initialize this Bank with an ATM.
     *
     * @param atm this Bank's ATM.
     */
    public Bank(ATM atm, AccountManager accountManager, ProductManager productManager, UserManager userManager,
                RequestManager requestManager, BankTimeManager bankTimeManager) {
        this.atm = atm;
        this.accountManager = accountManager;
        this.productManager = productManager;
        this.userManager = userManager;
        this.requestManager = requestManager;
        this.bankTimeManager = bankTimeManager;
    }

    /**
     * Get this Bank's ATM.
     *
     * @return this Bank's ATM.
     */
    public ATM getATM() {
        return atm;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public BankTimeManager getBankTimeManager() {
        return bankTimeManager;
    }
}
