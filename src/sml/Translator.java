package sml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Translates a SML program into an executable program of instructions.
 *
 * <p>
 * The translator of a <b>S</b><b>M</b><b>L</b> program.
 *
 * <p>
 * Nota bene: This class is implemented as a Spring component which
 * makes it a spring-managed singleton by default. I've added an extra
 * annotation for clarity.
 *
 * @author Fred Persyn
 */
@Component("translator")
@Scope("singleton")
public final class Translator {
    @Value("${fileName}")
    private final String fileName = null; // source file of SML code

    private final InstructionFactory factory = InstructionFactory.getInstance();

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    /**
     * Translate a SML file into labels and instructions.
     *
     * @param labels a label repository
     * @param program an instruction repository
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        // clear repositories
        labels.reset();
        program.clear();
        // parse file
        try(Stream<String> stream = Files.lines(Path.of(fileName), StandardCharsets.UTF_8)) {
            stream.map(String::trim)
                  .forEach(s -> {
                      line = s;
                      translateLine(labels, program);
                  });
        }
    }

    /**
     * Translate a SML line into a label and instruction.
     *
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
        String opcode = scan();
        List<String> args = new ArrayList<>();
        Instruction instruct = null;
        try {
            if (opcode.isEmpty()) return null;
            while (line.length() > 0) args.add(scan());
            instruct = factory.create(label, opcode, args);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown instruction: " + opcode);
        }
        return instruct;
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
        Optional<String> word = Arrays.stream(line.split(" "))
                .filter(s -> s.length() >= 1).findFirst();
        if (word.isPresent()) {
            int word_len = word.get().length();
            line = (line.length() - word_len > 1) ? line.substring(word_len + 1) : "";
            return word.get();
        }
        return line;
    }
}
