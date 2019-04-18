package ATM.Account;

import static org.junit.Assert.assertEquals;

public abstract class AssetAccountTest<T extends AssetAccount> extends AccountTest<T> {

    @Override
    public void testGetBalanceString() {
        account.setBalance(0);
        assertEquals(account.getBalanceString(), "0.00");
        account.setBalance(1.23456);
        assertEquals(account.getBalanceString(), "1.23");
    }

}
