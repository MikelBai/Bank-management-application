package ATM.Account;

import java.util.Date;

/**
 * A class representing a ChequingAccount
 */
public class ChequingAccount extends AssetAccount {

    private boolean primary;

    /**
     * Initialize this ChequingAccount with an initial user and a creation date.
     *
     * @param username     The username of the first owner of this ChequingAccount.
     * @param creationDate The date on which this ChequingAccount was created.
     */
    public ChequingAccount(String username, Date creationDate) {
        super(username, creationDate, -100);
        setPrimary(false);
    }

    @Override
    boolean isPrimary() {
        return primary;
    }

    /**
     * Set whether this ChequingAccount is the primary account.
     *
     * @param primary whether this ChequingAccount is primary.
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String toString() {
        return "Chequing Account | Balance: $" + getBalanceString();
    }

}
