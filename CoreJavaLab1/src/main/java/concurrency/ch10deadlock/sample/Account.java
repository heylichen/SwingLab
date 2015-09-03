package concurrency.ch10deadlock.sample;

public class Account {
	private DollarAmount balance;
 
	public void debit(DollarAmount a){}
	public void credit(DollarAmount a){}
	public DollarAmount getBalance() {
		return balance;
	}

	public void setBalance(DollarAmount balance) {
		this.balance = balance;
	}
	public Account(DollarAmount balance) {
		super();
		this.balance = balance;
	}
	
}
