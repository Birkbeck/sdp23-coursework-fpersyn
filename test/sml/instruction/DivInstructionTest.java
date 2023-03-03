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

public class DivInstructionTest {
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

    @Test
    void testToString() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction = new DivInstruction("f1", EAX, EBX);

        Assertions.assertEquals("f1: div EAX EBX", instruction.toString());
    }

    @Test
    void testEquals() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction1 = new DivInstruction("f1", EAX, EBX);
        Instruction instruction2 = new DivInstruction("f1", EAX, EBX);

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsTwo() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction1 = new DivInstruction("f1", EAX, EBX);
        Instruction instruction2 = new DivInstruction("f2", EAX, EBX);

        Assertions.assertFalse(instruction1.equals(instruction2));
    }

    @Test
    void testEqualsThree() {
        // A DivInstruction instance should not equal its string representation
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction1 = new DivInstruction("f1", EAX, EBX);

        Assertions.assertFalse(instruction1.equals(instruction1.toString()));
    }

    @Test
    void testListConstructor() {
        // Both constructors should create an identical object
        Instruction instruction1 = new DivInstruction("f1", EAX, EBX);
        Instruction instruction2 = new DivInstruction("f1", "add", Arrays.asList("EAX", "EBX"));

        Assertions.assertTrue(instruction1.equals(instruction2));
    }

    @Test
    void testListConstructorThrowsExceptionWhenWrongSize() {
        // longer size
//    List<String> args = Arrays.asList("EAX", "EBX", "etc");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new DivInstruction("f1", "div", Arrays.asList("EAX", "EBX", "etc")));

        // shorter size
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> new DivInstruction("f1", "div", Arrays.asList("EAX")));
    }
}
