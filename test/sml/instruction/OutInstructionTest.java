package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static sml.Registers.Register.EAX;

class OutInstructionTest {
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
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    registers.set(EAX, 5);
    Instruction instruction = new OutInstruction(null, EAX);
    instruction.execute(machine);
    String output = outputStream.toString().trim();
    assertEquals(String.valueOf(machine.getRegisters().get(EAX)), output);
  }

  @Test
  void executeValidTwo() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    registers.set(EAX, -5);
    Instruction instruction = new OutInstruction(null, EAX);
    instruction.execute(machine);
    String output = outputStream.toString().trim();
    assertEquals(String.valueOf(machine.getRegisters().get(EAX)), output);
  }

  @Test
  void equals_areEqual() {
    registers.set(EAX, 10);
    Instruction instruction = new OutInstruction("A1", EAX);
    Instruction instruction2 = new OutInstruction("A1", EAX);
    assertEquals(instruction, instruction2);
  }

  @Test
  void equals_areNotEqual() {
    registers.set(EAX, 10);
    Instruction instruction = new OutInstruction("A1", EAX);
    Instruction instruction2 = new OutInstruction(null, EAX);
    assertNotEquals(instruction, instruction2);
  }
}