package hr.fer.zemris.network.transfer_functions;

/**
 * Class represents f(x): 1 / (1 + e^-x);
 *
 * @author Martin
 *
 */
@SuppressWarnings("serial")
public class SigmoidTransferFunction implements ITransferFunction {

	@Override
	public double fire(double input) {
		return 1 / (1 + Math.exp(-input));
	}

}
