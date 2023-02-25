package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static sml.Registers.Register;


/**
 * Represents an instruction factory concrete class that returns an instruction
 * based on the provided opcode
 *
 * @author Marius Zilinskas
 */

public class InstructionFactoryImpl implements InstructionFactory {

    private static final InstructionFactory instance = new InstructionFactoryImpl();

    /**
     * Singleton pattern used to return only one instance
     */
    private InstructionFactoryImpl() {}

    @Override
    public InstructionFactory getInstance() {
        return instance;
    }

    @Override
    public Instruction createInstruction(String opcode, String[] params) {
        String namePrefix = opcode.substring(0, 1).toUpperCase() + opcode.substring(1);

        try {
            Class<?> instructionClass = Class.forName("sml.instruction." + namePrefix + "Instruction");
            Constructor<?>[] constructors = instructionClass.getConstructors();

            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();

                if (parameterTypes.length == params.length) {
                    Object[] arguments = new Object[params.length];

                    for (int i = 0; i < params.length; i++) {
                        Class<?> param = parameterTypes[i];
                        if (param.getName().equals("sml.RegisterName")) {
                            arguments[i] = Register.valueOf(params[i]);
                        } else if (param == String.class) {
                            arguments[i] = params[i];
                        } else if (param == int.class) {
                            arguments[i] = Integer.class.getConstructor(String.class).newInstance(params[i]);
                        } else {
                            throw new IllegalArgumentException("Unsupported argument type: " + param.getName());
                        }
                    }
                    return (Instruction) constructor.newInstance(arguments);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            System.out.println("Unknown instruction: " + opcode);
        }

        System.out.println("Unknown instruction: " + opcode);
        return null;
    }
}
