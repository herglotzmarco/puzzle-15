package de.herglotz.puzzle.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Permutation {

	private final List<Integer> list;
	private final int steps;

	public static Permutation of(Integer... list) {
		return new Permutation(list, 0);
	}

	public Permutation(Integer[] list, int steps) {
		this(Arrays.asList(list), steps);
	}

	public Permutation(List<Integer> list, int steps) {
		this.list = list;
		this.steps = steps;
	}

	public List<Permutation> performAllPossibleMoves() {
		List<Permutation> result = new ArrayList<>();
		int emptySpaceIndex = list.indexOf(16);

		swapIfPossible(emptySpaceIndex, emptySpaceIndex - 1).ifPresent(result::add);
		swapIfPossible(emptySpaceIndex, emptySpaceIndex + 1).ifPresent(result::add);
		swapIfPossible(emptySpaceIndex, emptySpaceIndex - 4).ifPresent(result::add);
		swapIfPossible(emptySpaceIndex, emptySpaceIndex + 4).ifPresent(result::add);

		return result;
	}

	private Optional<Permutation> swapIfPossible(int emptySpaceIndex, int swapIndex) {
		if (swapIndex >= 0 && swapIndex < list.size()) {
			return Optional.of(swap(emptySpaceIndex, swapIndex));
		}
		return Optional.empty();
	}

	private Permutation swap(int emptySpaceIndex, int swapIndex) {
		List<Integer> copy = new ArrayList<>(list);
		Integer value1 = copy.get(emptySpaceIndex);
		Integer value2 = copy.get(swapIndex);
		copy.set(emptySpaceIndex, value2);
		copy.set(swapIndex, value1);
		return new Permutation(copy, steps + 1);
	}

	public int steps() {
		return this.steps;
	}

	@Override
	public int hashCode() {
		return Objects.hash(list);
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
		return Objects.equals(list, other.list);
	}

	@Override
	public String toString() {
		return "Permutation " + list;
	}

}
