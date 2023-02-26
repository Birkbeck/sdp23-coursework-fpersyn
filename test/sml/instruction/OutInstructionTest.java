package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.*;

public class OutInstructionTest {
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
    void executeValid() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        registers.set(EAX, 6);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals("6\n", output.toString());
    }

    @Test
    void testToString() {
        registers.set(EAX, 5);
        Instruction instruction = new OutInstruction("f1", EAX);
        Assertions.assertEquals("f1: out EAX", instruction.toString());
    }

    @Test
    void testEquals() {
        registers.set(EAX, 5);
        Instruction instruction1 = new OutInstruction("f1", EAX);
        Instruction instruction2 = new OutInstruction("f1", EAX);
        Assertions.assertEquals(instruction1, instruction2);
    }
}
