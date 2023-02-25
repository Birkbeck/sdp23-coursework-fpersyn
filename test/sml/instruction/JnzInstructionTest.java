package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;
import static sml.Registers.Register.*;

public class JnzInstructionTest {
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
        // initialise registers
        registers.set(EAX, 0);
        registers.set(EBX, 0);
        registers.set(ECX, 0);
        // add labels – normally performed by translator
        machine.getLabels().addLabel("f1", 0);
        // series of instructions
        Instruction instruction1 = new MovInstruction("f1", EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EBX, 1);
        Instruction instruction3 = new JnzInstruction(null, ECX, "f1");
        // execute
        instruction1.execute(machine);
        instruction2.execute(machine);
        Assertions.assertEquals(NORMAL_PROGRAM_COUNTER_UPDATE, instruction3.execute(machine));
    }

    @Test
    void executeValidTwo() {
        // initialise registers
        registers.set(EAX, 0);
        registers.set(EBX, 0);
        registers.set(ECX, 0);
        // add labels – normally performed by translator
        machine.getLabels().addLabel("f1", 0);
        // series of instructions
        Instruction instruction1 = new MovInstruction("f1", EAX, 1);
        Instruction instruction2 = new MovInstruction(null, EBX, 1);
        Instruction instruction3 = new MovInstruction(null, ECX, 1);
        Instruction instruction4 = new JnzInstruction(null, ECX, "f1");
        // execute
        instruction1.execute(machine);
        instruction2.execute(machine);
        instruction3.execute(machine);
        Assertions.assertEquals(0, instruction4.execute(machine));
    }
}