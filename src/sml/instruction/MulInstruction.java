package sml.instruction;

import sml.RegisterName;

import java.util.Arrays;
import java.util.List;

/** Represents a multiplication Instruction.
 *
 * @author Fred Persyn
 */
public class MulInstruction extends MathInstruction {
    public static final String OP_CODE = "mul";

    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Constructor: alternative taking a label, opcode and list of String arguments.
     *
     * @param label optional label (can be null)
     * @param opcode optional operation code (can be null)
     * @param params params list with 2 arguments [result, source]
     */
    public MulInstruction(String label, String opcode, List<String> params) {
        super(label, OP_CODE, params);
    }

    @Override
    public int operator(int x1, int x2) {
        return x1 * x2;
    }
}
