package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * Represents an 'add' instruction class.
 * <p>
 * An instance adds the contents of two registers and stores the
 * result in the first one.
 *
 * @author Marius Zilinskas
 */

public class AddInstruction extends Instruction {
	private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "add";

	public AddInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/**
	 * Adds the contents of registers r and s and stores the result in register r.
	 *
	 * @param m the machine the instruction runs on
	 * @return NORMAL_PROGRAM_COUNTER_UPDATE
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 + value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof AddInstruction)) return false;
		return Objects.equals(this.toString(), obj.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, opcode, result, source);
	}
}
