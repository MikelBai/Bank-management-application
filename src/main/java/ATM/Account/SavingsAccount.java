package ATM.Account;

import java.util.Date;

/**
 * A class representing a savings account.
 */
class SavingsAccount extends AssetAccount {

    /**
     * Initialize this SavingsAccount with an initial user and a creation date.
     *
     * @param username     The username of the first owner of this SavingsAccount.
     * @param creationDate The date on which this SavingsAccount was created.
     */
    public SavingsAccount(String username, Date creationDate) {
        super(username, creationDate, 0);
    }

    /**
     * Increase this SavingsAccount's balance by 0.1%.
     */
    private void compoundInterest() {
        setBalance(getBalance() * 1.001);
    }

    @Override
    void update() {
        compoundInterest();
    }

    @Override
    public String toString() {
        return "Savings Account | Balance: $" + getBalanceString();
    }
}
