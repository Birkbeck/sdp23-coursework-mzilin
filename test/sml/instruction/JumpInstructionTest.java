package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

class JumpInstructionTest {
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
    //...
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
  }

  @Test
  void execute_sourceIsNotZero() {
    String label = "a1";
    int address = 0;
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction add = new AddInstruction(label, EAX, EBX);
    add.execute(machine);
    machine.getLabels().addLabel(label, address);
    Instruction jump = new JumpInstruction(null, EAX, label);
    assertEquals(address, jump.execute(machine));
  }

  @Test
  void execute_sourceEqualsZero() {
    String label = "A1";
    int address = 0;
    registers.set(EAX, 5);
    registers.set(EBX, -5);
    Instruction add = new AddInstruction(label, EAX, EBX);
    add.execute(machine);
    machine.getLabels().addLabel(label, address);
    Instruction jump = new JumpInstruction(null, EAX, label);
    assertEquals(-1, jump.execute(machine));
  }

  @Test
  void executeNullLabel_throwsException() {
    registers.set(EAX, 5);
    Instruction jump = new JumpInstruction(null, EAX, null);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jump.execute(machine));
    assertEquals("Label cannot be null", exception.getMessage());
  }

  @Test
  void executeLabelNotFound_throwsException() {
    registers.set(EAX, 5);
    Instruction jump = new JumpInstruction(null, EAX, "L1");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jump.execute(machine));
    assertEquals("Label 'L1' doesn't exist", exception.getMessage());
  }
}