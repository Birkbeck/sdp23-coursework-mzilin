package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

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
			return 0;
			// TODO: FINISH THIS NEXT



//			System.out.println("\n\n\nADDRESS " + m.getLabels().getAddress(label) +
//					"\n\n\n\n");
//			return m.getLabels().getAddress(label);
		}
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + jumpToLabel;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
