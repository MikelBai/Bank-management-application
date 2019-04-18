package ATM.FinanceProduct;

import java.util.Date;

/**
 * A class representing Mortgage.
 */
class Mortgage extends FinanceProduct {

    /**
     * Initialize mortgage with a username, total duration,
     * a interest rate and principal.
     *
     * @param username     Name of user who owns this mortgage.

     * @param duration     Total duration of this mortgage.
     * @param interestRate A interest rate for principal to increment.
     * @param Principal    Total amount borrowed.
     */
    Mortgage(String username,  int duration, Date creationDate, double interestRate, double Principal) {
        super(username, duration, Principal, creationDate);
        setInterestRate(interestRate);
    }


    public void setInitialInfo() {
        setInitialInfo("\nMortgage belonging to customer: " + getUsername() + "\n" +
                "Associated account: " + getAssociated().toString() + "\n" +
                "Principal: " + String.format("%.2f", getInvestment()) + "\n" +
                "Will last for " + getDuration() + " months\n" +
                "Created on :" + getCreationDate() + "\n");

    }

    /**
     *Check primary account has enough balance for the creation of this Mortgage
     * @return boolean initializable.
     */

    @Override
    public boolean initializable() {
        return true;
    }

    /**
     * Make a payment if the associated account has enough balance.
     */
    @Override
    void update() {
        boolean check = getAssociated().canWithdraw(getPaymentDue());

        if (check) {
            getAssociated().withdraw(getPaymentDue());
            MakePaymentDue();
        }

        else {
            SkipPaymentDue();
        }
    }

    @Override
    public String toString() {
        if (getDuration() <= 0 && getInvestment() < 0.001) {
            return getInitialInfo() + "MORTGAGE COMPLETED\n";
        } else {
            return "\nMortgage belonging to customer: " + getUsername() + "\n" +
                    "Associated account: " + getAssociated().toString() + "\n" +
                    "Principal remain: " + String.format("%.2f", getInvestment()) + "\n" +
                    "Amount due next: " + String.format("%.2f", getPaymentDue()) + "\n" +
                    "Will last for " + getDuration() + " months\n";
        }

    }
}
