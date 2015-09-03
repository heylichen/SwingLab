package concurrency.ch12test.correctness;

import java.util.concurrent.Semaphore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class BoundedBuffer<E> {
	private E[] items;
	private Semaphore availableItems;
	private Semaphore availableSpaces;
	private int itemIndex; 
	private final int capacity;

	public BoundedBuffer(int capacity) {
		super();
		this.capacity = capacity;
		items = (E[])new Object[capacity];
		itemIndex = -1;
		availableItems = new Semaphore(0);
		availableSpaces = new Semaphore(capacity); 
	} 

	public void put(E x) throws InterruptedException {
		availableSpaces.acquire();
		doPut(x);
		availableItems.release();
	}

	public E take() throws InterruptedException {
		availableItems.acquire();
		E result = doGet();
		availableSpaces.release();
		return result;
	}

	public synchronized void doPut(E x) {
		itemIndex += 1;
		items[itemIndex] = x;
	}

	public synchronized E doGet() {
		E result = items[itemIndex];
		items[itemIndex] = null;
		itemIndex -= 1;
		return result; 
	}
	public synchronized boolean isEmpty(){ 
		return itemIndex==-1;
	};

	public synchronized boolean isFull(){ 
		return itemIndex==capacity-1;
	};
	public static void main(String[] args) {

	}

}
