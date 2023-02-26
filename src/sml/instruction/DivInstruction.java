package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/** Represents a division Instruction.
 *
 * @author Fred Persyn
 */
public class DivInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "div";

    /**
     * Constructor: a `div` instruction with a label, a result and source.
     *
     * @param label optional label (can be null)
     * @param result register1
     * @param source register2
     */
    public DivInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int nominator = m.getRegisters().get(result);
        int denominator = m.getRegisters().get(source);
        m.getRegisters().set(result, nominator / denominator);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }
}
