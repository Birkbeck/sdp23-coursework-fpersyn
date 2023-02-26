package sml;

import java.util.*;
import java.util.stream.Collectors;

/** Represents a wrapper around labels.
 *
 * @author Fred Persyn
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) throws NullPointerException, IllegalArgumentException {
		Objects.requireNonNull(label, "label required.");
		if (labels.containsKey(label)) throw new IllegalArgumentException("label already exists");
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) throws NoSuchElementException {
		// TODO: Where can NullPointerException be thrown here?
		// ANSWER: This can occur either when the label argument is null,
		//         or when the key does not exist within the HashMap.
		//         Both can occur in a `jnz` instruction.
		// Nota bene: Verification of instruction input values is not the
		//            responsibility of this class – have addressed it there.
		if (labels.containsKey(label)) return labels.get(label);
		throw new NoSuchElementException("The label you requested does not exist.");
	}

	/**
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> e.getKey() + " -> " + e.getValue())
				.collect(Collectors.joining(", ", "[", "]"));
	}

	// TODO: Implement equals and hashCode (needed in class Machine).
	// ANSWER: I'm Using same reasoning here as with the Instuction class,
	// 		   relying on toString() for simplicity.
	/**
	 * Determine if the Labels instance equals another.
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Labels other) return this.hashCode() == other.hashCode();
		return false;
	}

	/**
	 * Compute a hash code for labels.
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
