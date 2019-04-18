package ATM.FinanceProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class represent Product Manager.
 */
public class ProductManager implements Serializable {

    private final List<FinanceProduct> FinanceProducts;

    /**
     * Initialize Product manager with an empty array list.
     */
    public ProductManager() {
        this.FinanceProducts = new ArrayList<>();
    }

    public void addProduct(FinanceProduct product) {
        FinanceProducts.add(product);
    }

    /**
     * Update all products in for this month.
     */
    public void updateAll() {
        for (FinanceProduct product : FinanceProducts) {
            product.update();
        }
    }

    /**
     * Returns information of all products a user owns.
     * @param username Name of user
     * @return Information of all products
     */
    public String getOwnerProductInfo(String username){
        StringBuilder toPrint = new StringBuilder();
        for (FinanceProduct product:FinanceProducts){
            if (product.getUsername().equals(username)){
                toPrint.append(product.toString());
            }
        }

        if (toPrint.toString().equals("")){return "No products.";}
        else{return toPrint.toString();}
    }

    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        for (FinanceProduct product : FinanceProducts) {
            toPrint.append(product.toString());
        }
        return toPrint.toString();
    }
}
