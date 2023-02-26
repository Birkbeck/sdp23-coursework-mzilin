package sml;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * An instance reads the program and translates it into an internal form.
 *
 * @author Marius Zilinskas
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    /**
     * Dependency injection of instruction factory
     */
    private final BeanFactory beanFactory = new ClassPathXmlApplicationContext("/beans.xml");
    private final InstructionFactory factory = (InstructionFactory) beanFactory.getBean("ins-factory");

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
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

        String[] values = scanLine();
        String opcode = values[0];
        values[0] = label;

        // TODO: Next, use dependency injection to allow this machine class
        //       to work with different sets of opcodes (different CPUs)
        return factory.createInstruction(opcode, values);
    }


    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();
        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }
        return line;
    }

    /**
     * Scans the entire line and
     * @return an array of 2 or 3 elements
     */
    private String[] scanLine() {
        String opcode = scan();
        String value1 = scan();
        int lineLengthBefore = line.length();
        String value2 = scan();
        int lineLengthAfter = line.length();

        // When value2 is missing like in 'out' command, scan() returns the same value twice!
        // Without changing scan() method, the workaround would be to check
        // if the line length before scanning value2 is the same as after the scan
        // If the line lengths are the same -> value2 was missing
        return lineLengthBefore == lineLengthAfter
                ? new String[]{opcode, value1}
                : new String[]{opcode, value1, value2};
    }
}