package ATM.Session;

import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.ATMMaintenanceRole;
import ATM.User.Role.Role;
import ATM.User.User;

/**
 * Represents an ATM session, which prompts the user to select options
 * and translates their input into appropriate method calls.
 */
public class Session {

    private User user;
    private boolean running;
    private boolean continueProgram = true;
    private OptionsTree currentOption;
    private OptionsTree optionsTree;
    private final GraphicalInputReader inputReader;
    private final MessageDisplay messageDisplay;

    /**
     * Initialize this Session with a User and an input reader.
     *
     * @param user           The User associated with this Session.
     * @param inputReader    A reader used to get input from the user.
     * @param messageDisplay A display used to show messages to the user.
     */
    public Session(User user, GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        this.user = user;
        this.inputReader = inputReader;
        this.messageDisplay = messageDisplay;
        generateOptionsTree();
    }

    /**
     * Run this Session.
     *
     * @return true if the main program should continue running after this
     * Session exits, false if the main program should terminate.
     */
    public boolean run() {
        resetCurrentOption();
        running = true;
        while (running) {
            if (currentOption.hasChildren()) {
                currentOption = currentOption.getChildFromUserInput(inputReader);
            } else {
                currentOption.executeCommand();
                resetCurrentOption();
            }
        }
        return continueProgram;
    }

    /**
     * Generate the OptionsTree for this Session.
     */
    private void generateOptionsTree() {
        optionsTree = new OptionsTree();

        for (Role role : user.getRoles()) {
            OptionsTree newOptionsTree = role.getOptionsTree(inputReader, messageDisplay);

            // Add additional options for terminating the ATM and exiting a menu
            if (role.getName().equals(ATMMaintenanceRole.name)) {
                newOptionsTree.add("Shutdown ATM", this::terminateProgram);
            }
            newOptionsTree.add("Go back", this::resetCurrentOption);

            optionsTree.add(newOptionsTree);
        }

        // Add an option to exit the Session
        optionsTree.add("Exit session", this::exit);
    }

    /**
     * Exit this Session.
     */
    private void exit() {
        running = false;
    }

    /**
     * Go back to the root option in this Session's OptionsTree.
     */
    private void resetCurrentOption() {
        currentOption = optionsTree;
    }

    /**
     * Exit this session and instruct the main program to terminate.
     */
    private void terminateProgram() {
        continueProgram = false;
        exit();
    }

    /**
     * Set this Session's User.
     *
     * @param user The User to set.
     */
    void setUser(User user) {
        this.user = user;
    }
}
