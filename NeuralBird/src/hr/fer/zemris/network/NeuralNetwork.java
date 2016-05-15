package hr.fer.zemris.network;

import hr.fer.zemris.game.environment.EnvironmentVariables;
import hr.fer.zemris.game.environment.IEnvironmentListener;
import hr.fer.zemris.game.environment.IEnvironmentProvider;
import hr.fer.zemris.network.transfer_functions.ITransferFunction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;


/**
 * Class represents a feed forward artificial neural network. It consists of
 * inputs, hidden layers and outputs. It is defined by number of neurons per
 * layer, activation function for each layer and data which is prepared for it.
 *
 * @author Martin
 *
 */
@SuppressWarnings("serial")
public class NeuralNetwork implements Serializable, IEnvironmentListener {

	/** Number of inputs in this network. */
	private final int numberOfInputs;
	/** Number of outputs in this network. */
	public final int numberOfOutputs;
	/** Layers of this network. Input layer is not counted. */
	private final NeuronLayer[] layers;
	/** Generator of random values. */
	private final Random rand = new Random();
	/** Number of weights of which network is constructed. */
	private int numberOfWeights;
	/** Number of neurons per layer. */
	private int[] neuronsPerLayer;


	/**
	 * Public constructor of neural network.
	 *
	 * @param neuronsPerLayer
	 *            Number of layers is defined by length of this array and number
	 *            of neurons in each of the layers is defined by value on index
	 *            of layer. <br>
	 *            E.g. int[] = {2,3,4,5} means there are two inputs, three
	 *            neurons in first hidden layer, four neurons in second hidden
	 *            layer and five outputs.
	 * @param transferFunctions
	 *            Transfer functions for each of the layers. Counting is same as
	 *            for <code>neuronsPerLayer</code> values.
	 *
	 */
	public NeuralNetwork(int[] neuronsPerLayer, ITransferFunction[] transferFunctions, IEnvironmentProvider provider) {
		numberOfInputs = neuronsPerLayer[0];
		numberOfOutputs = neuronsPerLayer[neuronsPerLayer.length - 1];
		layers = new NeuronLayer[transferFunctions.length];
		numberOfWeights = 0;
		for (int i = 1; i < neuronsPerLayer.length; i++) {
			layers[i - 1] = new NeuronLayer(transferFunctions[i - 1], neuronsPerLayer[i], neuronsPerLayer[i - 1], rand);
			// + 1 because of free weight / bias
			numberOfWeights += (neuronsPerLayer[i - 1] + 1) * neuronsPerLayer[i];
		}
		this.neuronsPerLayer=neuronsPerLayer;
		provider.addEnvironmentListener(this);
	}

	/**
	 * Returns total number of weights in this neural network (including bias
	 * for each of neurons).
	 *
	 * @return Number of weights in this network.
	 */
	public int getWeightsCount() {
		return numberOfWeights;
	}

	/**
	 * Method returns all weights of which this neural network is constructed
	 * (including bias for each neuron).
	 *
	 * @return All weights in this feed forward artificial neural network
	 */
	public double[] getWeights() {
		final double[] allWeights = new double[numberOfWeights];
		int index = 0;
		for (final NeuronLayer layer : layers) {
			for (int j = 0; j < layer.numberOfNeurons; j++) {
				for (final double weight : layer.neurons[j].weights) {
					allWeights[index++] = weight;
				}
			}
		}
		return allWeights;
	}

	/**
	 * It calculates outputs and stores it into <code>outputs</code> array based
	 * on inputs which are provided through <code>inputs</code> array and
	 * weights of this network which are currently set.
	 *
	 * @param inputs
	 *            Inputs for neural network.
	 */
	public double calcOutput(double[] inputs) {
		double[] output = new double[1];
		calcOutputs(inputs, this.getWeights(), output);
		return output[0];
	}

	/**
	 * This is main method of this class. It calculates outputs and stores it
	 * into <code>outputs</code> array based on inputs which are provided
	 * through <code>inputs</code> array and weights of network which are
	 * provided through <code>weights</code> array.
	 *
	 * @param inputs
	 *            Inputs for neural network.
	 * @param weights
	 *            Values for all weights of this network.
	 * @param outputs
	 *            Array in which outputs are stored.
	 */
	public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
		if (inputs.length != numberOfInputs) {
			throw new IllegalArgumentException("Number of inputs doesn't match");
		}

		double[] tempOutputs = new double[layers[0].numberOfNeurons];
		double[] tempInputs = Arrays.copyOf(inputs, inputs.length);
		int currentWeight = 0;
		// for each layer
		for (int i = 0; i < layers.length; i++) {
			if (i > 0) {
				// input = outputs;
				tempInputs = Arrays.copyOf(tempOutputs, tempOutputs.length);
				tempOutputs = new double[layers[i].numberOfNeurons];
			}
			int currentInput = 0;
			// for each neuron in that layer
			for (int j = 0; j < layers[i].numberOfNeurons; j++) {
				double netInput = 0;
				final int numOfinputs = layers[i].neurons[j].numberOfInputs;
				// for each weight in that neuron
				for (int k = 0; k < numOfinputs - 1; k++) {
					netInput += weights[currentWeight++] * tempInputs[currentInput++];
				}
				// add free weight
				netInput += weights[currentWeight++];
				tempOutputs[j] = layers[i].activationFunction.fire(netInput);
				currentInput = 0;
			}
		}

		// set outputs values
		System.arraycopy(tempOutputs, 0, outputs, 0, tempOutputs.length);
	}

	/**
	 * Sets weights for neural network.
	 *
	 * @param weights
	 *            Weights to set as new weights for this neural network.
	 */
	public void setWeights(double[] weights) {
		if (numberOfWeights != weights.length) {
			throw new IllegalArgumentException("Length of array doesn't match");
		}
		int index = 0;
		for (final NeuronLayer layer : layers) {
			for (int j = 0; j < layer.numberOfNeurons; j++) {
				for (int k = 0; k < layer.neurons[j].numberOfInputs; k++) {
					layer.neurons[j].weights[k] = weights[index++];
				}
			}
		}
	}


	/**
	 * Returns number of neurons per layer
	 *
	 * @return array with number of neurons per layer
	 */
	public int[] getNumberOfNeuronsPerLayer(){
		return neuronsPerLayer;

	}

	@Override
	public void environmentChanged(IEnvironmentProvider provider, EnvironmentVariables variables) {
		if(calcOutput(variables.getVariables()) > 0.5) {
			provider.react();
		}
	}
}
