package hr.fer.zemris.game.environment;

/**
 * Interface which is implemented by classes which are interested
 * in changes that occur in {@link IEnvironmentProvider}.
 *
 * @author Boris Generalic
 *
 */
@FunctionalInterface
public interface IEnvironmentListener {

	/**
	 * Called by @{IEnvironmentProvider} when environment has been changed.
	 *
	 * @param provider
	 * @param variables
	 */
	void environmentChanged(IEnvironmentProvider provider, EnvironmentVariables variables);

}
