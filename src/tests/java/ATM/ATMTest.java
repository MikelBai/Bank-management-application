package ATM;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ATMTest {

    private ATM atm;

    @Before
    public void setUp() {
        atm = new ATM();
    }

    @Test
    public void testAdd() {
        atm.add(50, 50);
        atm.add(20, 50);
        atm.add(10, 50);
        atm.add(5, 50);
        assertEquals(atm.getFifties(), 50);
        assertEquals(atm.getTwenties(), 50);
        assertEquals(atm.getTens(), 50);
        assertEquals(atm.getFives(), 50);
    }
}
