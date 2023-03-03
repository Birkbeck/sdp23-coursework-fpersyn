package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sml.Registers.Register.*;

class AddInstructionTest {
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
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);

    Assertions.assertEquals(11, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidTwo() {
    registers.set(EAX, -5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);

    Assertions.assertEquals(1, machine.getRegisters().get(EAX));
  }

  @Test
  void testToString() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction("f1", EAX, EBX);

    Assertions.assertEquals("f1: add EAX EBX", instruction.toString());
  }

  @Test
  void testEquals() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction1 = new AddInstruction("f1", EAX, EBX);
    Instruction instruction2 = new AddInstruction("f1", EAX, EBX);

    Assertions.assertTrue(instruction1.equals(instruction2));
  }

  @Test
  void testEqualsTwo() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction1 = new AddInstruction("f1", EAX, EBX);
    Instruction instruction2 = new AddInstruction("f2", EAX, EBX);

    Assertions.assertFalse(instruction1.equals(instruction2));
  }

  @Test
  void testEqualsThree() {
    // An AddInstruction instance should not equal its string representation
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction1 = new AddInstruction("f1", EAX, EBX);

    Assertions.assertFalse(instruction1.equals(instruction1.toString()));
  }

  @Test
  void testListConstructor() {
    // Both constructors should create an identical object
    Instruction instruction1 = new AddInstruction("f1", EAX, EBX);
    Instruction instruction2 = new AddInstruction("f1", "add", Arrays.asList("EAX", "EBX"));

    Assertions.assertTrue(instruction1.equals(instruction2));
  }

  @Test
  void testListConstructorThrowsExceptionWhenWrongSize() {
    // longer size
//    List<String> args = Arrays.asList("EAX", "EBX", "etc");
    Assertions.assertThrows(IllegalArgumentException.class,
            () -> new AddInstruction("f1", "add", Arrays.asList("EAX", "EBX", "etc")));

    // shorter size
    Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> new AddInstruction("f1", "add", Arrays.asList("EAX")));
  }
}
