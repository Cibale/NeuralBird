package hr.fer.zemris.network.transfer_functions;

import java.io.Serializable;

/**
 * Interface represents activation function on neuron.
 *
 * @author Martin
 *
 */
public interface ITransferFunction extends Serializable {

	/**
	 * Method of every activation function. For given input it calculates
	 * output.
	 *
	 * @param input
	 *            Input for which output is calculated.
	 * @return Output for provided input.
	 */
	double fire(double input);
}
