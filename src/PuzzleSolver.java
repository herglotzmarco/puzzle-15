import java.util.LinkedList;
import java.util.List;

public class PuzzleSolver implements Runnable {

	private PuzzleState state;
	private Permutation goal;
	private int highestSteps;

	public PuzzleSolver(PuzzleState state, Permutation goal) {
		this.state = state;
		this.goal = goal;
		this.highestSteps = 0;
	}

	@Override
	public void run() {
		while (!state.isSolved() && hasCandidates()) {
			List<Permutation> toCheck = state.fetchCandidateBatch();
			List<Permutation> newCandidates = new LinkedList<>();
			for (Permutation permutation : toCheck) {
				newCandidates.addAll(calculate(permutation));
			}
			state.addCandidates(newCandidates);
		}
	}

	private List<Permutation> calculate(Permutation current) {
		List<Permutation> candidates = current.performAllPossibleMoves();
		List<Permutation> newCandidates = new LinkedList<>();
		for (Permutation permutation : candidates) {
			if (permutation.equals(goal)) {
				state.setSolution(permutation);
			} else if (!state.wasChecked(permutation)) {
				newCandidates.add(permutation);
				if (permutation.steps() > highestSteps) {
					highestSteps = permutation.steps();
					System.out.println("STARTED NEW STEP LEVEL " + highestSteps);
				}
			}
		}
		return newCandidates;
	}

	private boolean hasCandidates() {
		if (state.hasCandidates()) {
			return true;
		} else {
			// no work left -> wait a little, other threads might add more soon
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return state.hasCandidates();
		}
	}
}
