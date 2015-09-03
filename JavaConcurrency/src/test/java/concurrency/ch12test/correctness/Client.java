package concurrency.ch12test.correctness;

public class Client {
	private Listener listener;
	
	
	public void test(){
		System.out.println(listener.hello());
		System.out.println(listener);
	}
	
	public Listener getListener() {
		return listener;
	}




	public void setListener(Listener listener) {
		this.listener = listener;
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
