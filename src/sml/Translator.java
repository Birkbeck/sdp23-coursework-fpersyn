package sml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

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
    public void readAndTranslate(Labels labels, List<Instruction> program) {
        // TODO: Is resetting/clearing the reponsibility of Translator?
        // TODO: Why not jut pass in the entire Machine context?
        labels.reset();
        program.clear();
        translateFile(labels, program);
    }

    /**
     * Process an SML file line-by-line.
     * @param labels a label repository
     * @param program an instruction repository
     */
    private void translateFile(Labels labels, List<Instruction> program) {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            // process line-by-line
            while (sc.hasNextLine()) {
                line = sc.nextLine();  // TODO: Makes no sense to store this in Class – why not just pass it?
                translateLine(labels, program);  // TODO: translateLine(line, labels, program)
            }
        } catch(IOException e) {
            ; // TODO: Do something
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
        // TODO: Mixed responsibilities => transformation & loading
        String opcode = scan();
        List<String> args = new ArrayList<>();
        try {
            if (opcode.isEmpty()) return null;
            while (line.length() > 0) args.add(scan());
        } catch (Exception e) {
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
        Optional<String> word = Stream.of(line.trim().split("\\s+")).findFirst();
        if (word.isPresent()) {
            line = line.substring(word.get().length() + 1);
            return word.get();
        }
        return "";
    }
}
