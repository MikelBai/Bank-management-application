package ATM.Request;

import ATM.FinanceProduct.FinanceProduct;
import ATM.FinanceProduct.ProductsFactory;

public class ProductRequest extends Request {
    private final String productType;
    private final ProductsFactory productsFactory;
    private final int duration;
    private final double investment;

    /**
     * Initializes a product type.
     * @param username The user who requested a new product.
     * @param productType the type of product.
     * @param duration Duration of requested product.
     * @param investment Total investment of requested product.
     * @param productsFactory the product factory.
     */
    public ProductRequest(String username, String productType, int duration, double investment, ProductsFactory productsFactory){
        super(username);
        this.productType = productType;
        this.productsFactory = productsFactory;
        this.duration = duration;
        this.investment = investment;
    }

    /**
     * Execute the request
     * @return a boolean of whether the request was successfully executed.
     */
    boolean execute(){
        FinanceProduct newProduct = productsFactory.getProduct(productType,username,duration,investment);
        if (newProduct != null && newProduct.initializable()){
            productManager.addProduct(newProduct);
            return true;
        }
        return false;
    }

    /**
     * returns a string representation of the request
     * @return a String representation
     */
    @Override
    public String toString() {
        return "Customer " + username + " requested a new " + productType;
    }
}
