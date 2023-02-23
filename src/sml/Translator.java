package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static sml.Registers.Register;

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

        String opcode = scan();
        String[] values = scanLine();

        switch (opcode) {
            case AddInstruction.OP_CODE -> {
                return new AddInstruction(label, Register.valueOf(values[0]), Register.valueOf(values[1]));
            }
            case SubInstruction.OP_CODE -> {
                return new SubInstruction(label, Register.valueOf(values[0]), Register.valueOf(values[1]));
            }
            case MulInstruction.OP_CODE -> {
                return new MulInstruction(label, Register.valueOf(values[0]), Register.valueOf(values[1]));
            }
            case DivInstruction.OP_CODE -> {
                return new DivInstruction(label, Register.valueOf(values[0]), Register.valueOf(values[1]));
            }
            case OutInstruction.OP_CODE -> {
                return new OutInstruction(label, Register.valueOf(values[0]));
            }
            case MovInstruction.OP_CODE -> {
                return new MovInstruction(label, Register.valueOf(values[0]), Integer.parseInt(values[1]));
            }
            case JnzInstruction.OP_CODE -> {
                return new JnzInstruction(label, Register.valueOf(values[0]), values[1]);
            }

            // TODO: Then, replace the switch by using the Reflection API

            // TODO: Next, use dependency injection to allow this machine class
            //       to work with different sets of opcodes (different CPUs)

            default -> {
                System.out.println("Unknown instruction: " + opcode);
            }
        }
        return null;
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
     * Scans the remaining of the line after the label
     *
     * @return an array of values, can be either 1 or 2
     */
    private String[] scanLine() {
        String value1 = scan();
        String value2 = scan();

        return value2 == null
                ? new String[]{value1}
                : new String[]{value1, value2};
    }
}