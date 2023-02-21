package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * @author Marius Zilinskas
 */

public class JumpInstruction extends Instruction {
	private final RegisterName source;
	private final String jumpToLabel;

	public static final String OP_CODE = "jnz";

	public JumpInstruction(String label, RegisterName source, String jumpToLabel) {
		super(label, OP_CODE);
		this.source = source;
		this.jumpToLabel = jumpToLabel;
	}

	@Override
	public int execute(Machine m) {
		int value = m.getRegisters().get(source);
		if (value != 0) {
			return m.getLabels().getAddress(jumpToLabel);
		}
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + jumpToLabel;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof JumpInstruction)) return false;
		return Objects.equals(this.toString(), obj.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, opcode, source, jumpToLabel);
	}
}
