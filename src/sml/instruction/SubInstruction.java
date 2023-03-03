package sml.instruction;

import sml.RegisterName;
import sml.Registers;

import java.util.Arrays;
import java.util.List;

/** Represents a subtraction Instruction.
 *
 * @author Fred Persyn
 */
public class SubInstruction extends MathInstruction {
    public static final String OP_CODE = "sub";

    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    /**
     * Constructor: alternative taking a label, opcode and list of String arguments.
     *
     * @param label optional label (can be null)
     * @param opcode optional operation code (can be null)
     * @param params params list with 2 arguments [result, source]
     */
    public SubInstruction(String label, String opcode, List<String> params) {
        super(label, OP_CODE, params);
    }

    @Override
    public int operator(int x1, int x2) {
        return x1 - x2;
    }
}
