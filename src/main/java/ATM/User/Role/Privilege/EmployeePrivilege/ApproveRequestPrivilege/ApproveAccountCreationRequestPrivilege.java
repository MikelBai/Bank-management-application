package ATM.User.Role.Privilege.EmployeePrivilege.ApproveRequestPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.Request;
import ATM.User.Role.Privilege.Privilege;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to approve account creation requests.
 */
public class ApproveAccountCreationRequestPrivilege extends Privilege {

    /**
     * Initialize this ApproveAccountCreationRequestPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ApproveAccountCreationRequestPrivilege(String username, Bank bank) {
        super("Review account creation requests", username, bank);
    }

    /**
     * Prompt the Bank Manager to create a new account from a list of account creation requests.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<Request> accountRequests = requestManager.getAccountRequests();
        if (accountRequests.size() > 0) {
            int requestIndex = inputReader.getSelectionFromOptions(accountRequests, "Select a request to assess");

            List<String> options = new ArrayList<>();
            options.add("Yes");
            options.add("No");
            int selection = inputReader.getSelectionFromOptions(options, "Create account?");

            if (selection == 0) {
                if (requestManager.executeRequest(requestIndex, 1)) {
                    messageDisplay.showMessage("Successfully created account", "Successful action");
                } else {
                    messageDisplay.showMessage("Failed to create account", "Action failed");
                }
            } else {
                messageDisplay.showMessage("Request rejected", "Rejected");
                requestManager.removeRequest(requestIndex, 1);
            }
        } else {
            messageDisplay.showMessage("No current requests", "Account Requests");
        }
    }
}
