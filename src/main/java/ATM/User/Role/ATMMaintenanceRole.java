package ATM.User.Role;

import ATM.Bank;
import ATM.User.Role.Privilege.PrivilegeFactory;

public class ATMMaintenanceRole extends Role {

    public final static String name = "ATM maintenance";

    /**
     * Initialize this ATMMaintenanceRole with a username, a bank, and a privilege factory.
     *
     * @param username         The user which possesses this Role.
     * @param bank             The Bank which this Role operates on.
     * @param privilegeFactory A factory used to create the necessary privileges.
     */
    public ATMMaintenanceRole(String username, Bank bank, PrivilegeFactory privilegeFactory) {
        super(name, username, bank, privilegeFactory);
    }

    @Override
    void setPrivilegeNames() {
        privilegeNames.add("View ATM Alerts");
        privilegeNames.add("Restock ATM");
    }
}
