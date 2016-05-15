package hr.fer.zemris.game.environment;

/**
 * Interface which is implemented which represent some kind of
 * environment.
 *
 * @author Boris Generalic
 *
 */
public interface IEnvironmentProvider {

	/**
     * Adds the given listener.
     *
     * @param listener listener to add
     */
	void addEnvironmentListener(IEnvironmentListener listener);

	/**
     * Removes the given listener.
     *
     * @param listener listener to remove
     */
	void removeEnvironmentListener(IEnvironmentListener listener);

	/**
     * Used to perform action if it is needed.
     *
     */
	void react();

}
