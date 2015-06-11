package lab.multithreads;

public class Bank implements IBank {
	private final double[] accounts;

	/**
	* Constructs the bank.
	* @param n the number of accounts
	* @param initialBalance the initial balance for each account
	*/
	public Bank(int n, double initialBalance) {
		accounts = new double[n];
		for (int i = 0; i < accounts.length; i++)
			accounts[i] = initialBalance;
	}

	/* (non-Javadoc)
	 * @see lab.multithreads.IBank#transfer(int, int, double)
	 */
	public void transfer(int from, int to, double amount) {
		if (accounts[from] < amount)
			return;
		System.out.print(Thread.currentThread());
		accounts[from] -= amount;
		System.out.printf(" %10.2f from %d to %d", amount, from, to);
		accounts[to] += amount;
		System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
	}

	/* (non-Javadoc)
	 * @see lab.multithreads.IBank#getTotalBalance()
	 */
	public double getTotalBalance() {
		double sum = 0;

		for (double a : accounts)
			sum += a;

		return sum;
	}

	/* (non-Javadoc)
	 * @see lab.multithreads.IBank#size()
	 */
	public int size() {
		return accounts.length;
	}
}