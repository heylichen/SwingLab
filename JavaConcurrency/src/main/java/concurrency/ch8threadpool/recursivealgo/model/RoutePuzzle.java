package concurrency.ch8threadpool.recursivealgo.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.ch8threadpool.recursivealgo.SequentialPuzzleSolver;

public class RoutePuzzle implements Puzzle<Point> {
	Logger logger = LoggerFactory.getLogger(SequentialPuzzleSolver.class);
	private Integer length;
	private Point startPos;
	private Point goalPos;
	private Map<Integer, Set<Integer>> badPositions = new HashMap<Integer, Set<Integer>>();

	@Override
	public void move() throws InterruptedException {
		Thread.currentThread().sleep(20);

	}

	@Override
	public Point initialPosition() {
		startPos = new Point(0, 0);
		for (int i = 0; i < length-1; i++) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(i);
			badPositions.put(i, set);
		}
		Set<Integer> set = new HashSet<Integer>();
		set.add(length-2);
		badPositions.put(length-1, set);
		 
		goalPos = new Point(length-1,length-1);
		return startPos;
	}

	@Override
	public boolean isGoal(Point position) {
		return goalPos.equals(position);
	}

	@Override
	public Set<Point> legalMoves(Point position) {
		Set<Point> result = new HashSet<Point>();
		if (position == null || position.getX() == null || position.getY() == null) {
			return result;
		}

		int x = position.getX();
		int y = position.getY();
		int startX = x;
		int endX = x + 1;
		int startY = y;
		int endY = y + 1;

		for (int i = startX; i <= endX; i++) {
			for (int k = startY; k <= endY; k++) {
				if (i == x && k == y) {
					continue;
				}
				if (i < 0 || i >= length) {
					continue;
				}
				if (k < 0 || k >= length) {
					continue;
				}
				Point p = new Point(i, k);
				if (!isLegalPosition(p)) {
					continue;
				}
				result.add(p);
			}
		}

		return result;
	}

	public boolean isLegalPosition(Point p) {
		Set<Integer> set =badPositions.get(p.getX());
		if(set==null){
			return true;
		}
		if(set.contains(p.getY())){
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		RoutePuzzle puzzle = new RoutePuzzle(10);
		puzzle.initialPosition();
		Set<Point> set = puzzle.legalMoves(new Point(0, 0));
		System.out.println("legal moves found :" + set.size());
		for (Point p : set) {
			System.out.println("point(x:" + p.getX() + ",y:" + p.getY() + ")");
		}

	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public RoutePuzzle() {
		super();
	}

	public RoutePuzzle(Integer length) {
		super();
		this.length = length;
	}

}
