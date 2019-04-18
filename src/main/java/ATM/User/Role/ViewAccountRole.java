package ATM.User.Role;

import ATM.Bank;
import ATM.User.Role.Privilege.PrivilegeFactory;

class ViewAccountRole extends Role {

    private final static String name = "Bank account information";

    /**
     * Initialize this ViewAccountRole with a username, a bank, and a privilege factory.
     *
     * @param username         The user which possesses this Role.
     * @param bank             The Bank which this Role operates on.
     * @param privilegeFactory A factory used to create the necessary privileges.
     */
    public ViewAccountRole(String username, Bank bank, PrivilegeFactory privilegeFactory) {
        super(name, username, bank, privilegeFactory);
    }

    @Override
    void setPrivilegeNames() {
        privilegeNames.add("View Account Information");
        privilegeNames.add("View Account Summary");
        privilegeNames.add("View Products info");
    }
}
