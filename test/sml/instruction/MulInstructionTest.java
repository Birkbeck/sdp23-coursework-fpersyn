package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class MulInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        var factory = new ClassPathXmlApplicationContext("beans.xml");
        machine = (Machine) factory.getBean("machine");
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeValid() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);

        Assertions.assertEquals(30, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, -5);
        registers.set(EBX, 6);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);

        Assertions.assertEquals(-30, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction = new MulInstruction("f1", EAX, EBX);

        Assertions.assertEquals("f1: mul EAX EBX", instruction.toString());
    }

    @Test
    void testEquals() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction1 = new MulInstruction("f1", EAX, EBX);
        Instruction instruction2 = new MulInstruction("f1", EAX, EBX);

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsTwo() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        registers.set(ECX, 10);
        Instruction instruction1 = new MulInstruction("f1", EAX, EBX);
        Instruction instruction2 = new MulInstruction("f1", EAX, ECX);

        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsThree() {
        // A MulInstruction instance should not equal its string representation
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction1 = new MulInstruction("f1", EAX, EBX);

        Assertions.assertFalse(instruction1.equals(instruction1.toString()));
    }
}
