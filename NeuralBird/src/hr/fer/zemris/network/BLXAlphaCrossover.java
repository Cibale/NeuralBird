package hr.fer.zemris.network;


import java.util.Random;

import hr.fer.zemris.game.environment.IEnvironmentProvider;
import hr.fer.zemris.game.model.GameModelAITrainable;
import hr.fer.zemris.network.transfer_functions.ITransferFunction;
import hr.fer.zemris.network.transfer_functions.SigmoidTransferFunction;


/**
 * Implementation of the blend crossover operator proposed by Eshelman and Schaffer, 1993.<br><br>
 *
 * The BLX crossover is applied element-wise. For two double values y and x
 * (assuming y > x one offspring value is created in the uniform interval
 * [x-(y-x)*alpha;y+(y-x)*alpha].<br>
 * If alpha is set to 0, the operator creates a random solution between x and y.<br>
 * Values greater zero allow offspring that is apart from the interval between x and y.<br>
 *
 * @author Damir Kopljar
 *
 */
public class BLXAlphaCrossover  {
	Random rand;
	double alpha;

	public BLXAlphaCrossover(double alpha) {
		this.alpha=alpha;
	}

	/**
	 * Metoda implemetira BLXAplha krizanje.
	 *
	 * @param parent1 prvi roditelj
	 * @param parent2 drugi roditelj
	 * @return neuronska mreza nastala krizanje
	 */
	public NeuralNetwork doCrossover(NeuralNetwork parent1,NeuralNetwork parent2) {
		rand = new Random();
		ITransferFunction[] transferFunction = new ITransferFunction[parent1.getNumberOfNeuronsPerLayer().length-1];
		for(int i = 0; i<transferFunction.length;i++){
			transferFunction[i] = new SigmoidTransferFunction();
		}

		IEnvironmentProvider model = new GameModelAITrainable();
		NeuralNetwork result = new NeuralNetwork(parent1.getNumberOfNeuronsPerLayer(), transferFunction, model);
		double[] minimalValue=new double[parent1.getWeightsCount()];
		double[] maxValue=new double[parent1.getWeightsCount()];


		for(int i = 0; i < minimalValue.length; i++){
			double[] weights1=parent1.getWeights();
			double[] weights2=parent2.getWeights();
			if(weights1[i]>weights2[i]){
				minimalValue[i]=weights2[i];
				maxValue[i]=weights1[i];
			}else{
				minimalValue[i]=weights1[i];
				maxValue[i]=weights2[i];
			}
		}

		double[] delta = new double[minimalValue.length];
		for(int i = 0; i < delta.length; i++){
			delta[i] = maxValue[i]-minimalValue[i];
		}

		double[] resultWeihts= new double[parent1.getWeightsCount()];

		for (int i = 0; i < minimalValue.length; i++) {

				resultWeihts[i]=randomInRange(minimalValue[i]-delta[i]*alpha, maxValue[i]+delta[i]*alpha);
		}

		result.setWeights(resultWeihts);


		return result;


	}

	public double randomInRange(double min, double max) {
		  double range = max - min;
		  double scaled = rand.nextDouble() * range;
		  double shifted = scaled + min;
		  return shifted; // == (rand.nextDouble() * (max-min)) + min;
	}


}
