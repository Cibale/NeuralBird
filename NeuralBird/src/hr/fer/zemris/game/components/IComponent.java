package hr.fer.zemris.game.components;

import hr.fer.zemris.game.model.GameModel;

/**
 * Interface which is implemented by component in {@link GameModel}.
 *
 * @author Boris Generalic
 *
 */
public interface IComponent extends IVisible, ITranslatable {

	/**
	 * Used to get component's right most x coordinate.
	 *
	 * @return	component's right most x coordinate
	 */
	double getRightMostX();

	/**
	 * Used to get component's left most x coordinate.
	 *
	 * @return	component's left most x coordinate
	 */
	double getLeftMostX();

}
