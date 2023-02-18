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
import static sml.Registers.Register.EAX;

class PrintInstructionTest {
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
    Instruction instruction = new PrintInstruction(null, EAX);
    instruction.execute(machine);
    String output = outputStream.toString().trim();
    assertEquals(String.valueOf(machine.getRegisters().get(EAX)), output);
  }

  @Test
  void executeValidTwo() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    registers.set(EAX, -5);
    Instruction instruction = new PrintInstruction(null, EAX);
    instruction.execute(machine);
    String output = outputStream.toString().trim();
    assertEquals(String.valueOf(machine.getRegisters().get(EAX)), output);
  }
}