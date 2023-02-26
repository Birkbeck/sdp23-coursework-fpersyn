package sml.instruction;

import sml.RegisterName;

/** Represents a subtraction Instruction.
 *
 * @author Fred Persyn
 */
public class SubInstruction extends MathInstruction {
    public static final String OP_CODE = "sub";

    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int operator(int x1, int x2) {
        return x1 - x2;
    }
}
