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
     * Constructor for an OutInstruction.
     *
     * @param label optional instruction label (nullable)
     * @param source register name for source
     */
    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    /**
     * Alternative constructor using an argument list.
     *
     * @param label optional instruction label (nullable)
     * @param opcode optional operation code (nullable)
     * @param params parameter list with 1 argument [source]
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
