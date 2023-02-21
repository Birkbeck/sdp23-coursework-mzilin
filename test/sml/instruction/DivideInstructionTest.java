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
import static sml.Registers.Register.EBX;

class DivideInstructionTest {
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
    registers.set(EAX, 15);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(5, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidTwo() {
    registers.set(EAX, -15);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(-5, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidThree() {
    registers.set(EAX, 10);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(3, machine.getRegisters().get(EAX));
  }

  @Test
  void equals_areEqual() {
    registers.set(EAX, 10);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction("A1", EAX, EBX);
    Instruction instruction2 = new DivideInstruction("A1", EAX, EBX);
    assertEquals(instruction, instruction2);
  }

  @Test
  void equals_areNotEqual() {
    registers.set(EAX, 10);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction(null, EAX, EBX);
    Instruction instruction2 = new DivideInstruction("B4", EAX, EBX);
    assertNotEquals(instruction, instruction2);
  }
}