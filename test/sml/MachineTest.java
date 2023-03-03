package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sml.Machine;
import sml.Registers;

public class MachineTest {
    @Test
    void testEquals() {
        Machine mach1 = new Machine();
        Machine mach2 = new Machine();

        Assertions.assertTrue(mach1.equals(mach2));
    }

    @Test
    void testEqualsTwo() {
        Machine mach1 = new Machine();
        String mach2 = new String(mach1.toString());

        Assertions.assertFalse(mach1.equals(mach2));
    }
}
