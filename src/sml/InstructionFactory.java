package sml;


/**
 * Represents an instruction factory interface
 *
 * @author Marius Zilinskas
 */
public interface InstructionFactory {
    InstructionFactory getInstance();
    Instruction createInstruction(String opcode, String[] params);
}
