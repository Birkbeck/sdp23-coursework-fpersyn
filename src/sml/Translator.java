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
     * Translates the SML program (file) into a collection of `labels`
     * and executable instructions (`program`).
     * @param labels – label storage
     * @param program – instruction storage
     * @throws IOException
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        // TODO: Mixed responsibilities => scanning, processing & storage
        try {
            // clear storage
            labels.reset();
            program.clear();
            // Scan and process file line-by-line
            var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();
                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        } catch (Exception e) {
            // TODO: Populate with response.
            ; // do nothing
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
