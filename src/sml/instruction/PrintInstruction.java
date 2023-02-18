package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author Marius Zilinskas
 */

public class PrintInstruction extends Instruction {
	private final RegisterName source;

	public static final String OP_CODE = "out";

	public PrintInstruction(String label, RegisterName source) {
		super(label, OP_CODE);
		this.source = source;
	}

	@Override
	public int execute(Machine m) {
		int value = m.getRegisters().get(source);
		System.out.println(value);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source;
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
