package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.List;

/** Represents an abstract Mathematical operator.
 *
 * @author Fred Persyn
 */
public abstract class MathInstruction extends Instruction {
    protected final RegisterName result;
    protected final RegisterName source;

    /**
     * Constructor for a MathInstruction.
     * @param label optional label (can be null)
     * @param opcode operation name
     * @param result register1
     * @param source register2
     */
    public MathInstruction(String label, String opcode, RegisterName result, RegisterName source) {
        super(label, opcode);
        this.result = result;
        this.source = source;
    }

    /**
     * Alternative constructor using an argument list.
     *
     * @param label optional instruction label (nullable)
     * @param opcode operation code
     * @param params parameter list with 2 arguments [result, source]
     */
    public MathInstruction(String label, String opcode, List<String> params) {
        super(label, opcode, null);
        this.result = Registers.Register.valueOf(params.get(0));
        this.source = Registers.Register.valueOf(params.get(1));
        if (params.size() != 2) throw new IllegalArgumentException("`params` list must be of size 2.");
    }

    /**
     * The mathematical operator.
     * @param x1 – the first term (int)
     * @param x2 - the second term (int)
     * @return the result of the operation (int)
     */
    protected abstract int operator(int x1, int x2);

    @Override
    public final int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, operator(value1, value2));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public final String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }
}
