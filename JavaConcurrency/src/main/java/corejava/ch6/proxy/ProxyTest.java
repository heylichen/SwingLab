package corejava.ch6.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyTest {

	public static void main(String[] args) {
		// populate values
		final int length = 1000;
		Object[] elements = new Object[length];
		for (int i = 0; i < length; i++) {
			Integer value = i + 1;
			TraceHandler tr = new TraceHandler(value);
			Object proxy = Proxy.newProxyInstance(null, new Class[] { Comparable.class }, tr);
			elements[i] = proxy;
		}
		// search
		Integer targetValue = new Random().nextInt(length) + 1;

		int index = Arrays.binarySearch(elements, targetValue);
		if (index > 0) {
			System.out.println(elements[index]);
		}

	}

}
