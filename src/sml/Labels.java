package sml;

import java.util.*;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class

/**
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
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		// TODO: Add a check that there are no label duplicates.
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) throws IllegalArgumentException, NoSuchElementException {
		// TODO: Where can NullPointerException be thrown here?
		// ANSWER: This can occur either when the label argument is null,
		//         or when the key does not exist within the HashMap.
		//         Both can occur in a `jnz` instruction.
		if (label == null) throw new IllegalArgumentException("A label is required.");
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

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
