package ATM.User.Role.Privilege.EmployeePrivilege.ATMMaintenancePrivilege;

import ATM.Bank;
import ATM.GUI.GraphicalInputReader;
import ATM.GUI.MessageDisplay;
import ATM.User.Role.Privilege.Privilege;

/**
 * A class used to restock an ATM.
 */
public class RestockATMPrivilege extends Privilege {

    /**
     * Initialize this RestockATMPrivilege with a username and a bank.
     *
     * @param username The name of the User associated with this privilege.
     * @param bank     The Bank on which this Privilege operates.
     */
    public RestockATMPrivilege(String username, Bank bank) {
        super("Restock the ATM", username, bank);
    }

    /**
     * Prompt the BankManager to restock this Session's ATM.
     *
     * @param inputReader    A reader to get input from the user.
     * @param messageDisplay A display to show messages to the user.
     */
    @Override
    public void perform(GraphicalInputReader inputReader, MessageDisplay messageDisplay) {
        messageDisplay.showMessage(atm.stockLevel(), "Current stock");
        int fives = inputReader.getPositiveInt("Number of fives: ", "Input value");
        restockATM(5, fives, messageDisplay);
        int tens = inputReader.getPositiveInt("Number of tens: ", "Input value");
        restockATM(10, tens, messageDisplay);
        int twenties = inputReader.getPositiveInt("Number of twenties: ", "Input value");
        restockATM(20, twenties, messageDisplay);
        int fifties = inputReader.getPositiveInt("Number of fifties: ", "Input value");
        restockATM(50, fifties, messageDisplay);
    }

    /**
     * Restock an ATM with bills.
     *
     * @param denomination   The denomination to restock.
     * @param quantity       The quantity of the denomination to restock.
     * @param messageDisplay A display used to show messages.
     */
    private void restockATM(int denomination, int quantity, MessageDisplay messageDisplay) {
        boolean success = false;
        switch (denomination) {
            case 5: {
                if (quantity + atm.getFives() <= atm.getBillQuantityLimit()) {
                    atm.add(5, quantity);
                    success = true;
                }
                break;
            }
            case 10: {
                if (quantity + atm.getTens() <= atm.getBillQuantityLimit()) {
                    atm.add(10, quantity);
                    success = true;
                }
                break;
            }
            case 20: {
                if (quantity + atm.getTwenties() <= atm.getBillQuantityLimit()) {
                    atm.add(20, quantity);
                    success = true;
                }
                break;
            }
            case 50: {
                if (quantity + atm.getFifties() <= atm.getBillQuantityLimit()) {
                    atm.add(50, quantity);
                    success = true;
                }
                break;
            }
        }
        if (success) {
            messageDisplay.showMessage("Restocked " + quantity + " $" + denomination + " bills", "Restock succeeded");
        } else {
            messageDisplay.showErrorMessage("Attempted to restock too many bills", "Restock failed");
        }
    }
}
