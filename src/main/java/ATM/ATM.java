package ATM;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an ATM, including its bill denominations.
 */
public class ATM implements Serializable {
    private final int billQuantityLimit = 1000;
    private final int billMinimumLimit = 20;
    private final ATMCalculator calculator;
    private final String alertsFileName;
    private final String depositsFileName;
    private int fives;
    private int tens;
    private int twenties;
    private int fifties;
    private int totalDollarAmount;

    /**
     * Initialize this ATM with no bills.
     */
    public ATM() {
        this.fifties = 0;
        this.twenties = 0;
        this.tens = 0;
        this.fives = 0;
        this.totalDollarAmount = 0;
        this.calculator = new ATMCalculator(this);
        alertsFileName = "externalFiles/alerts.txt";
        depositsFileName = "externalFiles/deposits.txt";
    }

    /**
     * Return the name of the alerts file.
     *
     * @return the name of the alters file.
     */
    public String getAlertsFileName() {
        return alertsFileName;
    }

    /**
     * Withdraw bills from the ATM.
     *
     * @param dollars The amount to withdraw, in dollars.
     */
    public boolean withdraw(int dollars) {
        boolean success = false;
        int[] billAmounts = {fifties, twenties, tens, fives};
        if (calculator.canWithdrawBasic(dollars)) {
            if (dollars == this.totalDollarAmount) {
//                defaults to everything
                success = true;
            } else {
                int[] potentialSolution = calculator.canWithdrawAdvanced(dollars);
                if (potentialSolution[0] != -1) {
                    billAmounts = Arrays.copyOf(potentialSolution, potentialSolution.length);
                    success = true;
                }
            }
        }
        if (success) {
            withdraw(dollars, billAmounts);
        } else {
            System.out.println("Withdrawal unsuccessful. Please check that the amount is a multiple of 5. If it is " +
                    "already a multiple of 5, a manager should restock the ATM shortly.");
        }
        if (needsRestock()) {
            alertRestock();
        }
        return success;
    }

    private boolean needsRestock() {
        return (fifties < billMinimumLimit || twenties < billMinimumLimit ||
                tens < billMinimumLimit || fives < billMinimumLimit);
    }

    private void withdraw(int dollars, int[] billAmounts) {
        this.totalDollarAmount = getNewTotalDollarAmount(-dollars); //subtracting dollars from total
        this.fifties -= billAmounts[0];
        this.twenties -= billAmounts[1];
        this.tens -= billAmounts[2];
        this.fives -= billAmounts[3];
        System.out.println(withdrawalMessage(dollars, billAmounts[0], billAmounts[1], billAmounts[2], billAmounts[3]));
    }

    /**
     * Generate and return a message about a withdrawal.
     *
     * @param dollars     The total dollar amount.
     * @param numFifties  The number of fifties.
     * @param numTwenties The number of twenties.
     * @param numTens     The number of tens.
     * @param numFives    The number of fives.
     * @return a message describing the withdrawal.
     */
    private String withdrawalMessage(int dollars, int numFifties, int numTwenties, int numTens, int numFives) {
        return "Returned " + dollars + " in "
                + numFifties + " $50 bills, "
                + numTwenties + " $20 bills, "
                + numTens + " $10 bills, and "
                + numFives + " $5 bills.";
    }

    /**
     * Add bills to the ATM. Can be done by either User or BankManager.
     * Has to either be in 5, 10, 20, or 50 dollar bills.
     *
     * @param denomination The bill denomination to add.
     * @param count        The number of bills to add.
     */
    public void add(int denomination, int count) {
        switch (denomination) {
            case 50: {
                fifties += count;
                this.totalDollarAmount = getNewTotalDollarAmount(50 * count);
                break;
            }
            case 20: {
                twenties += count;
                this.totalDollarAmount = getNewTotalDollarAmount(20 * count);
                break;
            }
            case 10: {
                tens += count;
                this.totalDollarAmount = getNewTotalDollarAmount(10 * count);
                break;
            }
            case 5: {
                fives += count;
                this.totalDollarAmount = getNewTotalDollarAmount(5 * count);
                break;
            }
        }
    }

    public boolean canWithdraw(int dollars) {
        if (needsRestock()) {
            alertRestock();
        }
        int[] potentialSolution = calculator.canWithdrawAdvanced(dollars);
        return potentialSolution[0] != -1;
    }

    /**
     * Recalculate the dollar amount stored in this ATM through calculating a known difference.
     *
     * @param difference The signed difference in dollars to add.
     *                   Positive means a deposit occurred.
     *                   Negative means a withdrawal occurred.
     */
    private int getNewTotalDollarAmount(int difference) {
        return this.totalDollarAmount + difference;
    }

    /**
     * Write a message to the alerts file indicating which bills need to be restocked.
     */
    private void alertRestock() {
        StringBuilder restock = new StringBuilder("Need to restock:\n");
        if (this.fifties < billMinimumLimit) {
            restock.append("$50 bills.\n");
        }
        if (this.twenties < billMinimumLimit) {
            restock.append("$20 bills.\n");
        }
        if (this.tens < billMinimumLimit) {
            restock.append("$10 bills.\n");
        }
        if (this.fives < billMinimumLimit) {
            restock.append("$5 bills.\n");
        }
        try {
            FileWriter outFile = new FileWriter(alertsFileName, true);
            BufferedWriter writer = new BufferedWriter(outFile);
            writer.write(restock.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong with writing to alerts.txt.");
        }
    }

    /**
     * Get the number of fives in this ATM.
     *
     * @return the number of fives.
     */
    public int getFives() {
        return fives;
    }

    /**
     * Get the number of tens in this ATM.
     *
     * @return the number of tens.
     */
    public int getTens() {
        return tens;
    }

    /**
     * Get the number of twenties in this ATM.
     *
     * @return the number of twenties.
     */
    public int getTwenties() {
        return twenties;
    }

    /**
     * Get the number of fifties in this ATM.
     *
     * @return the number of fifties.
     */
    public int getFifties() {
        return fifties;
    }

    /**
     * Get the number of cash in this ATM.
     *
     * @return the amount of cash.
     */
    private int getTotalDollarAmount() {
        return totalDollarAmount;
    }

    /**
     * Get the maximum number of each bill denomination this ATM can store.
     *
     * @return the maximum number of each bill denomination.
     */
    public int getBillQuantityLimit() {
        return billQuantityLimit;
    }

    /**
     * Get a string representing the current bills in stock in this ATM.
     *
     * @return a string representing the stock of each bill in this ATM.
     */
    public String stockLevel() {
        return "ATM currently has:\n" +
                fifties + " $50 bills\n" +
                twenties + " $20 bills\n" +
                tens + " $10 bills\n" +
                fives + " $5 bills\n" +
                "The limit of this ATM is " + billQuantityLimit + " of each type of bill";
    }

    /**
     * Parse the deposits file.
     *
     * @return an ArrayList containing information about the deposits listed in the deposits file.
     */
    public ArrayList<String[]> getDeposits() {
        ArrayList<String[]> depositsList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(depositsFileName));
            String line = reader.readLine();
            while (line != null) {
                String[] deposit = line.split("[$#]");
                depositsList.add(deposit);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("File cannot be accessed");
        }
        return depositsList;
    }

    class ATMCalculator implements Serializable {

        private final ATM atm;

        ATMCalculator(ATM atm) {
            this.atm = atm;
        }

        /**
         * Determine whether a withdrawal from this ATM is possible, only checking that the dollar amount is not greater
         * than what the ATM is holding onto, and that the dollar amount desired is a multiple of 5.
         *
         * @param dollars The amount to withdraw.
         * @return true if the withdrawal is not possible, false otherwise.
         */
        boolean canWithdrawBasic(int dollars) {
            return (dollars <= atm.getTotalDollarAmount() && dollars % 5 == 0);
        }

        /**
         * Determine whether a withdrawal from this ATM is possible, using combinations.
         *
         * @param dollars The amount to withdraw.
         * @return true if the withdrawal is not possible, false otherwise.
         */

        int[] canWithdrawAdvanced(int dollars) {
            ArrayList<Integer[]> refinedSolutions = findWithdrawalSolutions(dollars, new int[]{50, 20, 10, 5},
                    new int[]{atm.getFifties(), atm.getTwenties(), atm.getTens(), atm.getFives()},
                    new int[]{0, 0, 0, 0}, 0);

            if (refinedSolutions.size() == 0) {
                return new int[]{-1, -1, -1, -1};
            } else {
                Integer[] solution = refinedSolutions.get(0);
                return new int[]{solution[0], solution[1], solution[2], solution[3]};
            }
        }

        /**
         * Finds the solutions to a dollar amount desired to withdraw.
         *
         * @param dollars               The amount to withdraw.
         * @param denominations         The bill denominations available to choose from.
         * @param billStock             The stock of the bills.
         * @param billStockingVariation An arrangement of bill stock to be tested on.
         * @param position              The index/position of which variations is being incremented on, i.e. the index associated with
         *                              a certain denomination of which the billStockingVariation is incrementing on.
         * @return An ArrayList of all possible solutions.
         */
        private ArrayList<Integer[]> findWithdrawalSolutions(int dollars, int[] denominations, int[] billStock, int[]
                billStockingVariation, int position) {
            ArrayList<Integer[]> viableVariations = new ArrayList<>();
            int combinationValue = computeDenominationCombination(denominations, billStockingVariation);
            if (combinationValue == dollars) {
                viableVariations.add(myCopy(billStockingVariation));
            } else if (combinationValue < dollars) {
                for (int i = position; i < denominations.length; i++) {
                    if (billStock[i] > billStockingVariation[i]) {
                        int[] newVariation = billStockingVariation.clone();
                        newVariation[i]++;
                        ArrayList<Integer[]> newList = findWithdrawalSolutions(dollars, denominations, billStock,
                                newVariation, i);
                        if (newList != null) {
                            viableVariations.addAll(newList);
                        }
                    }
                }
            }
            return viableVariations;
        }

        /**
         * Computes the maximum sum possible with this arrangement of denominations and denomination amounts.
         *
         * @param denominations      The bill denominations available to choose from.
         * @param billStockVariation The bill stocking variation being looked at
         * @return The sum of this denomination-stocking arrangement.
         */
        private int computeDenominationCombination(int[] denominations, int[] billStockVariation) {
            int sum = 0;
            for (int i = 0; i < billStockVariation.length; i++) {
                sum += denominations[i] * billStockVariation[i];
            }
            return sum;
        }

        /**
         * Creates a copy of an Integer[] from an int[]. Necessary due to not finding something similar to int[]'s deep
         * copying methods.
         *
         * @param arr The array to copy.
         * @return arr, but as an Integer[]
         */
        private Integer[] myCopy(int[] arr) {
            Integer[] copy = new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                copy[i] = arr[i];
            }
            return copy;
        }
    }
}
