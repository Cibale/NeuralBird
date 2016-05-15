package hr.fer.zemris.game.model;

import hr.fer.zemris.network.NeuralNetwork;

/**
 * Model used in training process of {@link NeuralNetwork}.<br>
 *
 * This is performance-wise implemented model to reduce unnecessary overheads
 * during the training process.
 *
 * @author Boris Generalic
 *
 */
public class GameModelAITrainable extends GameModelAI {

	@Override
	protected void setupGround() {
		//does nothing performance-wise
	}

	@Override
	protected double initialiseGround(double nextGroundX) {
		//does nothing performance-wise
		return 0;
	}

	@Override
	protected void moveGround(int time) {
		//does nothing performance-wise
	}

}
