package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.util.Arrays;

import static sml.Registers.Register.*;

public class MovInstructionTest {
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
        registers.set(EAX, 0);
        Instruction instruction = new MovInstruction(null, EAX, 5);
        instruction.execute(machine);

        Assertions.assertEquals(5, machine.getRegisters().get(EAX));
    }

    @Test
    void testToString() {
        registers.set(EAX, 0);
        Instruction instruction = new MovInstruction("f1", EAX, 5);

        Assertions.assertEquals("f1: mov EAX 5", instruction.toString());
    }

    @Test
    void testEquals() {
        registers.set(EAX, 0);
        Instruction instruction1 = new MovInstruction("f1", EAX, 5);
        Instruction instruction2 = new MovInstruction("f1", EAX, 5);

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsTwo() {
        registers.set(EAX, 0);
        Instruction instruction1 = new MovInstruction("f1", EAX, 5);
        Instruction instruction2 = new MovInstruction("f1", EAX, 10);

        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsThree() {
        // A MovInstruction instance should not equal its string representation
        registers.set(EAX, 0);
        Instruction instruction1 = new MovInstruction("f1", EAX, 5);

        Assertions.assertFalse(instruction1.equals(instruction1.toString()));
    }

    @Test
    void testListConstructor() {
        // Both constructors should create an identical object
        Instruction instruction1 = new MovInstruction("f1", EAX, 5);
        Instruction instruction2 = new MovInstruction("f1", "mov", Arrays.asList("EAX", "5"));

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testListConstructorThrowsExceptionWhenWrongSize() {
        // longer size
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new MovInstruction("f1", "mov", Arrays.asList("EAX", "5", "etc")));

        // shorter size
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> new MovInstruction("f1", "mov", Arrays.asList("EAX")));
    }
}
