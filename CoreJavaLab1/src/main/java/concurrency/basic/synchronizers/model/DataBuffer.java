package concurrency.basic.synchronizers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This buffer is for testing
 * not thread safe
 * @author heylichen
 *
 */
public class DataBuffer {
	private List<String> data = new ArrayList<String>();
	private int bound;

	public DataBuffer(int bound) {
		super();
		this.bound = bound;
	}

	public void add(String entry) {
		if (!isFull()) {
			data.add(entry);
		}
	}

	public String take() {
		if (!isEmpty()) {
			String a = data.get(0);
			data.remove(0);
			return a;
		}
		return null;
	}

	public boolean isFull() {
		return data.size() < bound;
	}

	public boolean isEmpty() {
		return data.size() == 0;
	}
}
