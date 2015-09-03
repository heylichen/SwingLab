package corejava.ch12generics;

class ArrayAlg {
	/**
	 * A pair of floating-point numbers
	 */
	public static class Pair {
		private double first;
		private double second;

		/**
		 * Constructs a pair from two floating-point numbers
		 * 
		 * @param f
		 *            the first number
		 * @param s
		 *            the second number
		 */
		public Pair(double f, double s) {
			first = f;
			second = s;
		}

		/**
		 * Returns the first number of the pair
		 * 
		 * @return the first number
		 */
		public double getFirst() {
			return first;
		}

		/**
		 * Returns the second number of the pair
		 * 
		 * @return the second number
		 */
		public double getSecond() {
			return second;
		}
	}

	/**
	 * Computes both the minimum and the maximum of an array
	 * 
	 * @param values
	 *            an array of floating-point numbers
	 * @return a pair whose first element is the minimum and whose second ement
	 *         is the maximum
	 */
	public static Pair minmax(double[] values) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (double v : values) {
			if (min > v)
				min = v;
			if (max < v)
				max = v;
		}
		return new Pair(min, max);
	}

	public static <T extends Comparable<? super T>> T min(T[] a) {
		if (a == null || a.length <= 0) {
			return null;
		}
		T min = a[0];
		if (a.length == 1) {
			return min;
		}
		for (int i = 1; i < a.length; i++) {
			T next = a[i];
			if (min.compareTo(next) > 0) {
				min = next;
			}
		}
		return min;
	}
}