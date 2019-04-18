package ATM.User.Role.Privilege.EmployeePrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

import java.text.ParseException;
import java.util.Date;

/**
 * A class used to set the time of the bank.
 */
public class SetTimePrivilege extends Privilege {

    /**
     * Initialize this SetTimePrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public SetTimePrivilege(String username, Bank bank) {
        super("Set the time", username, bank);
    }

    /**
     * Prompt the Bank Manager to set the date of the Bank.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        int year = inputReader.getPositiveInt("Please enter the year (yyyy):", "Enter the year");
        int month = inputReader.getPositiveInt("Please enter the month (mm):", "Enter the month");
        int day = inputReader.getPositiveInt("Please enter the day (dd):", "Enter the day");
        String dateString = year + " " + month + " " + day;
        try {
            Date inputDate = timeManager.getDateFormat().parse(dateString);
            timeManager.setDate(inputDate);
            messageDisplay.showMessage("Date set to " + timeManager.getCurrentTime(), "Date set successfully");
        } catch (ParseException e) {
            messageDisplay.showErrorMessage("Invalid date format", "Failed to set the date");
        }
    }
}
