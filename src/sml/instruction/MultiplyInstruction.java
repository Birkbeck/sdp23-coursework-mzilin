package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * Represents a 'mul' instruction class.
 * <p>
 * An instance multiplies the contents of one register by another and stores the
 * result in the first one.
 *
 * @author Marius Zilinskas
 */

public class MultiplyInstruction extends Instruction {
	private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "mul";

	public MultiplyInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/**
	 * Multiplies the contents of registers r and s and store the result in register r.
	 *
	 * @param m the machine the instruction runs on
	 * @return NORMAL_PROGRAM_COUNTER_UPDATE
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 * value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof MultiplyInstruction)) return false;
		return Objects.equals(this.toString(), obj.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, opcode, result, source);
	}
}
