package ATM.User.Role;

import ATM.Bank;
import ATM.User.Role.Privilege.PrivilegeFactory;

class UserRole extends Role {

    private final static String name = "User options";

    /**
     * Initialize this UserRole with a username, a bank, and a privilege factory.
     *
     * @param username         The user which possesses this Role.
     * @param bank             The Bank which this Role operates on.
     * @param privilegeFactory A factory used to create the necessary privileges.
     */
    public UserRole(String username, Bank bank, PrivilegeFactory privilegeFactory) {
        super(name, username, bank, privilegeFactory);
    }

    @Override
    void setPrivilegeNames() {
        privilegeNames.add("Change Password");
    }
}
