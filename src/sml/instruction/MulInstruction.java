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

    /**
     * Constructor for a MulInstruction.
     * @param label optional instruction label (nullable)
     * @param result register name for result
     * @param source register name for source
     */
    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Alternative constructor using an argument list.
     *
     * @param label optional instruction label (nullable)
     * @param opcode optional operation code (nullable)
     * @param params parameter list with 2 arguments [result, source]
     */
    public MulInstruction(String label, String opcode, List<String> params) {
        super(label, OP_CODE, params);
    }

    @Override
    public int operator(int x1, int x2) {
        return x1 * x2;
    }
}
