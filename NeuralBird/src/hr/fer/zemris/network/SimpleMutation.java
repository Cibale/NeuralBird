package hr.fer.zemris.network;

import java.util.Random;

import hr.fer.zemris.util.RandomProvider;



/**
 * Razred koji predstavlja implementaciju mutacije neuronske mreze.
 * Svaka tezina mutira se za mali odmak prema Gauss-ovoj razdiobi.
 *
 * @author Damir Kopljar
 *
 */
public class SimpleMutation {

	public double sigma;

	public SimpleMutation(Double sigma) {
		this.sigma=sigma;
	}

	/**
	 * Metoda implementira mutaciju neuronske mreze.
	 * Svaka tezina mutira se za mali odmak prema normalnoj razdiobi
	 * @param sol neuronska mreza koja se mutira
	 * @return mutirana mreza
	 */
	public void mutate(NeuralNetwork sol){
		Random rand = RandomProvider.get();

		double[] weights = sol.getWeights();

		for (int i = 0; i < weights.length; i++) {

			weights[i]+=rand.nextGaussian()*sigma;
		}
		sol.setWeights(weights);
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

}
