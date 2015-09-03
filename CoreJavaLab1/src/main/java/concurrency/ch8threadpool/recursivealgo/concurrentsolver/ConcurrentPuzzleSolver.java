package concurrency.ch8threadpool.recursivealgo.concurrentsolver;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.ch8threadpool.recursivealgo.model.Node;
import concurrency.ch8threadpool.recursivealgo.model.Point;
import concurrency.ch8threadpool.recursivealgo.model.Puzzle;
import concurrency.ch8threadpool.recursivealgo.model.RoutePuzzle;

public class ConcurrentPuzzleSolver<P> {
	private final Puzzle<P> puzzle;
	private final ExecutorService exec;
	private final ConcurrentMap<P, Boolean> seen;
	final ValueLatch<Node<P>> solution = new ValueLatch<Node<P>>();

	public static void main(String[] args) {
		try {
			Logger logger = LoggerFactory.getLogger("SequentialPuzzleSolver_Main");
			logger.info("start");
			RoutePuzzle puzzle = new RoutePuzzle(10);

			ConcurrentPuzzleSolver<Point> solver = new ConcurrentPuzzleSolver<Point>(puzzle);
			long start = System.currentTimeMillis();

			List<Point> list = solver.solve();
			long end = System.currentTimeMillis();
			logger.info("total tryMoves:{}, done in {} ms.", 0, (end - start));
			if (list == null) {
				logger.info("Puzzle not solved.");
			} else {
				for (Point p : list) {
					logger.info("point(x:{},y:{})", p.getX(), p.getY());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ConcurrentPuzzleSolver(Puzzle<P> p) {
		super();
		exec = Executors.newCachedThreadPool();
		seen = new ConcurrentHashMap<P, Boolean>();
		puzzle = p;
	}

	public List<P> solve() throws InterruptedException {
		try {
			P p = puzzle.initialPosition();
			exec.execute(newTask(p, null));
			// block until solution found
			Node<P> solnNode = solution.getValue();
			return (solnNode == null) ? null : solnNode.asPointList();
		} finally {
			exec.shutdown();
		}
	}

	protected Runnable newTask(P p, Node<P> n) {
		return new SolverTask(p, n);
	}

	class SolverTask extends Node<P>implements Runnable {

		public SolverTask(P p, Node<P> n) {
			super(p, n);
		}

		public void run() {
			if (solution.isSet() || seen.putIfAbsent(pos, true) != null)
				return; // already solved or seen this position
			if (puzzle.isGoal(pos))
				solution.setValue(this);
			else
				for (P m : puzzle.legalMoves(pos)) {
					try {
						puzzle.move();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(solution.isSet()){return;}
					exec.execute(newTask(m, this));
				}

		}
	}
}