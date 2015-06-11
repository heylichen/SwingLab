package lab.multithreads;

import java.util.concurrent.locks.ReentrantLock;

public class SafeBank implements IBank {
	private final double[] accounts;
	private ReentrantLock lock = new ReentrantLock();

	/**
	* Constructs the bank.
	* @param n the number of accounts
	* @param initialBalance the initial balance for each account
	*/
	public SafeBank(int n, double initialBalance) {
		accounts = new double[n];
		for (int i = 0; i < accounts.length; i++)
			accounts[i] = initialBalance;
	}

	/**
	* Transfers money from one account to another.
	* @param from the account to transfer from
	* @param to the account to transfer to
	* @param amount the amount to transfer
	*/
	public void transfer(int from, int to, double amount) {
		lock.lock();
		try {
			if (accounts[from] < amount)
				return;
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf(" %10.2f from %d to %d", amount, from, to);
			accounts[to] += amount;
			System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
		} finally {
			lock.unlock();
		}

	}

	/**
	* Gets the sum of all account balances.
	* @return the total balance
	*/
	public double getTotalBalance() {
		lock.lock();
		double sum = 0;
		try {
			for (double a : accounts)
				sum += a;
		} finally {
			lock.unlock();
		}
		return sum;
	}

	/**
	* Gets the number of accounts in the bank.
	* @return the number of accounts
	*/
	public int size() {
		return accounts.length;
	}
}