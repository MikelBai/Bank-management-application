package ATM.Account;

import java.util.Date;

/**
 * A class representing a debt account.
 */
abstract class DebtAccount extends Account {

    /**
     * Initialize this DebtAccount with an initial user and a creation date.
     *
     * @param username     The username of the first owner of this DebtAccount.
     * @param creationDate The date on which this DebtAccount was created.
     */
    DebtAccount(String username, Date creationDate) {
        super(username, creationDate, -100000);
    }

    @Override
    String getBalanceString() {
        if (getBalance() == 0.0) {
            return String.format("%.2f", getBalance());
        }
        return String.format("%.2f", -getBalance());
    }
}
