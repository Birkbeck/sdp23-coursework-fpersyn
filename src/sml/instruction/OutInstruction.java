package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * Constructor: alternative taking a label, opcode and list of String arguments.
     *
     * @param label optional label (can be null)
     * @param opcode optional operation code (can be null)
     * @param params params list with 1 argument [source]
     */
    public OutInstruction(String label, String opcode, List<String> params) {
        super(label, OP_CODE, null);
        this.source = Registers.Register.valueOf(params.get(0));
        if (params.size() != 1) throw new IllegalArgumentException("`params` list must be of size 1.");
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
