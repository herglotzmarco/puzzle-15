import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Puzzle {

	private PuzzleState state;

	public Puzzle(Permutation initial) {
		this.state = new PuzzleState(initial);
	}

	public Permutation solve(Permutation goal) {
		solveMultithreaded(goal);
		return state.getSolution();
	}

	private void solveMultithreaded(Permutation goal) {
		ForkJoinPool pool = new ForkJoinPool(PuzzleRunner.CONCURRENCY);
		for (int i = 0; i < PuzzleRunner.CONCURRENCY; i++) {
			pool.execute(new PuzzleSolver(state, goal));
		}
		pool.shutdown();
		try {
			pool.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
