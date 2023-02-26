package sml;

/**
 * Represents an abstract instruction.
 *
 * @author Fred Persyn
 */
public abstract class Instruction {
	protected final String label;
	protected final String opcode;

	/**
	 * Constructor: an instruction with a label and an opcode
	 * (opcode must be an operation of the language)
	 *
	 * @param label optional label (can be null)
	 * @param opcode operation name
	 */
	public Instruction(String label, String opcode) {
		this.label = label;
		this.opcode = opcode;
	}

	/**
	 * Get the label of the instruction.
	 *
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Get the opcode of the instruction.
	 *
	 * @return opcode
	 */
	public String getOpcode() {
		return opcode;
	}

	public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	/**
	 * Executes the instruction in the given machine.
	 *
	 * @param machine the machine the instruction runs on
	 * @return the new program counter (for jump instructions)
	 *          or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *          the instruction with the next address is to be executed
	 */
	public abstract int execute(Machine machine);

	/**
	 * Get the label string for the instruction.
	 *
	 * @return label string
	 */
	protected String getLabelString() {
		return (getLabel() == null) ? "" : getLabel() + ": ";
	}

	// TODO: What does abstract in the declaration below mean?
	// ANSWER: abstract here declares a method without an implementation, forcing the subclass to implement it.
	//         `@override` indicates default implementations inherited from `class` are overridden.

	/**
	 * Format the instruction to a String.
	 *
	 * @return instruction as String
	 */
	@Override
	public abstract String toString();

	// TODO: Make sure that subclasses also implement equals and hashCode (needed in class Machine).
	// ANSWER: I chose to implement both equals() and hashCode() here using the toString() methods
	//         which are implemented by the subclasses. It is more elegant and avoids repetition.
	/**
	 * Determine if the instruction equals another.
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) { return this.hashCode() == o.hashCode(); }

	/**
	 * Compute a hash code for the instruction.
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
