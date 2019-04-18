package ATM.Account;

import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountManagerTest {

    private AccountManager manager;

    @Before
    public void setUp() {
        manager = new AccountManager();

        Account account1 = mock(Account.class);
        Account account2 = mock(Account.class);
        Account account3 = mock(Account.class);

        when(account1.ownedBy("Bob")).thenReturn(true);
        when(account1.ownedBy("Jim")).thenReturn(false);
        when(account1.ownedBy("Sally")).thenReturn(false);

        when(account2.ownedBy("Bob")).thenReturn(false);
        when(account2.ownedBy("Jim")).thenReturn(true);
        when(account2.ownedBy("Sally")).thenReturn(false);

        when(account3.ownedBy("Bob")).thenReturn(false);
        when(account3.ownedBy("Jim")).thenReturn(true);
        when(account3.ownedBy("Sally")).thenReturn(true);

        when(account1.getID()).thenReturn("1");
        when(account2.getID()).thenReturn("2");
        when(account3.getID()).thenReturn("3");

        when(account1.isPrimary()).thenReturn(false);
        when(account2.isPrimary()).thenReturn(false);
        when(account3.isPrimary()).thenReturn(true);

        when(account1.toString()).thenReturn("account1");

        manager.addAccount(account1);
        manager.addAccount(account2);
        manager.addAccount(account3);
    }

    @Test
    public void testGetAccountIDs() {
        List<String> bob = manager.getAccountIDs("Bob");
        assertTrue(bob.contains("1"));
        assertFalse(bob.contains("2"));
        assertFalse(bob.contains("3"));

        List<String> jim = manager.getAccountIDs("Jim");
        assertFalse(jim.contains("1"));
        assertTrue(jim.contains("2"));
        assertTrue(jim.contains("3"));

        List<String> sally = manager.getAccountIDs("Sally");
        assertFalse(sally.contains("1"));
        assertFalse(sally.contains("2"));
        assertTrue(sally.contains("3"));
    }

    @Test
    public void testGetPrimaryAccountID() {
        String accountID;
        accountID = manager.getPrimaryAccountID("Jim");
        assertEquals("3", accountID);
        accountID = manager.getPrimaryAccountID("Sally");
        assertEquals("3", accountID);
    }

    @Test
    public void testGetAccountString() {
        String accountString = manager.getAccountString("1");
        assertEquals("account1", accountString);
    }

}
