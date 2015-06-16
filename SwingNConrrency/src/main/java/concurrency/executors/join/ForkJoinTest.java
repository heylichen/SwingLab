package concurrency.executors.join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * This program demonstrates the fork-join framework.
 * @version 1.00 2012-05-20
 * @author Cay Horstmann
 */
public class ForkJoinTest {
	public static void main(String[] args) {
		final int SIZE = 10000000;
		double[] numbers = new double[SIZE];
		for (int i = 0; i < SIZE; i++)
			numbers[i] = Math.random();
		Counter counter = new Counter(numbers, 0, numbers.length, new Filter() {
			public boolean accept(double x) {
				return x > 0.5;
			}
		});
		ForkJoinPool pool = new ForkJoinPool();
		long start = System.currentTimeMillis();
		pool.invoke(counter);
		System.out.println(counter.join());
		long duration = System.currentTimeMillis() - start;
		System.out.println(duration);

		SimpleCounter sc = new SimpleCounter(numbers, 0, numbers.length,
				new Filter() {
					public boolean accept(double x) {
						return x > 0.5;
					}
				});
		start = System.currentTimeMillis();
		Integer count = sc.compute();
		System.out.println(count);
		duration = System.currentTimeMillis() - start;
		System.out.println(duration);

	}
}

class SimpleCounter {
	private double[] values;
	private int from;
	private int to;
	private Filter filter;

	public SimpleCounter(double[] values, int from, int to, Filter filter) {
		this.values = values;
		this.from = from;
		this.to = to;
		this.filter = filter;
	}

	protected Integer compute() {

		int count = 0;
		for (int i = from; i < to; i++) {
			if (filter.accept(values[i]))
				count++;
		}
		return count;
	}
}

interface Filter {
	boolean accept(double t);
}

class Counter extends RecursiveTask<Integer> {
	public static final int THRESHOLD = 1000;
	private double[] values;
	private int from;
	private int to;
	private Filter filter;

	public Counter(double[] values, int from, int to, Filter filter) {
		this.values = values;
		this.from = from;
		this.to = to;
		this.filter = filter;
	}

	protected Integer compute() {
		if (to - from < THRESHOLD) {
			int count = 0;
			for (int i = from; i < to; i++) {
				if (filter.accept(values[i]))
					count++;
			}
			return count;
		} else {
			int mid = (from + to) / 2;
			Counter first = new Counter(values, from, mid, filter);
			Counter second = new Counter(values, mid, to, filter);
			invokeAll(first, second);
			return first.join() + second.join();
		}
	}
}