package ATM.User.Role.Privilege.EmployeePrivilege.ApproveRequestPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.Request;
import ATM.User.Role.Privilege.Privilege;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to approve the creation of a new financial product.
 */
public class ApproveProductCreationRequestPrivilege extends Privilege {

    /**
     * Initialize this ApproveProductCreationRequestPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public ApproveProductCreationRequestPrivilege(String username, Bank bank) {
        super("Review product creation requests", username, bank);
    }

    /**
     * Prompt the Bank Manager to create a financial product from a list of product creation requests.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<Request> productRequests = requestManager.getProductRequests();
        if (productRequests.size() > 0) {
            int requestIndex = inputReader.getSelectionFromOptions(productRequests, "Select a request to assess");

            List<String> options = new ArrayList<>();
            options.add("Yes");
            options.add("No");
            int selection = inputReader.getSelectionFromOptions(options, "Set up new product?");

            if (selection == 0) {
                if (requestManager.executeRequest(requestIndex, 3)) {
                    messageDisplay.showMessage("Successfully set up new product", "Successful action");
                } else {
                    messageDisplay.showMessage("Failed to set up new product", "Action failed");
                }
            } else {
                messageDisplay.showMessage("Request rejected", "Rejected");
                requestManager.removeRequest(requestIndex, 3);
            }
        } else {
            messageDisplay.showMessage("No current requests", "Product Requests");
        }
    }
}
