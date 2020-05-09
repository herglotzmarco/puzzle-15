package de.herglotz.puzzle.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Permutation {

	public static final byte EMPTY_SPACE = 16;

	private final byte[] list;
	private final int steps;

	public static Permutation of(Integer... list) {
		return new Permutation(intToByte(list), 0);
	}

	private static byte[] intToByte(Integer[] list) {
		byte[] result = new byte[list.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = list[i].byteValue();
		}
		return result;
	}

	public Permutation(byte[] list, int steps) {
		this.list = list;
		this.steps = steps;
	}

	public List<Permutation> performAllPossibleMoves() {
		List<Permutation> result = new ArrayList<>();
		int emptySpaceIndex = findIndexOf(EMPTY_SPACE);

		swapIfPossible(emptySpaceIndex, emptySpaceIndex - 1).ifPresent(result::add);
		swapIfPossible(emptySpaceIndex, emptySpaceIndex + 1).ifPresent(result::add);
		swapIfPossible(emptySpaceIndex, emptySpaceIndex - 4).ifPresent(result::add);
		swapIfPossible(emptySpaceIndex, emptySpaceIndex + 4).ifPresent(result::add);

		return result;
	}

	private int findIndexOf(byte value) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == value) {
				return i;
			}
		}
		return -1;
	}

	private Optional<Permutation> swapIfPossible(int emptySpaceIndex, int swapIndex) {
		if (swapIndex >= 0 && swapIndex < list.length) {
			return Optional.of(swap(emptySpaceIndex, swapIndex));
		}
		return Optional.empty();
	}

	private Permutation swap(int emptySpaceIndex, int swapIndex) {
		byte[] copy = Arrays.copyOf(list, list.length);
		byte value1 = copy[emptySpaceIndex];
		byte value2 = copy[swapIndex];
		copy[emptySpaceIndex] = value2;
		copy[swapIndex] = value1;
		return new Permutation(copy, steps + 1);
	}

	public int steps() {
		return this.steps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(list);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Permutation other = (Permutation) obj;
		return Arrays.equals(list, other.list);
	}

	@Override
	public String toString() {
		return "Permutation " + list;
	}

}
