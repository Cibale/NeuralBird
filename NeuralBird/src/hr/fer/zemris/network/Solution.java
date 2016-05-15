package hr.fer.zemris.network;

/**
 * Class which represents a solution of {@link GeneticProgram}.
 *
 * @author Damir Kopljar
 *
 */
public class Solution  implements Comparable<Solution>{

	public NeuralNetwork network;
	public double fitness;

	public Solution(NeuralNetwork net) {
		network= net;
	}

	@Override
	public int compareTo(Solution o) {
		return Double.compare(fitness, o.fitness);
	}

}
