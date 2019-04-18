package ATM.Account;

import java.util.Date;

/**
 * A class representing an asset account.
 */
abstract class AssetAccount extends Account {

    /**
     * Initialize this AssetAccount with an initial user, an overdraft limit, and a creation date.
     *
     * @param username       The username of the first owner of this AssetAccount.
     * @param overdraftLimit The maximum negative balance this AssetAccount is allowed to incur.
     * @param creationDate   The date on which this AssetAccount was created.
     */
    AssetAccount(String username, Date creationDate, double overdraftLimit) {
        super(username, creationDate, overdraftLimit);
    }

    @Override
    String getBalanceString() {
        return String.format("%.2f", getBalance());
    }
}
