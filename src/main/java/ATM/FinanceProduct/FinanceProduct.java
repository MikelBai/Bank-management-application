package ATM.FinanceProduct;

import ATM.Account.Account;

import java.io.Serializable;
import java.util.Date;

public abstract class FinanceProduct implements Serializable {

    private Account associated;
    private double InterestRate;
    private int duration;
    private final String username;
    private String InitialInfo;
    private final Date creationDate;
    private double Investment;
    private double PaymentDue;

    /**
     * Initialize a finance product with duration of the product,
     * the amount invested in this product and a creation date.
     *
     * @param username     Name of the user who owns this product.
     * @param investment   The amount invested in this product.
     * @param duration     Duration of this product in unit of year.
     * @param creationDate Date of creation.
     */

    FinanceProduct(String username,int duration, double investment, Date creationDate) {
        this.username = username;
        this.Investment = investment;
        this.duration = 12 * duration;
        this.PaymentDue = investment / this.duration;
        this.creationDate = creationDate;
    }

    /**
     * Associate this product with an account.
     * @param associated the account to be associated.
     */
    public void setAssociated(Account associated) {
        this.associated = associated;
    }

    /**
     * Get the username of the owner.
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the associated account.
     *
     * @return The account associated with this product.
     */
    Account getAssociated() {
        return associated;
    }

    /**
     * Get total investment in this product.
     *
     * @return Total investment
     */
    double getInvestment() {
        return Investment;
    }

    /**
     * Get the duration of this product.
     *
     * @return The duration of this product in unit of month.
     */
    int getDuration() {
        return duration;
    }

    /**
     * Set the interest rate of this product.
     *
     * @param e interest rate of this product.
     */
    void setInterestRate(double e) {
        this.InterestRate = e;
    }

    /**
     * Gets information of this product upon creation.
     *
     * @return Information of this product upon creation.
     */
    String getInitialInfo() {
        return this.InitialInfo;
    }

    /**
     * Records information about this product upon creation.
     *
     * @param info Information of this product upon creation.
     */
    void setInitialInfo(String info) {
        this.InitialInfo = info;
    }

    /**
     * Set initial info after assigning account.
     */
    public abstract void setInitialInfo();


    /**
     * Gets date of creation.
     *
     * @return Date of creation.
     */
    Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Get the amount to be paid for current update cycle.
     *
     * @return The amount to be paid.
     */
    double getPaymentDue() {
        return PaymentDue;
    }

    /**
     * Updates duration of this product.
     */
    private void UpdateDuration() {
        this.duration -= 1;
    }

    /**
     * Updates total investment.
     */
    private void UpdateInvestment() {
        this.Investment += Investment * (InterestRate / 12);
    }

    /**
     * Make a payment for current cycle.
     */
    void MakePaymentDue() {
        if (duration > 0) {
            this.Investment -= PaymentDue;
            UpdateInvestment();
            UpdateDuration();
            this.PaymentDue = Investment / duration;
        } else {
            this.Investment -= PaymentDue;
        }
    }

    public abstract boolean initializable();

    /**
     * Skip a payment for current cycle.
     */
    void SkipPaymentDue() {
        if (duration > 0) {
            UpdateDuration();
            UpdateInvestment();
            this.PaymentDue += (Investment - PaymentDue) / duration;
        } else {
            UpdateInvestment();
            this.PaymentDue = Investment;
        }


    }

    abstract void update();


}
