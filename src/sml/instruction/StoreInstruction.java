package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author Marius Zilinskas
 */

public class StoreInstruction extends Instruction {
	private final RegisterName source;
	private final int value;

	public static final String OP_CODE = "mov";

	public StoreInstruction(String label, RegisterName source, int value) {
		super(label, OP_CODE);
		this.source = source;
		this.value = value;
	}

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
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
