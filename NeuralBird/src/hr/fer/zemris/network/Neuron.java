/**
 *
 */
package hr.fer.zemris.network;

import java.io.Serializable;
import java.util.Random;

/**
 * Class represents one neuron in artificial neural network.
 *
 * @author Martin
 *
 */
@SuppressWarnings("serial")
public class Neuron implements Serializable {
	/** Number of weights entering this neuron (bias is also counted) */
	final int numberOfInputs;
	/** Array of weights. */
	final double[] weights;

	/**
	 * Public constructor of neuron. Weights are random defined in interval
	 * [-1,1].
	 *
	 * @param numberOfInputs
	 *            Number of inputs to this neuron. <b>Bias (<i>free weight</i>)
	 *            is not counted here.</b>
	 * @param rand
	 *            Generator of random values.
	 */
	public Neuron(int numberOfInputs, Random rand) {
		this.numberOfInputs = numberOfInputs + 1;
		weights = new double[this.numberOfInputs];
		for (int i = 0; i < this.numberOfInputs; i++) {
			weights[i] = rand.nextBoolean() ? rand.nextDouble() : rand.nextDouble() * -1;
		}
	}

}
