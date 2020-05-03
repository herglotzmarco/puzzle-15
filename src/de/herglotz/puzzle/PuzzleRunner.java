package de.herglotz.puzzle;

import de.herglotz.puzzle.data.Permutation;
import de.herglotz.puzzle.solver.Puzzle;

public class PuzzleRunner {

	public static final int CONCURRENCY = 8;
	public static final int BATCH_SIZE = 100;

	private static final Permutation INITIAL = Permutation.of(//
			1, 2, 3, 4, //
			5, 6, 7, 8, //
			9, 10, 11, 12, //
			13, 14, 15, 16 //
	);
	private static final Permutation GOAL = Permutation.of( //
			1, 2, 3, 4, //
			5, 6, 7, 8, //
			9, 10, 11, 12, //
			13, 14, 15, 16 //
	);

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle(INITIAL);

		long start = System.currentTimeMillis();
		Permutation solution = puzzle.solve(GOAL);
		long duration = System.currentTimeMillis() - start;

		if (solution != null) {
			int steps = solution.steps();
			System.out.println("SOLUTION WITH " + steps + " STEPS FOUND AFTER " + duration + "ms");
		} else {
			System.out.println("GOAL WAS FOUND IMPOSSIBLE AFTER " + duration + "ms");
		}
	}

}
