package sml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * Represents the machine, the context in which programs run.
 *
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * <p>
 * Nota bene: This class is implemented as a Spring component to exploit
 * dependency injection (through auto-wiring) and it also causes the class
 * to become a spring-managed singleton by default. I've added an extra
 * annotation for clarity.
 */
@Component("machine")
@Scope("singleton")
public final class Machine {

	@Autowired
	private final Labels labels = null;

	private final List<Instruction> program = new ArrayList<>();

	@Autowired
	private final Registers registers = null;

	// The program counter; it contains the index (in program)
	// of the next instruction to be executed.
	private int programCounter = 0;

	/**
	 * Execute the program in program, beginning at instruction 0.
	 * Precondition: the program and its labels have been stored properly.
	 */
	public void execute() {
		programCounter = 0;
		registers.clear();
		while (programCounter < program.size()) {
			Instruction ins = program.get(programCounter);
			int programCounterUpdate = ins.execute(this);
			programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
				? programCounter + 1
				: programCounterUpdate;
		}
	}

	/**
	 * Getter for lables.
	 */
	public Labels getLabels() {
		return this.labels;
	}

	/**
	 * Getter for program.
	 */
	public List<Instruction> getProgram() {
		return this.program;
	}

	/**
	 * Getter for registers.
	 */
	public Registers getRegisters() {
		return this.registers;
	}


	/**
	 * String representation of the program under execution.
	 *
	 * @return pretty formatted version of the code.
	 */
	@Override
	public String toString() {
		return program.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Determine if the Machine equals another.
	 *
	 * @param o Machine object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Machine other) {
			return Objects.equals(this.labels, other.labels)
					&& Objects.equals(this.program, other.program)
					&& Objects.equals(this.registers, other.registers)
					&& this.programCounter == other.programCounter;
		}
		return false;
	}

	/**
	 * Compute a hash code for the instruction.
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
