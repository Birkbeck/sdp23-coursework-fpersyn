package sml;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Factory class responsible for producing Instruction objects.
 */
@Component("instructionFactory")
public class InstructionFactory {

    /**
     * Create an Instruction object.
     * @param label optional instruction label (nullable)
     * @param opcode operation code
     * @param args list of operation arguments (String)
     * @return instruction object
     *
     * @author Fred Persyn
     */
    public Instruction create(String label, String opcode, List<String> args) {
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

    /**
     * Derive the class name for an opcode
     * @param opcode operation code
     * @return class name (String)
     */
    private String getClassName(String opcode) {
        return opcode.substring(0, 1).toUpperCase() + opcode.substring(1) + "Instruction";
    }
}
