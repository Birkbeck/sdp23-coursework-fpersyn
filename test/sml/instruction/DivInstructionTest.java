package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class DivInstructionTest {
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
        registers.set(EAX, 6);
        registers.set(EBX, 3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        // can handle negative input (1/2)
        registers.set(EAX, -6);
        registers.set(EBX, 3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-2, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidThree() {
        // can handle negative input (2/2)
        registers.set(EAX, 6);
        registers.set(EBX, -3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-2, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidFour() {
        // integer division should only return integers = truncate quotient
        registers.set(EAX, 5);
        registers.set(EBX, 2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidFive() {
        // integer division by zero should throw an exception
        registers.set(EAX, 5);
        registers.set(EBX, 0);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Exception exception = Assertions.assertThrows(ArithmeticException.class, () ->
                instruction.execute(machine));
        Assertions.assertEquals("/ by zero", exception.getMessage());
    }
}