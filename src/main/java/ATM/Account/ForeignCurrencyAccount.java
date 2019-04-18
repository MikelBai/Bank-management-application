package ATM.Account;

import java.util.Date;

public class ForeignCurrencyAccount extends Account {

    private String currencyCode;
    private Character currencySymbol;
    private boolean currencyPermanentlySet;

    public ForeignCurrencyAccount(String username, Date creationDate) {
        super(username, creationDate, 0);
        currencyCode = "CAD";
        currencySymbol = '$';
        currencyPermanentlySet = false;
    }

    //TODO add to AccountFactory                                                                            done
    //TODO add relevant methods to AccountManager to support converting currencies                          done
    //TODO add a Privilege class to set the currency type of this account                                   not done
    //TODO add current supported currencies list to Bank                                                    done
    //TODO override some account characteristics in this account, disable withdrawals                       done

    public boolean setCurrencyCode(String currencyCode) {
        if (currencyPermanentlySet) {
            return false;
        }
        this.currencyCode = currencyCode;
        switch (currencyCode) {
            case ("USD"): {
                currencySymbol = '$';
                break;
            }
            case ("EUR"): {
                currencySymbol = '€';
                break;
            }
            case ("JPY"): {
                currencySymbol = '¥';
                break;
            }
            case ("GBP"): {
                currencySymbol = '£';
                break;
            }
            case ("AUD"): {
                currencySymbol = '$';
                break;
            }
            case ("CNH"): {
                currencySymbol = '¥';
                break;
            }
        }
        if (currencySymbol != null) {
            currencyPermanentlySet = true;
            return true;
        }
        return false;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public char getCurrencySymbol() {
        return currencySymbol;
    }

    @Override
    String getBalanceString() {
        return String.format("%.2f", getBalance());
    }


    @Override
    public String toString() {
        return "Foreign Currency Account | Balance: " + currencyCode + " " + currencySymbol + getBalanceString();
    }
}
