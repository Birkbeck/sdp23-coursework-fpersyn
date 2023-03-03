package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.List;

/** Represents a `mov` Instruction.
 *
 * @author Fred Persyn
 */
public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final Integer value;
    public static final String OP_CODE = "mov";

    /**
     * Constructor for a MovInstruction.
     *
     * @param label optional instruction label (nullable)
     * @param result register name for result
     * @param value the integer to store
     */
    public MovInstruction(String label, RegisterName result, Integer value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    /**
     * Alternative constructor using an argument list.
     *
     * @param label optional instruction label (nullable)
     * @param opcode optional operation code (nullable)
     * @param params parameter list with 2 arguments [result, value]
     */
    public MovInstruction(String label, String opcode, List<String> params) {
        super(label, opcode, null);
        this.result = Registers.Register.valueOf(params.get(0));
        this.value = Integer.parseInt(params.get(1));
        if (params.size() != 2) throw new IllegalArgumentException("`params` list must be of size 2.");
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
