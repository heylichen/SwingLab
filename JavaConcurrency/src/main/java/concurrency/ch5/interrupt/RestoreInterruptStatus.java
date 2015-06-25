package concurrency.ch5.interrupt;

public class RestoreInterruptStatus implements Runnable {

	@Override
	public void run() {
		 try{
			 Thread.currentThread().sleep(6000);
		 }catch(InterruptedException e){
			// restore interrupted status
			 Thread.currentThread().interrupt();
		 }

	}

}
