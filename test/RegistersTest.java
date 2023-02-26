import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sml.Registers;

public class RegistersTest {
    @Test
    void testEquals() {
        Registers reg1 = new Registers();
        String reg2 = new String("whatever");
        Assertions.assertFalse(reg1.equals(reg2));
    }

    @Test
    void testEqualsTwo() {
        Registers reg1 = new Registers();
        Registers reg2 = new Registers();
        Assertions.assertTrue(reg1.equals(reg2));
    }
}
