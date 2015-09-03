package concurrency.ch8threadpool.recursivealgo.model;

import java.util.ArrayList;
import java.util.List;

public class Node<P> {
	protected P pos;
	protected Node<P> prev;
	
	public List<P> asPointList(){
		List<P> result = new ArrayList<P>();
		Node<P> current = this;
		while(current!=null){
			result.add(0, current.getPos());
			current = current.getPrev();
		}
		return result;
	}
	
	public P getPos() {
		return pos;
	}

	public void setPos(P pos) {
		this.pos = pos;
	}

	public Node<P> getPrev() {
		return prev;
	}

	public void setPrev(Node<P> prev) {
		this.prev = prev;
	}

	public Node() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Node(P pos, Node<P> prev) {
		super();
		this.pos = pos;
		this.prev = prev;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
