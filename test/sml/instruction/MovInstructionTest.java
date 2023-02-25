package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static sml.Registers.Register.EAX;

class MovInstructionTest {
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
    registers.set(EAX, 5);
    Instruction instruction = new MovInstruction(null, EAX, 6);
    instruction.execute(machine);
    Assertions.assertEquals(6, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidTwo() {
    registers.set(EAX, -5);
    Instruction instruction = new MovInstruction(null, EAX, 9);
    instruction.execute(machine);
    Assertions.assertEquals(9, machine.getRegisters().get(EAX));
  }

  @Test
  void equals_areEqual() {
    registers.set(EAX, 10);
    Instruction instruction = new MovInstruction("A1", EAX, 6);
    Instruction instruction2 = new MovInstruction("A1", EAX, 6);
    assertEquals(instruction, instruction2);
  }

  @Test
  void equals_areNotEqual() {
    registers.set(EAX, 10);
    Instruction instruction = new MovInstruction(null, EAX, 7);
    Instruction instruction2 = new MovInstruction("B4", EAX, 8);
    assertNotEquals(instruction, instruction2);
  }
}