package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/** Represents a `mov` Instruction.
 *
 * @author Fred Persyn
 */
public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final Integer value;
    public static final String OP_CODE = "mov";

    /**
     * Constructor: a `mov` instruction with a label, a result and value.
     *
     * @param label optional label (can be null)
     * @param result the register to set
     * @param value the int to store
     */
    public MovInstruction(String label, RegisterName result, Integer value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + value;
    }
}
