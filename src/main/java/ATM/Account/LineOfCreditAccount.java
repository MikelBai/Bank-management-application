package ATM.Account;

import java.util.Date;

/**
 * A class representing a LineOfCreditAccount
 */
class LineOfCreditAccount extends DebtAccount {

    /**
     * Initialize this LineOfCreditAccount with an initial user and a creation date.
     *
     * @param username     The username of the first owner of this LineOfCreditAccount.
     * @param creationDate The date on which this LineOfCreditAccount was created.
     */
    public LineOfCreditAccount(String username, Date creationDate) {
        super(username, creationDate);
    }

    @Override
    public String toString() {
        return "Line of Credit Account | Balance: $" + getBalanceString();
    }
}
