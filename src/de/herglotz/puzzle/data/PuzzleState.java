package de.herglotz.puzzle.data;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import de.herglotz.puzzle.PuzzleRunner;

public class PuzzleState {

	private Queue<Permutation> queue = new ArrayDeque<>();
	private Set<Permutation> seen = new HashSet<>();
	private Permutation solution = null;

	public PuzzleState(Permutation initial) {
		queue.add(initial);
	}

	public List<Permutation> fetchCandidateBatch() {
		List<Permutation> batch = new LinkedList<>();
		synchronized (queue) {
			for (int i = 0; i < PuzzleRunner.BATCH_SIZE; i++) {
				batch.add(queue.poll());
			}
		}
		batch.removeIf(Objects::isNull);
		return batch;
	}

	public void addCandidates(List<Permutation> candidates) {
		if (!candidates.isEmpty()) {
			synchronized (queue) {
				queue.addAll(candidates);
				seen.addAll(candidates);
			}
		}
	}

	public boolean hasCandidates() {
		return !queue.isEmpty();
	}

	public boolean wasChecked(Permutation permutation) {
		return seen.contains(permutation);
	}

	public void setSolution(Permutation solution) {
		if (this.solution == null || this.solution.steps() > solution.steps()) {
			this.solution = solution;
		}
	}

	public boolean isSolved() {
		return solution != null;
	}

	public Permutation getSolution() {
		return solution;
	}

}
