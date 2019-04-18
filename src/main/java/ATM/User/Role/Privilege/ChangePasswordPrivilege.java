package ATM.User.Role.Privilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.User;

/**
 * A class used to change a User's password.
 */
public class ChangePasswordPrivilege extends Privilege {

    /**
     * Initialize this ChangePasswordPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ChangePasswordPrivilege(String username, Bank bank) {
        super("Change your password", username, bank);
    }

    /**
     * Prompt the user to change their password.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String oldPassword = inputReader.getString("Enter your old password", "Old password");
        String newPassword = inputReader.getString("Enter your new password", "New password");
        User user = userManager.getUser(username);
        if (user.isValidPassword(oldPassword)) {
            user.setPassword(newPassword);
            messageDisplay.showMessage("Password changed successfully", "Password changed");
        } else {
            messageDisplay.showErrorMessage("Old password invalid", "Failed to change password");
        }
    }

}
