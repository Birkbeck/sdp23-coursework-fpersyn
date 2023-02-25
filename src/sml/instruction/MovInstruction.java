package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/** Represents a mov Instruction.
 * @author Fred Persyn
 */
public class MovInstruction extends Instruction {
    private final RegisterName destination;
    private final Integer value;
    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName destination, Integer value) {
        super(label, OP_CODE);
        this.destination = destination;
        this.value = value;
    }

    @Override
    public int execute(Machine m) {
        m.getRegisters().set(destination, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + destination + " " + value;
    }
}
