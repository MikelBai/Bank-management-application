package ATM.Account;

import static org.junit.Assert.*;

import java.util.Date;

public class CreditCardAccountTest extends DebtAccountTest<CreditCardAccount>{

    @Override
    CreditCardAccount createAccount() {
        Date date = new Date();
        return new CreditCardAccount("Bob", date);
    }

    @Override
    public void testWithdraw() {
        boolean success;

        account.setBalance(100);
        success = account.withdraw(1.23456);
        assertEquals(100 - 1.23, account.getBalance(), 0.0001);
        assertTrue(success);

        account.setBalance(-99999.99);
        success = account.withdraw(0.01);
        assertEquals(-100000.0, account.getBalance(), 0.0001);
        assertTrue(success);

        success = account.withdraw(0.01);
        assertEquals(-100000.0, account.getBalance(), 0.0001);
        assertFalse(success);

        account.setBalance(0);
        success = account.withdraw(-1.23456);
        assertEquals(0.0, account.getBalance(), 0.0001);
        assertFalse(success);
    }

    @Override
    public void testCanWithdraw() {
        account.setBalance(-99999.99);
        assertTrue(account.canWithdraw(0.01));
        assertFalse(account.canWithdraw(0.02));
        assertFalse(account.canWithdraw(-1.23456));
    }

    @Override
    public void testCanTransferOut() {
        assertFalse(account.canTransferOut(0));
    }

    @Override
    public void testIsPrimary() {
        assertFalse(account.isPrimary());
    }
}
