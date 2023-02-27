package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

// TODO –update docstring
/**
 * Translates a SML program into an executable program of instructions.
 * <p>
 * The translator of a <b>S</b><b>M</b><b>L</b> program.
 *
 * @author Fred Persyn
 */
public final class Translator {

    private final String fileName; // source file of SML code

    private final InstructionFactory factory;

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    /**
     * Constructor
     */
    public Translator(String fileName, InstructionFactory factory) {
        this.fileName =  fileName;
        this.factory = factory;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    /**
     * Translate an SML file into labels and instructions.
     * @param labels a label repository
     * @param program an instruction repository
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        // TODO: Is resetting/clearing the responsibility of Translator?
        // TODO: Why not jut pass in the entire Machine context?
        labels.reset();
        program.clear();
        // parse file line-by-line
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                translateLine(labels, program);
            }
        }
    }

    /**
     * Translate a SML line into a label and instruction.
     * @param labels a label repository
     * @param program an instruction repository
     */
    private void translateLine(Labels labels, List<Instruction> program) {
        String label = getLabel();
        Instruction instruction = getInstruction(label);
        if (instruction != null) {
            if (label != null)
                labels.addLabel(label, program.size());
            program.add(instruction);
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        // TODO – remove explicit calls (switch statement) with reflection API
        // * You can call scan() 3 times to get the required args: op, arg1, arg2
        // * `op` mappings can be maintained in a factory class
        // TODO: Next, use dependency injection to allow this machine class
        //       to work with different sets of opcodes (different CPUs)
        String opcode = scan();
        List<String> args = new ArrayList<>();
        try {
            if (opcode.isEmpty()) return null;
            while (line.length() > 0) args.add(scan());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown instruction: " + opcode);
        }
        return factory.createInstruction(label, opcode, args);
    }

    /**
     * Return the label for a line.
     */
    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();
        Optional<String> word = Arrays.stream(line.split(" "))
                .filter(s -> s.length() >= 1).findFirst();
        if (word.isPresent()) {
            int word_len = word.get().length();
            line = line.substring((line.length() - word_len > 1) ? word_len + 1 : 1);
            return word.get();
        }
        return line;
    }
}
