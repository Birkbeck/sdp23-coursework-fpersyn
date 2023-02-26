import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Machine;
import sml.Registers;

public class LabelsTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        //...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void testToString() {
        machine.getLabels().addLabel("f1", 0);
        machine.getLabels().addLabel("f2", 1);
        machine.getLabels().addLabel("f3", 2);
        String expectedString = "[f1 -> 0, f2 -> 1, f3 -> 2]";
        Assertions.assertEquals(expectedString, machine.getLabels().toString());
    }

}
