import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Registers;
import sml.instruction.AddInstruction;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class RegistersTest {
    @Test
    void testClear() {
        Registers reg = new Registers();
        reg.set(EAX, 1);
        reg.clear();

        Assertions.assertEquals(0, reg.get(EAX));
    }

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
