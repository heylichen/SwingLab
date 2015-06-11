package lab.multithreads;

public interface IBank {

	/**
	* Transfers money from one account to another.
	* @param from the account to transfer from
	* @param to the account to transfer to
	* @param amount the amount to transfer
	*/
	public void transfer(int from, int to, double amount);

	/**
	* Gets the sum of all account balances.
	* @return the total balance
	*/
	public double getTotalBalance();

	/**
	* Gets the number of accounts in the bank.
	* @return the number of accounts
	*/
	public int size();

}