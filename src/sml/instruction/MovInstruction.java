package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * Represents a 'mov' instruction class.
 * <p>
 * An instance stores the given value in the register.
 *
 * @author Marius Zilinskas
 */

public class MovInstruction extends Instruction {
	private final RegisterName source;
	private final int value;

	public static final String OP_CODE = "mov";

	public MovInstruction(String label, RegisterName source, int value) {
		super(label, OP_CODE);
		this.source = source;
		this.value = value;
	}

	/**
	 * Stores integer x in register r.
	 *
	 * @param m the machine the instruction runs on
	 * @return NORMAL_PROGRAM_COUNTER_UPDATE
	 */
	@Override
	public int execute(Machine m) {
		m.getRegisters().set(source, value);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof MovInstruction)) return false;
		return Objects.equals(this.toString(), obj.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, opcode, source, value);
	}
}
