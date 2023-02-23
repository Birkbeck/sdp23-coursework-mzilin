package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * Represents a 'jnz' instruction class.
 * <p>
 * An instance checks if the contents of the register is not zero, then returns
 * an address for the next instruction.
 *
 * @author Marius Zilinskas
 */

public class JumpInstruction extends Instruction {
	private final RegisterName source;
	private final String address;

	public static final String OP_CODE = "jnz";

	public JumpInstruction(String label, RegisterName source, String address) {
		super(label, OP_CODE);
		this.source = source;
		this.address = address;
	}

	/**
	 * Checks if the contents of register s is not zero, then returns an address
	 * for the next instruction.
	 *
	 * @param m the machine the instruction runs on
	 * @return NORMAL_PROGRAM_COUNTER_UPDATE if source register content is zero,
	 * 			otherwise returns an address for the next instruction
	 */
	@Override
	public int execute(Machine m) {
		int value = m.getRegisters().get(source);
		if (value != 0) {
			return m.getLabels().getAddress(address);
		}
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof JumpInstruction)) return false;
		return Objects.equals(this.toString(), obj.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, opcode, source, address);
	}
}
