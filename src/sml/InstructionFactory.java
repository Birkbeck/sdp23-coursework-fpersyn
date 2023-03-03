package sml;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Factory class responsible for producing Instruction objects.
 *
 * <p>
 * Implemented as a singleton for the purpose of this coursework.
 *
 * @author Fred Persyn
 */
public class InstructionFactory {

    /**
     * Constructor - private hence forces class creation to go through getInstance().
     */
    private InstructionFactory() {}

    /**
     * Inner class to create and store the singleton instance (constant).
     */
    private static class SingletonHolder {
        private static final InstructionFactory INSTANCE = new InstructionFactory();
    }

    /**
     * Getter for the InstructionFactory instance.
     */
    public static InstructionFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

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
