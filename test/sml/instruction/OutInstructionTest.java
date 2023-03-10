package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static sml.Registers.Register.*;

public class OutInstructionTest {
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

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsTwo() {
        registers.set(EAX, 5);
        registers.set(EBX, 10);
        Instruction instruction1 = new OutInstruction("f1", EAX);
        Instruction instruction2 = new OutInstruction("f1", EBX);

        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsThree() {
        // A OutInstruction instance should not equal its string representation
        registers.set(EAX, 5);
        Instruction instruction1 = new OutInstruction("f1", EAX);

        Assertions.assertFalse(instruction1.equals(instruction1.toString()));
    }

    @Test
    void testListConstructor() {
        // Both constructors should create an identical object
        Instruction instruction1 = new OutInstruction("f1", EAX);
        Instruction instruction2 = new OutInstruction("f1", "out", Arrays.asList("EAX"));

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testListConstructorThrowsExceptionWhenWrongSize() {
        // longer size
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new OutInstruction("f1", "out", Arrays.asList("EAX", "etc")));

        // shorter size
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> new OutInstruction("f1", "out", Arrays.asList()));
    }
}
