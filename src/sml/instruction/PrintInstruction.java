package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

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
		if (this == obj) return true;
		if (!(obj instanceof PrintInstruction)) return false;
		return Objects.equals(this.toString(), obj.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, opcode, source);
	}
}
