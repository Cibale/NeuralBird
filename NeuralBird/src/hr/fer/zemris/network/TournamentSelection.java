package hr.fer.zemris.network;

import java.util.Random;

import hr.fer.zemris.util.RandomProvider;

/**
 * Tournament selection scheme.
 *
 * @author Damir Kopljar
 *
 */
public class TournamentSelection {

	private Random rand = RandomProvider.get();

	/**
	 * Metoda implementira n-turnirsku selekciju
	 * @param population populacija
	 * @param n velicina turnira
	 * @return pobjednik turnira
	 */
	NeuralNetwork select(Solution[] population,int n){
		Solution[] pool = new Solution[n];

		for(int i=0;i<n;i++){
			pool[i]=population[rand.nextInt(population.length)];
		}


		double maxPipes=pool[0].fitness;


		NeuralNetwork bestOne=pool[0].network;
		for(int i=0;i<n;i++){
			if(pool[i].fitness>maxPipes){
				maxPipes= pool[i].fitness;
				bestOne=pool[i].network;
			}
		}

		return bestOne;

	}
}
