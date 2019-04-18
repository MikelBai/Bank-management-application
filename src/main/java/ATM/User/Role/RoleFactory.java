package ATM.User.Role;

import ATM.Bank;
import ATM.User.Role.Privilege.PrivilegeFactory;

import java.io.Serializable;

/**
 * A class used to instantiate roles.
 */
public class RoleFactory implements Serializable {

    private final PrivilegeFactory privilegeFactory;

    /**
     * Initialize this RoleFactory with a PrivilegeFactory.
     *
     * @param privilegeFactory A factory used to generate the privileges for each role.
     */
    public RoleFactory(PrivilegeFactory privilegeFactory) {
        this.privilegeFactory = privilegeFactory;
    }

    /**
     * Initialize a new role.
     *
     * @param roleType The new role type.
     * @param username The name of the user exercising the role.
     * @param bank     The bank which the new role operates on.
     * @return the new role, or null if an invalid roleType was given.
     */
    public Role getRole(String roleType, String username, Bank bank) {
        Role role = null;
        switch (roleType) {
            case "Admin":
                role = new AdminRole(username, bank, privilegeFactory);
                break;
            case "ATM Maintenance":
                role = new ATMMaintenanceRole(username, bank, privilegeFactory);
                break;
            case "Employee":
                role = new EmployeeRole(username, bank, privilegeFactory);
                break;
            case "Request":
                role = new RequestRole(username, bank, privilegeFactory);
                break;
            case "Transaction":
                role = new TransactionRole(username, bank, privilegeFactory);
                break;
            case "User":
                role = new UserRole(username, bank, privilegeFactory);
                break;
            case "View Account":
                role = new ViewAccountRole(username, bank, privilegeFactory);
                break;
            case "Account Management":
                role = new AccountManagementRole(username, bank, privilegeFactory);
                break;
        }
        return role;
    }
}
