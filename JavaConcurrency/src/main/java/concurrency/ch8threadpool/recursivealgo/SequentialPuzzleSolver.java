package concurrency.ch8threadpool.recursivealgo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.ch8threadpool.recursivealgo.model.Node;
import concurrency.ch8threadpool.recursivealgo.model.Point;
import concurrency.ch8threadpool.recursivealgo.model.Puzzle;
import concurrency.ch8threadpool.recursivealgo.model.RoutePuzzle;

public class SequentialPuzzleSolver<P> {
	Logger logger = LoggerFactory.getLogger(SequentialPuzzleSolver.class);
	private final Puzzle<P> puzzle;
	private final Set<P> seen = new HashSet<P>();
	private Integer totalMoves = 0;

	public SequentialPuzzleSolver(Puzzle<P> puzzle) {
		this.puzzle = puzzle;
	}

	public static void main(String[] args) { 
		Logger logger = LoggerFactory.getLogger("SequentialPuzzleSolver_Main");
		logger.info("start");
		RoutePuzzle puzzle = new RoutePuzzle(10);
		puzzle.initialPosition();

		SequentialPuzzleSolver<Point> solver = new SequentialPuzzleSolver<Point>(puzzle);
		long start = System.currentTimeMillis();
		List<Point> list = solver.solve();
		long end = System.currentTimeMillis();
		logger.info("total tryMoves:{}, done in {} ms.", solver.getTotalMoves(),(end - start));
		if(list==null){
			logger.info("Puzzle not solved.");
		} else{
			for (Point p : list) {
				logger.info("point(x:{},y:{})", p.getX(), p.getY());
			}
		}
		

	}

	public List<P> solve() {
		P pos = puzzle.initialPosition();
		return search(new Node<P>(pos, null));
	}

	private List<P> search(Node<P> node) {
		if (!seen.contains(node.getPos())) {
			seen.add(node.getPos());
			if (puzzle.isGoal(node.getPos()))
				return node.asPointList();
			for (P pos : puzzle.legalMoves(node.getPos())) {
				try {
					puzzle.move();
					totalMoves+=1;
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("error");
				}

				Node<P> child = new Node<P>(pos, node);
				List<P> result = search(child);
				if (result != null)
					return result;
			}
		}
		return null;
	}

	public Integer getTotalMoves() {
		return totalMoves;
	}

	public void setTotalMoves(Integer totalMoves) {
		this.totalMoves = totalMoves;
	}

}