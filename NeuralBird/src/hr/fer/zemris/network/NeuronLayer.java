package hr.fer.zemris.network;

import java.io.Serializable;
import java.util.Random;

import hr.fer.zemris.network.transfer_functions.ITransferFunction;


/**
 * Class represents one neuron layer which consists of predefined number of
 * neurons.
 *
 * @author Martin
 *
 */
@SuppressWarnings("serial")
public class NeuronLayer implements Serializable {
	/** Number of neurons in this layer. */
	final int numberOfNeurons;
	/** Neurons of which this layer is consisted. */
	final Neuron[] neurons;
	/**
	 * Activation function for this layer (every neuron in this layer has this
	 * activation function.
	 */
	ITransferFunction activationFunction;

	/**
	 * Constructor of one neuron layer. It is only defined by number of neurons
	 * which are in this layer and number of inputs per one neuron. Every neuron
	 * has same number of inputs (bias is not counted).
	 *
	 * @param activationFunction
	 *            Function for activating a neuron.
	 * @param numberOfNeurons
	 *            Number of neurons in this layer.
	 * @param numberOfInputsPerNeuron
	 *            Number of inputs per one neuron (bias not counted).
	 * @param rand
	 *            Generator of random values.
	 */
	public NeuronLayer(ITransferFunction activationFunction, final int numberOfNeurons,
			final int numberOfInputsPerNeuron, Random rand) {
		this.numberOfNeurons = numberOfNeurons;
		this.activationFunction = activationFunction;
		neurons = new Neuron[numberOfNeurons];
		for (int i = 0; i < numberOfNeurons; i++) {
			neurons[i] = new Neuron(numberOfInputsPerNeuron, rand);
		}
	}

}
