package hr.fer.zemris.game.components;

/**
 * Implemented by classes which can be translatable.
 *
 * @author Boris Generalic
 *
 */
@FunctionalInterface
public interface ITranslatable {

	/**
	 * Translates component for {@linkplain dx}.
	 *
	 * @param dx	how much component will be translated
	 */
	void translate(double dx);

}
