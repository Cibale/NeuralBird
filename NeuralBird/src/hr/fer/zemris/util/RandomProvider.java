package hr.fer.zemris.util;

import java.util.Random;

/**
 * Razred služi za dohvaćanje generatora slučajnih brojeva.
 *
 * @author Boris Generalic
 *
 */
public class RandomProvider {

	/**
	 * Mapa generatora slučajnih brojeva po dretvama.
	 */
	private static ThreadLocal<Random> randoms = new RandomThreadLocal();

	/**
	 * Dohvaćanja generatora slučajnih brojeva.
	 *
	 * @return Generator slučajnih brojeva.
	 */
	public static Random get() {
		return randoms.get();
	}

	/**
	 * Razred predstavlja mapu koja sadrži generatore slučajnih brojeva.
	 *
	 * @author Boris Generalic
	 *
	 */
	public static class RandomThreadLocal extends ThreadLocal<Random> {

		@Override
		protected Random initialValue() {
			return new Random();
		}

	}

}
