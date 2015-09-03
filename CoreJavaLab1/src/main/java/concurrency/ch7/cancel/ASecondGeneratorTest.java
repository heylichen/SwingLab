package concurrency.ch7.cancel;

import java.math.BigInteger;
import java.util.List;

public class ASecondGeneratorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<BigInteger> list = aSecondOfPrimes();
			System.out.println(list);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public static List<BigInteger> aSecondOfPrimes()
			throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();
		try {
			Thread.currentThread().sleep(1000);
		} finally {
			generator.cancel();
		}
		return generator.get();
	}

}
