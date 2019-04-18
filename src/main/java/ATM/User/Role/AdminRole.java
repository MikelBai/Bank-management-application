package ATM.User.Role;

import ATM.Bank;
import ATM.User.Role.Privilege.PrivilegeFactory;

class AdminRole extends Role {

    private final static String name = "Administrator options";

    /**
     * Initialize this AdminRole with a username, a bank, and a privilege factory.
     *
     * @param username         The user which possesses this Role.
     * @param bank             The Bank which this Role operates on.
     * @param privilegeFactory A factory used to create the necessary privileges.
     */
    public AdminRole(String username, Bank bank, PrivilegeFactory privilegeFactory) {
        super(name, username, bank, privilegeFactory);
    }

    @Override
    void setPrivilegeNames() {
        privilegeNames.add("Create User");
        privilegeNames.add("Approve Account Creation");
        privilegeNames.add("Approve New Product");
        privilegeNames.add("Approve Revert");
    }

}
