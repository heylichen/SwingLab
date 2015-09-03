package concurrency.ch8threadpool.recursivealgo.model;

import java.util.Set;

public interface Puzzle<P> {
	P initialPosition();

	boolean isGoal(P position);

	Set<P> legalMoves(P position);

	public void move() throws InterruptedException;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
