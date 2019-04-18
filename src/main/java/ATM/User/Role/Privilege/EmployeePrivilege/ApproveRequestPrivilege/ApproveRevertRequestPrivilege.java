package ATM.User.Role.Privilege.EmployeePrivilege.ApproveRequestPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.Request;
import ATM.User.Role.Privilege.Privilege;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to approve transaction reversion requests.
 */
public class ApproveRevertRequestPrivilege extends Privilege {

    /**
     * Initialize this ApproveRevertRequestPrivilege with a username, a bank, and GUI input and output objects.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ApproveRevertRequestPrivilege(String username, Bank bank) {
        super("Review transaction reversion requests", username, bank);
    }

    /**
     * Prompt the Bank Manager to revert a transaction from a list of reversion requests.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<Request> revertRequests = requestManager.getRevertTransactionRequests();
        if (revertRequests.size() > 0) {
            int requestIndex = inputReader.getSelectionFromOptions(revertRequests, "Select a request to assess");

            List<String> options = new ArrayList<>();
            options.add("Yes");
            options.add("No");
            int selection = inputReader.getSelectionFromOptions(options, "Revert transaction?");

            if (selection == 0) {
                if (requestManager.executeRequest(requestIndex, 2)) {
                    messageDisplay.showMessage("Successfully reverted transaction", "Successful action");
                } else {
                    messageDisplay.showMessage("Failed to revert transaction", "Action failed");
                }
            } else {
                messageDisplay.showMessage("Request rejected", "Rejected");
                requestManager.removeRequest(requestIndex, 2);
            }
        } else {
            messageDisplay.showMessage("No current requests", "Revert Transaction Requests");
        }
    }
}
