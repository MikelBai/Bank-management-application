package ATM.Account;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

public class SavingsAccountTest extends AssetAccountTest<SavingsAccount> {

    @Override
    SavingsAccount createAccount() {
        Date date = new Date();
        return new SavingsAccount("Bob", date);
    }

    @Override
    public void testWithdraw() {
        boolean success;

        account.setBalance(100);
        success = account.withdraw(1.23456);
        assertEquals(100 - 1.23, account.getBalance(), 0.0001);
        assertTrue(success);

        account.setBalance(0.01);
        success = account.withdraw(0.01);
        assertEquals(0.0, account.getBalance(), 0.0001);
        assertTrue(success);

        success = account.withdraw(0.01);
        assertEquals(0.0, account.getBalance(), 0.0001);
        assertFalse(success);

        account.setBalance(0);
        success = account.withdraw(-1.23456);
        assertEquals(0.0, account.getBalance(), 0.0001);
        assertFalse(success);
    }

    @Override
    public void testCanWithdraw() {
        account.setBalance(0.01);
        assertTrue(account.canWithdraw(0.01));
        assertFalse(account.canWithdraw(0.02));
        assertFalse(account.canWithdraw(-1.23456));
    }

    @Override
    public void testCanTransferOut() {
        account.setBalance(0.01);
        assertTrue(account.canTransferOut(0.01));
        assertFalse(account.canTransferOut(0.02));
        assertFalse(account.canTransferOut(-1.23456));
    }

    @Override
    public void testIsPrimary() {
        assertFalse(account.isPrimary());
    }


    @Test
    public void testCompoundInterest() {
        account.setBalance(0);
        account.update();
        assertEquals(account.getBalance(), 0.0, 0.0001);

        account.setBalance(100);
        account.update();
        assertEquals(account.getBalance(), 100.1, 0.0001);
    }
}
