package ATM.FinanceProduct;

import java.util.Date;

/**
 * A class representing non-Redeemable GIC.
 */
class NonRedeemableGIC extends FinanceProduct {
    /**
     * Initialize a non-Redeemable GIC with a username,duration,
     * a interest rate and total investment.
     *
     * @param username     Name of customer who owns this GIC.
     * @param duration     Duration of this GIC.
     * @param creationDate Date of creation.
     * @param interestRate Interest rate.
     * @param invested     Total investment.
     */
    NonRedeemableGIC(String username, int duration, Date creationDate, double interestRate, double invested) {
        super(username, duration, invested, creationDate);
        setInterestRate(interestRate);
    }

    @Override
    public void setInitialInfo() {
        setInitialInfo("\nNR-GIC belonging belongs to customer: " + getUsername() + "\n" +
                "Associated account: " + getAssociated().toString() + "\n" +
                "Investment: " + String.format("%.2f", getInvestment()) + "\n" +
                "Will last for " + getDuration() + " months\n" +
                "Created on :" + getCreationDate() + "\n");
    }

    /**
     * Check primary account has enough balance for the creation of this GIC.
     * @return boolean initializable.
     */
    @Override
    public boolean initializable() {
        boolean check = getAssociated().canWithdraw(getInvestment());
        if (check) {
            getAssociated().withdraw(getInvestment());
        }
        return check;
    }

    @Override
    void update() {
        boolean check = getAssociated().canDeposit(getPaymentDue());

        if (check) {
            getAssociated().deposit(getPaymentDue());
            MakePaymentDue();
        }

        else {
            SkipPaymentDue();
        }
    }

    @Override
    public String toString() {
        if (getDuration() < 0 && getInvestment() < 0.001) {
            return getInitialInfo() + "GIC COMPLETED\n";
        } else {
            return "\nNR-GIC belonging to customer: " + getUsername() + "\n" +
                    "Associated account: " + getAssociated().toString() + "\n" +
                    "Investment remain: " + String.format("%.2f", getInvestment()) + "\n" +
                    "Amount to pay next: " + String.format("%.2f", getPaymentDue()) + "\n" +
                    "Will last for " + getDuration() + " months\n";
        }

    }
}
