package ATM.Account;

import java.util.Date;

/**
 * A class representing a credit card account.
 */
public class CreditCardAccount extends DebtAccount {

    /**
     * Initialize this CreditCardAccount with an initial user and a creation date.
     *
     * @param username     The username of the first owner of this CreditCardAccount.
     * @param creationDate The date on which this CreditCardAccount was created.
     */
    public CreditCardAccount(String username, Date creationDate) {
        super(username, creationDate);
    }

    @Override
    public boolean canTransferOut(double amount) {
        return false;
    }

    @Override
    public String toString() {
        return "Credit Card Account | Balance: $" + getBalanceString();
    }
}
