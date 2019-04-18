package ATM.Account;

import static org.junit.Assert.*;

import java.util.Date;

public class ChequingAccountTest extends AssetAccountTest<ChequingAccount> {

    @Override
    ChequingAccount createAccount() {
        Date date = new Date();
        return new ChequingAccount("Bob", date);
    }

    @Override
    public void testWithdraw() {
        boolean success;

        account.setBalance(100);
        success = account.withdraw(1.23456);
        assertEquals(100 - 1.23, account.getBalance(), 0.0001);
        assertTrue(success);

        account.setBalance(0.01);
        success = account.withdraw(100.01);
        assertEquals(-100.0, account.getBalance(), 0.0001);
        assertTrue(success);

        account.setBalance(0.01);
        success = account.withdraw(100.02);
        assertEquals(0.01, account.getBalance(), 0.0001);
        assertFalse(success);

        account.setBalance(0);
        success = account.withdraw(-1.23456);
        assertEquals(0.0, account.getBalance(), 0.0001);
        assertFalse(success);
    }

    @Override
    public void testCanWithdraw() {
        account.setBalance(0.01);
        assertTrue(account.canWithdraw(100.01));
        assertFalse(account.canWithdraw(100.02));
        assertFalse(account.canWithdraw(-1.23456));
    }

    @Override
    public void testCanTransferOut() {
        account.setBalance(0.01);
        assertTrue(account.canTransferOut(100.01));
        assertFalse(account.canTransferOut(100.02));
        assertFalse(account.canTransferOut(-1.23456));
    }

    @Override
    public void testIsPrimary() {
        assertFalse(account.isPrimary());
        account.setPrimary(true);
        assertTrue(account.isPrimary());
    }
}
