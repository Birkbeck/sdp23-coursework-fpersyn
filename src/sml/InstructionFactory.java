package sml;
import java.util.List;

import org.springframework.stereotype.Component;
import sml.instruction.*;
import static sml.Registers.Register;

// TODO: Add docstring
/**
 *
 */
@Component("instructionFactory")
public class InstructionFactory {
    public Instruction createInstruction(String label, String opcode, List<String> args) {
        // TODO – remove explicit calls (switch statement) with reflection API
        Instruction instruct = null;
        switch (opcode) {
            case AddInstruction.OP_CODE -> {
                Register r = Registers.Register.valueOf(args.get(0));
                Register s = Registers.Register.valueOf(args.get(1));
                instruct = new AddInstruction(label, r, s);
            }
            case SubInstruction.OP_CODE -> {
                Register r = Registers.Register.valueOf(args.get(0));
                Register s = Registers.Register.valueOf(args.get(1));
                instruct = new SubInstruction(label, r, s);
            }
            case MulInstruction.OP_CODE -> {
                Register r = Registers.Register.valueOf(args.get(0));
                Register s = Registers.Register.valueOf(args.get(1));
                instruct = new MulInstruction(label, r, s);
            }
            case DivInstruction.OP_CODE -> {
                Register r = Registers.Register.valueOf(args.get(0));
                Register s = Registers.Register.valueOf(args.get(1));
                instruct = new DivInstruction(label, r, s);
            }
            case OutInstruction.OP_CODE -> {
                Register s = Registers.Register.valueOf(args.get(0));
                instruct = new OutInstruction(label, s);
            }
            case MovInstruction.OP_CODE -> {
                Register r = Registers.Register.valueOf(args.get(0));
                Integer x = Integer.valueOf(args.get(1));
                instruct = new MovInstruction(label, r, x);
            }
            case JnzInstruction.OP_CODE -> {
                Register s = Registers.Register.valueOf(args.get(0));
                String L = args.get(1);
                instruct = new JnzInstruction(label, s, L);
            }
        }
        return instruct;
    }
}
