package sml;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Component;

// TODO: Add docstring
/**
 *
 */
@Component("instructionFactory")
public class InstructionFactory {
    public Instruction createInstruction(String label, String opcode, List<String> args) {
        String labelAlt = (label == null) ? "F1" : label;
        Instruction instruction = null;
        try {
            String className = "sml.instruction." + this.getClassName(opcode);
            Class<? extends Instruction> classObject = Class.forName(className).asSubclass(Instruction.class);
            Constructor<? extends Instruction> constructor = classObject.getDeclaredConstructor(String.class, String.class, List.class);
            instruction = constructor.newInstance(labelAlt, opcode, args);
        } catch (ClassNotFoundException |
                 IllegalAccessException |
                 InstantiationException |
                 NoSuchMethodException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            System.out.println(e.getCause().getClass());
            e.getStackTrace();
            throw new RuntimeException(e);
        }
        return instruction;
    }

    private String getClassName(String opcode) {
        return opcode.substring(0, 1).toUpperCase() + opcode.substring(1) + "Instruction";
    }
}
