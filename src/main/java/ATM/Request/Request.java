package ATM.Request;

import ATM.Account.AccountManager;
import ATM.FinanceProduct.ProductManager;

import java.io.Serializable;

public abstract class Request implements Serializable {

    final String username;
    AccountManager accountManager;
    ProductManager productManager;

    /**
     * creates a request by a requested
     *
     * @param username the creator of the request
     */
    Request(String username) {
        this.username = username;
    }

    /**
     * get the username of the requester
     *
     * @return a username
     */
    public String getUsername() {
        return username;
    }

    /**
     * the account manager of which this request is associated with
     *
     * @param accountManager an account manager
     */
    void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    /**
     * execute this request
     *
     * @return the success or failure of the request
     */
    abstract boolean execute();
}
