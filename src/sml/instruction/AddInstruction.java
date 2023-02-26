package sml.instruction;

import sml.RegisterName;

/** Represents an addition Instruction.
 *
 * @author Fred Persyn
 */
public class AddInstruction extends MathInstruction {
	public static final String OP_CODE = "add";

	public AddInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE, result, source);
	}

	@Override
	public int operator(int x1, int x2) {
		return x1 + x2;
	}
}
