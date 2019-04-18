package ATM.FinanceProduct;

import ATM.Account.AccountManager;
import ATM.BankTimeManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class used to instantiate new products.
 */
public class ProductsFactory implements Serializable {

    private final BankTimeManager bankTimeManager;
    private final AccountManager accountManager;

    /**
     * Initialize this ProductFactory with a BankTimeManager
     * @param bankTimeManager The time manager used to record the date of product creation.
     * @param accountManager The account manager used to get a customer's primary account.
     */
    public ProductsFactory(BankTimeManager bankTimeManager, AccountManager accountManager) {
        this.bankTimeManager = bankTimeManager;
        this.accountManager = accountManager;
    }

    public static List<String> getAvailableProductTypes() {
        List<String> returnList = new ArrayList<>();
        returnList.add("Mortgage");
        returnList.add("non Redeemable GIC");
        return returnList;
    }

    /**
     * Instantiate a new product.
     * @param type A String representing the Product type.
     * @param username The username of the owner.
     * @param duration Duration of this product.
     * @param investment The amount invested in this product.
     * @return The new Product, of null if an invalid product type is given.
     */
    public FinanceProduct getProduct(String type, String username, int duration, double investment) {
        FinanceProduct product = null;
        Date currentTime = bankTimeManager.getCurrentTime();

        switch (type) {
            case "Mortgage": {
                product = new Mortgage(username, duration, currentTime, 0.02, investment);
                accountManager.associateProductAccount(product);
                product.setInitialInfo();
                break;
            }
            case "non Redeemable GIC": {
                product = new NonRedeemableGIC(username, duration, currentTime, 0.02, investment);
                accountManager.associateProductAccount(product);
                product.setInitialInfo();
                break;
            }
        }
        return product;
    }
}
