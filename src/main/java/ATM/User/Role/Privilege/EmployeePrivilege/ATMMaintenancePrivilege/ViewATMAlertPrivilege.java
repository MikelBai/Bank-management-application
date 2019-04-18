package ATM.User.Role.Privilege.EmployeePrivilege.ATMMaintenancePrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * A class used to view ATM alerts.
 */
public class ViewATMAlertPrivilege extends Privilege {

    /**
     * Initialize this ViewATMAlertPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ViewATMAlertPrivilege(String username, Bank bank) {
        super("View ATM alerts", username, bank);
    }

    /**
     * Print the alerts file.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        messageDisplay.showMessage(viewAlerts(messageDisplay), "ATM alerts");
    }

    /**
     * Return a string containing the contents of the alerts file of this BankManager's Bank.
     *
     * @param messageDisplay A display used to show the alerts.
     * @return a string representing the contents of the alerts file.
     */
    private String viewAlerts(MessageDisplay messageDisplay) {
        StringBuilder alertString = new StringBuilder();
        String filename = atm.getAlertsFileName();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                alertString.append(line);
                alertString.append("\n");
                line = reader.readLine();
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new File(filename));
            writer.print("");
            writer.close();
        } catch (Exception e) {
            messageDisplay.showErrorMessage("Error in reading alerts.txt", "File read error");
        }
        return alertString.toString();
    }
}
