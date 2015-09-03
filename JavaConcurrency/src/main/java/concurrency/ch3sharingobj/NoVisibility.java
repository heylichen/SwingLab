package concurrency.ch3sharingobj;

public class NoVisibility {
	private boolean ready = false;
	private int number = 0;

	private class ReaderThread extends Thread {
		public void run() {
			while (!ready)
				Thread.yield();

			// System.out.println(Thread.currentThread().getName() + ":" +
			// number);
			if (number != 42) {
				System.out.println(Thread.currentThread().getName() + ": 0 Found!");
			}
		}
	}

	public void runTest(int n) {
		for (int i = 0; i < n; i++) {
			new ReaderThread().start();
		}
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		number = 42;
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ready = true;
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		NoVisibility noVisibility = new NoVisibility();
		noVisibility.runTest(128);
	}
}