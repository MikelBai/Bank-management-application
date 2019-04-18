package ATM.Account;

import ATM.BankTimeManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class used to instantiate new accounts.
 */
public class AccountFactory implements Serializable {

    private final BankTimeManager bankTimeManager;

    /**
     * Initialize this AccountFactory with a BankTimeManager.
     *
     * @param bankTimeManager The time manager used to record the date of account creation.
     */
    public AccountFactory(BankTimeManager bankTimeManager) {
        this.bankTimeManager = bankTimeManager;
    }

    public static List<String> getAvailableAccountTypes() {
        List<String> returnList = new ArrayList<>();
        returnList.add("Chequing Account");
        returnList.add("Savings Account");
        returnList.add("Credit Card Account");
        returnList.add("Line of Credit Account");
        returnList.add("Cashable GIC");
        returnList.add("Foreign Currency Account");
        return returnList;
    }

    /**
     * Instantiate a new account.
     *
     * @param accountType A String representing the Account type.
     * @param username    The username of the first Account owner.
     * @return The new Account, or null if an invalid accountType was given.
     */
    public Account getAccount(String accountType, String username) {
        Account account = null;
        Date currentTime = bankTimeManager.getCurrentTime();

        switch (accountType) {
            case ("Chequing Account"): {
                account = new ChequingAccount(username, currentTime);
                break;
            }
            case ("Savings Account"): {
                account = new SavingsAccount(username, currentTime);
                break;
            }
            case ("Credit Card Account"): {
                account = new CreditCardAccount(username, currentTime);
                break;
            }
            case ("Line of Credit Account"): {
                account = new LineOfCreditAccount(username, currentTime);
                break;
            }
            case ("Foreign Currency Account"): {
                account = new ForeignCurrencyAccount(username, currentTime);
                break;
            }
            case "Cashable GIC":
                account = new CashableGIC(username, currentTime);
                break;
        }
        return account;
    }
}
