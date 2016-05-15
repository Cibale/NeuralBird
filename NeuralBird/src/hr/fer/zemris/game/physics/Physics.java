package hr.fer.zemris.game.physics;

import hr.fer.zemris.game.environment.Constants;
import hr.fer.zemris.game.model.GameModel;

/**
 * Class which represents equations of 2nd Newton's law, which
 * are used in {@link GameModel} to implement real world physics.
 *
 * @author Jure Cular
 *
 */
public final class Physics {

	private Physics() {}

	public static double calculateShiftX(double v, int t) {
		return v * t;
	}

	public static double calculateShiftY(double v0, int t) {
		return v0 * t + 0.5 * Constants.currentConstants.GRAVITY.get() * t * t;
	}

	public static double calculateVelocity(double v0, int t) {
		return v0 + Constants.currentConstants.GRAVITY.get() * t;
	}

}
