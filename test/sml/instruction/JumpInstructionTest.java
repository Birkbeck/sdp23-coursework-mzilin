package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Labels;
import sml.Machine;
import sml.Registers;

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
  void executeValid() {
    String label = "a1";
    int address = 0;
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction add = new AddInstruction(label, EAX, EBX);
    add.execute(machine);
    machine.getLabels().addLabel(label, address);
    Instruction jump = new JumpInstruction(null, EAX, label);
    Assertions.assertEquals(jump.execute(machine), address);
  }

  @Test
  void executeNotValid() {
    String label = "a1";
    int address = 0;
    registers.set(EAX, 5);
    registers.set(EBX, -5);
    Instruction add = new AddInstruction(label, EAX, EBX);
    add.execute(machine);
    machine.getLabels().addLabel(label, address);
    Instruction jump = new JumpInstruction(null, EAX, label);
    Assertions.assertEquals(jump.execute(machine), -1);
  }
}