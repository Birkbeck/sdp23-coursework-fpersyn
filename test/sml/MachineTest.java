package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sml.Machine;
import sml.Registers;

public class MachineTest {
    @Test
    void testEquals() {
        Registers reg1 = new Registers();
        Machine mach1 = new Machine(reg1);

        Registers reg2 = new Registers();
        Machine mach2 = new Machine(reg2);

        Assertions.assertTrue(mach1.equals(mach2));
    }

    @Test
    void testEqualsTwo() {
        Registers reg1 = new Registers();
        Machine mach1 = new Machine(reg1);

        String mach2 = new String(mach1.toString());

        Assertions.assertFalse(reg1.equals(mach2));
    }
}
