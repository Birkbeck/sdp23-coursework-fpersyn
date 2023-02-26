import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.AddInstruction;

import java.util.NoSuchElementException;

public class LabelsTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void testAddLabelThrowsExceptionWhenInvalid() {
        // label required
        Assertions.assertThrows(NullPointerException.class,
                () -> machine.getLabels().addLabel(null, 1));

        // label already exists
        machine.getLabels().addLabel("f2", 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> machine.getLabels().addLabel("f2", 1));
        machine.getLabels().reset();
    }

    @Test
    void testGetAddress() {
        machine.getLabels().addLabel("f1", 0);
        int address = machine.getLabels().getAddress("f1");
        Assertions.assertEquals(0, address);
        machine.getLabels().reset();
    }

    @Test
    void testGetAddressThrowsExceptionWhenKeyNotFound() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> machine.getLabels().getAddress("f1"));
    }

    @Test
    void testToString() {
        machine.getLabels().addLabel("f1", 0);
        machine.getLabels().addLabel("f2", 1);
        machine.getLabels().addLabel("f3", 2);
        String expectedString = "[f1 -> 0, f2 -> 1, f3 -> 2]";
        Assertions.assertEquals(expectedString, machine.getLabels().toString());
        machine.getLabels().reset();
    }

    @Test
    void testEquals() {
        machine.getLabels().addLabel("f1", 0);
        machine.getLabels().addLabel("f2", 1);

        Machine machine2 = new Machine(new Registers());
        machine2.getLabels().addLabel("f1", 0);
        machine2.getLabels().addLabel("f2", 1);

        Assertions.assertEquals(machine.getLabels(), machine2.getLabels());
    }
}
