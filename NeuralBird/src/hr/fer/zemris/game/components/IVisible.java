package hr.fer.zemris.game.components;

/**
 * Used to identify if component is visible or not.
 *
 * @author Boris Generalic
 *
 */
@FunctionalInterface
public interface IVisible {

	/**
	 * Returns {@code true} if it's visible, {@code false otherwise.}
	 *
	 * @return	{@code true} if it's visible, {@code false otherwise.
	 */
	boolean isVisible();

}
