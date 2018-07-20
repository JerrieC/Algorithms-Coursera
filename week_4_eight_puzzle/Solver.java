import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

/**
 * @author Jiayu Chen
 * @since 07/18/2018
 */
public class Solver {
  private int moves = -1;
  private boolean isSolvable = false;
  private SearchNode minPriority;

  /**
   * SearchNode
   */
  private class SearchNode implements Comparable<SearchNode> {
    private final SearchNode predecessor;
    private final Board current;
    private int moves;
    private final int priority;
    private final boolean isTwin;


    public SearchNode(SearchNode predecessor, Board current, boolean isTwin) {
      this.predecessor = predecessor;
      this.current = current;
      if (predecessor == null) moves = 0;
      else moves = predecessor.moves + 1;
      priority = current.manhattan() + moves;
      this.isTwin = isTwin;
    }

    @Override
    public int compareTo(SearchNode searchNode) {
      if (priority == searchNode.priority) return Integer.compare(current
              .manhattan(), searchNode.current.manhattan());
      return Integer.compare(priority, searchNode.priority);
    }
  }

  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   */
  // (i) those that lead to the goal board and
  // (ii) those that lead to the goal board if we modify the initial board by
  // swapping any pair of blocks (the blank square is not a block).
  public Solver(Board initial) {
    final MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
    if (initial == null) throw new IllegalArgumentException();
    // First, insert the initial search node
    // (the initial board, 0 moves, and a null predecessor search node)
    // into a priority queue.
    minPQ.insert(new SearchNode(null, initial, false));
    minPQ.insert(new SearchNode(null, initial.twin(), true));
    // Then, delete from the priority queue the search node
    // with the minimum priority
    minPriority = minPQ.delMin();
    // Repeat this procedure
    // until the search node dequeued corresponds to a goal board
    while (!minPriority.current.isGoal()) {
      Board minPriBoard = minPriority.current;
      Board minPriPre;
      if (minPriority.predecessor != null)
        minPriPre = minPriority.predecessor.current;
      else minPriPre = null;
      // insert onto the priority queue all neighboring search nodes
      for (Board b : minPriBoard.neighbors()) {
        if (!b.equals(minPriPre)) {
          minPQ.insert(new SearchNode(minPriority, b, minPriority.isTwin));
        }
      }
      if (!minPQ.isEmpty()) minPriority = minPQ.delMin();
      else break;
    }
    if (minPriority.current.isGoal() && !minPriority.isTwin) {
      moves = minPriority.moves;
      isSolvable = true;
    }
  }

  /**
   * is the initial board solvable?
   *
   * @return
   */
  public boolean isSolvable() {
    return isSolvable;
  }

  /**
   * min number of moves to solve initial board; -1 if unsolvable
   *
   * @return
   */
  public int moves() {
    return moves;
  }

  /**
   * sequence of boards in a shortest solution; null if unsolvable
   *
   * @return
   */
  public Iterable<Board> solution() {
    if (!isSolvable) return null;
    final LinkedList<Board> solution = new LinkedList<>();
    SearchNode last = minPriority;
    solution.addFirst(last.current);
    while (last.predecessor != null) {
      last = last.predecessor;
      solution.addFirst(last.current);
    }
    return solution;
  }

  /**
   * solve a slider puzzle (given below)
   *
   * @param args
   */
  public static void main(String[] args) {
//    // create initial board from file
//    In in = new In("puzzle3x3-04.txt");
//    int n = in.readInt();
//    int[][] blocks = new int[n][n];
//    for (int i = 0; i < n; i++)
//      for (int j = 0; j < n; j++)
//        blocks[i][j] = in.readInt();
//    Board initial = new Board(blocks);
//
//    // solve the puzzle
//    Solver solver = new Solver(initial);
//
//    // print solution to standard output
//    if (!solver.isSolvable())
//      StdOut.println("No solution possible");
//    else {
//      StdOut.println("Minimum number of moves = " + solver.moves());
//      for (Board board : solver.solution())
//        StdOut.println(board);
//    }
  }
}