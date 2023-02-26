package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/** Represents a `out` Instruction.
 *
 * @author Fred Persyn
 */
public class OutInstruction extends Instruction {
    private final RegisterName source;

    public static final String OP_CODE = "out";

    /**
     * Constructor: a `out` instruction with a label, and a source.
     *
     * @param label optional label (can be null)
     * @param source register to read and print
     */
    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int source_value = m.getRegisters().get(source);
        System.out.println(source_value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }
}
