package ATM.User.Role.Privilege.CustomerPrivilege.RequestPrivilege;

import ATM.Bank;
import ATM.FinanceProduct.ProductsFactory;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.Request.ProductRequest;
import ATM.User.Role.Privilege.Privilege;

import java.util.List;

/**
 * A class used to request a new financial product.
 */
public class RequestProductPrivilege extends Privilege {

    private final ProductsFactory productsFactory;

    /**
     * Initialize this RequestProductPrivilege with a username, a bank, and a products factory.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     * @param productsFactory A factory used to generate the new financial products.
     */
    public RequestProductPrivilege(String username, Bank bank, ProductsFactory productsFactory) {
        super("Request a new product", username, bank);
        this.productsFactory = productsFactory;
    }

    /**
     * Prompt the user to request the creation of a new financial product.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        List<String> productTypes = ProductsFactory.getAvailableProductTypes();
        double investment = 0;
        int productTypeIndex = inputReader.getSelectionFromOptions(productTypes, "Select a product type.");
        String productType = productTypes.get(productTypeIndex);
        int duration = inputReader.getPositiveInt("Enter number of years for this product", "Duration");
        while (investment <= 50000) {
            investment = inputReader.getPositiveDouble("Must be larger than $50000", "Principal/Investment");
        }
        requestManager.addRequest(new ProductRequest(username, productType, duration, investment, productsFactory));
        messageDisplay.showMessage("Requested a new " + productType, "Successful request.");

    }
}
