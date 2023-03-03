package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.List;
import java.util.Objects;

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
    public JnzInstruction(String label, RegisterName source, String targetLabel) throws NullPointerException {
        super(label, OP_CODE);
        Objects.requireNonNull(source, "source required.");
        Objects.requireNonNull(targetLabel, "targetLabel required.");
        this.source = source;
        this.targetLabel = targetLabel;
    }

    /**
     * Constructor: alternative taking a label, opcode and list of String arguments.
     *
     * @param label optional label (can be null)
     * @param opcode optional operation code (can be null)
     * @param params params list with 2 arguments [source, targetLabel]
     */
    public JnzInstruction(String label, String opcode, List<String> params) {
        super(label, OP_CODE, null);
        Objects.requireNonNull(params.get(0), "source required.");
        Objects.requireNonNull(params.get(1), "targetLabel required.");
        this.source = Registers.Register.valueOf(params.get(0));
        this.targetLabel = params.get(1);
        if (params.size() != 2) throw new IllegalArgumentException("`params` list must be of size 2.");
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
