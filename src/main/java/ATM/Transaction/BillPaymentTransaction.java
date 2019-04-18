package ATM.Transaction;

import ATM.Account.Account;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * A class representing a bill payment transaction, which transfers money out
 * of an account to pay an external bill.
 */
public class BillPaymentTransaction extends Transaction {

    private final static String outgoingFileName = "externalFiles/outgoing.txt";
    private final String payee;

    /**
     * Initialize this BillPaymentTransaction with a primary account, an amount,
     * a date, and a payee.
     *
     * @param primary The account from which money is to be transferred.
     * @param amount  The amount to be transferred.
     * @param date    The date of the transfer.
     * @param payee   The name of the payee.
     */
    public BillPaymentTransaction(Account primary, double amount, Date date, String payee) {
        super(primary, amount, date);
        this.payee = payee;
    }

    @Override
    boolean canExecute() {
        boolean canTransferOut = primary.canTransferOut(amount);
        return canTransferOut && isExecutable();
    }

    @Override
    public boolean canRevert() {
        return false;
    }

    @Override
    void performExecution() {
        primary.withdraw(amount);
        updateOutgoing();
    }

    @Override
    void performReversion() {
        primary.deposit(amount);
    }

    /**
     * Updates current bill payment information to outgoing.txt
     */
    private void updateOutgoing() {
        StringBuilder toWrite = new StringBuilder();
        toWrite.append(toString());
        toWrite.append("\n");
        try {
            FileWriter outFile = new FileWriter(outgoingFileName, true);
            BufferedWriter writer = new BufferedWriter(outFile);
            writer.write(toWrite.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
    }

    @Override
    String transactionInfo() {
        return "Bill payment of $" + String.format("%.2f", amount) + " from: " + primary + " to: " + payee;
    }
}
