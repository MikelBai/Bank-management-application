package ATM.Account;

import java.util.Date;

/**
 * A class representing Cashable GIC program.
 */
class CashableGIC extends AssetAccount {
    private final int investment;
    private final double interestRate;

    /**
     * Initialize a cashable GIC with a username, a creation date.
     *
     * @param username     The username of the owner of this GIC program
     * @param creationDate The date that this program created.
     */
    CashableGIC(String username, Date creationDate) {
        super(username, creationDate, 5000);
        this.investment = 5000;
        this.interestRate = 0.005;

    }

    /**
     * Increment account balance with interest only if balance is larger than 5000.
     */
    private void payInterest() {
        if (getBalance() > 5000) {
            setBalance(getBalance() + investment * interestRate);
        }
    }

    /**
     * Update the account balance with
     */
    @Override
    void update() {
        payInterest();
    }

    @Override
    public String toString() {
        return "Cashable GIC account | Balance: $" + getBalanceString();
    }
}
