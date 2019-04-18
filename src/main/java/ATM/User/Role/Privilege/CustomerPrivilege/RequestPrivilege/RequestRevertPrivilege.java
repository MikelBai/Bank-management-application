package ATM.User.Role.Privilege.CustomerPrivilege.RequestPrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.RevertTransactionRequest;
import ATM.Transaction.Transaction;
import ATM.User.Role.Privilege.Privilege;

import java.util.List;

public class RequestRevertPrivilege extends Privilege {

    /**
     * Initialize this RequestRevertPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public RequestRevertPrivilege(String username, Bank bank) {
        super("Revert a transaction", username, bank);
    }

    /**
     * Prompt the user to request that a transaction be reverted.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        String accountID = selectAccount(inputReader, "Select the account to revert a transaction");
        List<Transaction> transactionList = accountManager.getRecentAccountTransactions(accountID, 10);
        if (transactionList.size() >0) {
            int transactionIndex = inputReader.getSelectionFromOptions(transactionList, "Select the transaction to revert");
            Transaction transaction = accountManager.getTransactionByIndex(accountID, transactionIndex);
            boolean success = requestManager.addRequest(new RevertTransactionRequest(username, transaction));

            if (success) {
                messageDisplay.showMessage("Successfully requested to revert the transaction", "Successful request");
            } else {
                messageDisplay.showErrorMessage("Transaction cannot be reverted", "Request failed");
            }
        } else {
            messageDisplay.showMessage("There are no transactions to revert", "Select transaction");
        }
    }
}
