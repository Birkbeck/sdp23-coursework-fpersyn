package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/** Represents a `jnz` Instruction.
 *
 * @author Fred Persyn
 */
public class JnzInstruction extends Instruction {
    private final RegisterName source;
    private final String targetLabel;
    public static final String OP_CODE = "jnz";

    /**
     * Constructor: a `jnz` instruction with a label, a source a targetLabel.
     *
     * @param label optional label (can be null)
     * @param source register to validate
     * @param targetLabel the target statement to execute
     */
    public JnzInstruction(String label, RegisterName source, String targetLabel) throws IllegalArgumentException {
        super(label, OP_CODE);
        if (source == null) throw new IllegalArgumentException("A source is required.");
        if (targetLabel == null) throw new IllegalArgumentException("A label is required.");
        this.source = source;
        this.targetLabel = targetLabel;
    }

    @Override
    public int execute(Machine m) {
        int value = m.getRegisters().get(source);
        if (value == 0) return NORMAL_PROGRAM_COUNTER_UPDATE;
        return m.getLabels().getAddress(targetLabel);
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source + " " + targetLabel;
    }
}
