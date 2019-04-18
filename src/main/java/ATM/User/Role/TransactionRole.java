package ATM.User.Role;

import ATM.Bank;
import ATM.User.Role.Privilege.PrivilegeFactory;

public class TransactionRole extends Role {

    public final static String name = "Make a transaction";

    /**
     * Initialize this TransactionRole with a username, a bank, and a privilege factory.
     *
     * @param username         The user which possesses this Role.
     * @param bank             The Bank which this Role operates on.
     * @param privilegeFactory A factory used to create the necessary privileges.
     */
    public TransactionRole(String username, Bank bank, PrivilegeFactory privilegeFactory) {
        super(name, username, bank, privilegeFactory);
    }

    @Override
    void setPrivilegeNames() {
        privilegeNames.add("Withdraw");
        privilegeNames.add("Deposit");
        privilegeNames.add("Transfer Between Accounts");
        privilegeNames.add("Transfer to User");
        privilegeNames.add("Pay Bill");
        privilegeNames.add("View Exchange Rates");
    }
}
