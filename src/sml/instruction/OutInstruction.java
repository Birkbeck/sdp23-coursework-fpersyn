package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/** Represents an out Instruction.
 * @author Fred Persyn
 */
public class OutInstruction extends Instruction {
    private final RegisterName result;

    public static final String OP_CODE = "out";

    public OutInstruction(String label, RegisterName result) {
        super(label, OP_CODE);
        this.result = result;
    }

    @Override
    public int execute(Machine m) {
        int value = m.getRegisters().get(result);
        System.out.println(value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result;
    }
}
