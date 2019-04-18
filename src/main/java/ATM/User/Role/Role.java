package ATM.User.Role;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Session.OptionsTree;
import ATM.User.Role.Privilege.Privilege;
import ATM.User.Role.Privilege.PrivilegeFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Role, which contains a small collection of Privileges.
 */
public abstract class Role implements Serializable {

    final List<String> privilegeNames;
    private final String name;
    private final String username;
    private final Bank bank;
    private final PrivilegeFactory privilegeFactory;
    private final List<Privilege> privileges;

    /**
     * Initialize this Role with a role name, a username, a bank, and a privilege factory.
     *
     * @param name             The name of this Role.
     * @param username         The user which possesses this Role.
     * @param bank             The Bank which this Role operates on.
     * @param privilegeFactory A factory used to create the necessary privileges.
     */
    Role(String name, String username, Bank bank, PrivilegeFactory privilegeFactory) {
        this.name = name;
        this.username = username;
        this.bank = bank;
        this.privilegeFactory = privilegeFactory;
        this.privileges = new ArrayList<>();
        this.privilegeNames = new ArrayList<>();
        setPrivilegeNames();
        generatePrivileges();
    }

    /**
     * Generate an OptionsTree for this Role.
     *
     * @return an OptionsTree, containing options to execute this Role's Privileges.
     */
    public OptionsTree getOptionsTree(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        OptionsTree options = new OptionsTree(name);
        for (Privilege p : privileges) {
            options.add(p.getOptionsTree(inputReader, messageDisplay));
        }
        return options;
    }

    /**
     * Generate a list of privilege names associated with this role. These names
     * must correspond to the privilege types in PrivilegeFactory.
     */
    abstract void setPrivilegeNames();

    /**
     * Generate this Role's Privileges.
     */
    private void generatePrivileges() {
        for (String privilegeType : privilegeNames) {
            privileges.add(privilegeFactory.getPrivilege(privilegeType, username, bank));
        }
    }

    public String getName() {
        return name;
    }
}

