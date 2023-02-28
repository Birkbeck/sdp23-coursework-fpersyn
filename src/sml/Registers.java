package sml;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a register repository.
 *
 * @author Fred Persyn
 */
@Component("registers")
public final class Registers {
    private final Map<Register, Integer> registers = new HashMap<>();

    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI;
    }

    /**
     * Constructor
     */
    public Registers() {
        clear(); // the class is final
    }

    /**
     * Clears the register values.
     */
    public void clear() {
        Stream.of(Register.values()).forEach(key -> registers.put(key, 0));
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    /**
     * Determine if the Registers equal another.
     *
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers other) return registers.equals(other.registers);
        return false;
    }

    /**
     * Compute a hash code for the registers.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    /**
     * Represent the registers as a string
     * in the form "[key = value, , ..., key -> value]"
     *
     * @return the string representation of the labels map
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
