package hr.fer.zemris.game.environment;

import hr.fer.zemris.network.NeuralNetwork;

/**
 * Class which wraps variables from environment which are sent as inputs
 * for {@link NeuralNetwork}.
 * Variables are self documented.
 *
 * @author Damir Kopljar and Boris Generalic
 *
 */
public class EnvironmentVariables {

	private double distanceToLowerPipeLeftSide;
	private double distanceToUpperPipeLeftSide;
	private double distanceToLowerPipeRightSide;
	private double distanceToUpperPipeRightSide;
	private double birdHeight;
	private double pipeDirection;
	private double distanceToReward;
	private double visibleReward;
	private double relativeHeightToReward;

	public EnvironmentVariables(double distanceToLowerPipeLeftSide, double distanceToUpperPipeLeftSide,
			double distanceToLowerPipeRightSide, double distanceToUpperPipeRightSide, double birdHeight,
			double pipeDirection, double distanceToReward, double visibleReward, double relativeHeightToReward) {
		super();
		this.distanceToLowerPipeLeftSide = distanceToLowerPipeLeftSide;
		this.distanceToUpperPipeLeftSide = distanceToUpperPipeLeftSide;
		this.distanceToLowerPipeRightSide = distanceToLowerPipeRightSide;
		this.distanceToUpperPipeRightSide = distanceToUpperPipeRightSide;
		this.birdHeight = birdHeight;
		this.pipeDirection = pipeDirection;
		this.distanceToReward = distanceToReward;
		this.visibleReward = visibleReward;
		this.relativeHeightToReward = relativeHeightToReward;
	}

	public double[] getVariables() {
		return new double[] {
				distanceToLowerPipeLeftSide,
				distanceToUpperPipeLeftSide,
				distanceToLowerPipeRightSide,
				distanceToUpperPipeRightSide,
				birdHeight,
				pipeDirection,
				distanceToReward,
				visibleReward,
				relativeHeightToReward
		};
	}

}
