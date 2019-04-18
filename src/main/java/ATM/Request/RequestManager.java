package ATM.Request;

import ATM.Account.AccountManager;
import ATM.FinanceProduct.ProductManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestManager implements Serializable {

    private final List<Request> accountRequests;
    private final List<Request> revertTransactionRequests;
    private final List<Request> productRequests;
    private final AccountManager accountManager;
    private final ProductManager productManager;

    /**
     * The manager of all requests
     *
     * @param accountManager the account manager, for executing requests
     */
    public RequestManager(AccountManager accountManager, ProductManager productManager) {
        accountRequests = new ArrayList<>();
        revertTransactionRequests = new ArrayList<>();
        productRequests = new ArrayList<>();
        this.accountManager = accountManager;
        this.productManager = productManager;
    }

    /**
     * Returns the list of account requests
     *
     * @return list of account requests
     */
    public List<Request> getAccountRequests() {
        return accountRequests;
    }

    /**
     * Returns the list of revert transaction requests
     *
     * @return list of revert transaction requests
     */
    public List<Request> getRevertTransactionRequests() {
        return revertTransactionRequests;
    }

    public List<Request> getProductRequests() {
        return productRequests;
    }

    /**
     * Adds an account request
     *
     * @param request an AccountRequest
     */
    public void addRequest(AccountRequest request) {
        request.setAccountManager(accountManager);
        accountRequests.add(request);
    }

    public void addRequest(ProductRequest request) {
        request.setProductManager(productManager);
        productRequests.add(request);
    }

    /**
     * Adds a revert transaction request, returns whether it was successful
     *
     * @param request the transaction request
     * @return a boolean if the request was successfully added
     */
    public boolean addRequest(RevertTransactionRequest request) {
        if (request.isValid() && !request.isRequested()) {
            request.setTransactionAsRequested();
            request.setAccountManager(accountManager);
            revertTransactionRequests.add(request);
            return true;
        }
        return false;
    }

    /**
     * Removes a request after having been executed
     *
     * @param request an Account request
     */
    private void removeRequest(AccountRequest request) {
        accountRequests.remove(request);
    }

    /**
     * Removes a request after having been executed
     *
     * @param request an Product request
     */
    private void removeRequest(ProductRequest request) {
        productRequests.remove(request);
    }

    /**
     * Removes a request after having been executed
     *
     * @param request a RevertTransaction request
     */
    private void removeRequest(RevertTransactionRequest request) {
        revertTransactionRequests.remove(request);
    }

    /**
     * Removes whichever type of request at the index of the list of that type of request
     *
     * @param requestIndex the location of the request in the list
     * @param requestType  the type of request
     */
    public void removeRequest(int requestIndex, int requestType) {
        switch (requestType) {
            case 1:
                accountRequests.remove(requestIndex);
                break;
            case 2:
                revertTransactionRequests.remove(requestIndex);
                break;
            case 3:
                productRequests.remove(requestIndex);
                break;
        }
    }

    /**
     * Returns which ever type of request at that index
     *
     * @param requestIndex the location of the request in the list
     * @param requestType  the type of request
     * @return a request at a particular index
     */
    private Request getRequest(int requestIndex, int requestType) {
        switch (requestType) {
            case 1:
                return accountRequests.get(requestIndex);
            case 2:
                return revertTransactionRequests.get(requestIndex);
            case 3:
                return productRequests.get(requestIndex);
            default:
                return null;
        }
    }

    /**
     * Executes the request
     *
     * @param requestIndex the location of the request in the list
     * @param requestType  the type of request
     * @return the operation's success/failure
     */
    public boolean executeRequest(int requestIndex, int requestType) {
        boolean success = false;
        Request requestToBeExecuted = getRequest(requestIndex, requestType);
        switch (requestType) {
            case 1:
                if (requestToBeExecuted != null && requestToBeExecuted.execute()) {
                    removeRequest((AccountRequest) requestToBeExecuted);
                    success = true;
                }
                break;
            case 2:
                if (requestToBeExecuted != null && requestToBeExecuted.execute()) {
                    removeRequest((RevertTransactionRequest) requestToBeExecuted);
                    success = true;
                }
                break;
            case 3:
                if (requestToBeExecuted != null && requestToBeExecuted.execute()) {
                    removeRequest((ProductRequest) requestToBeExecuted);
                    success = true;
                }
                break;
        }
        return success;
    }
}
