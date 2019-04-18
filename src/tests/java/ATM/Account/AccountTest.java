package ATM.Account;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AccountTest<T extends Account> {

    T account;

    abstract T createAccount();

    @Before
    public void setUp() {
        account = createAccount();
    }

    @Test
    public void testSetBalance() {
        account.setBalance(0);
        assertEquals(0.0, account.getBalance(), 0.0001);
        account.setBalance(1.23456);
        assertEquals(1.23, account.getBalance(), 0.0001);
    }

    @Test
    public void testDeposit() {
        boolean success;

        account.setBalance(0);
        success = account.deposit(1.23456);
        assertEquals(1.23, account.getBalance(), 0.0001);
        assertTrue(success);

        account.setBalance(0);
        success = account.deposit(-1.23456);
        assertEquals(0.0, account.getBalance(), 0.0001);
        assertFalse(success);
    }

    @Test
    public abstract void testWithdraw();

    @Test
    public void testCanDeposit() {
        assertTrue(account.canDeposit(1.23456));
        assertFalse(account.canDeposit(-1.23456));
    }

    @Test
    public abstract void testCanWithdraw();

    @Test
    public abstract void testCanTransferOut();

    @Test
    public void testAddUser() {
        assertTrue(account.ownedBy("Bob"));
        assertFalse(account.ownedBy("Jim"));
        account.addUser("Jim");
        assertTrue(account.ownedBy("Jim"));
    }

    @Test
    public abstract void testIsPrimary();

    @Test
    public abstract void testGetBalanceString();

    @Test
    public void testAccountID() {
        String regex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(account.getID());
        assertTrue(m.matches());
    }
}
