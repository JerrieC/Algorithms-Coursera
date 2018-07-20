import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiayu Chen
 * @since 07/18/2018
 */
public class Board {
  private static final int BLANK = 0;
  private final int[][] blocks;
  private final int n;

  /**
   * construct a board from an n-by-n array of blocks
   * (where blocks[i][j] = block in row i, column j)
   *
   * @param blocks
   */
  public Board(int[][] blocks) {
    if (blocks == null) throw new IllegalArgumentException();
    this.n = blocks.length;
    this.blocks = copyBlocks(blocks);
    // Stores a reference to an externally mutable object in the instance
    // variable 'blocks', exposing the internal representation of the class
    // 'Board'.
  }

  /**
   * board dimension n
   *
   * @return
   */
  public int dimension() {
    return n;
  }

  /**
   * number of blocks out of place
   *
   * @return
   */
  public int hamming() {
    int hammingNum = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int num = blocks[i][j];
        if (num == BLANK) continue;
        if (position(num)[0] != i || position(num)[1] != j)
          hammingNum++;
      }
    }
    return hammingNum;
  }

  /**
   * @param num
   * @return
   */
  private int[] position(int num) {
    int[] position = {(num - 1) / n, (num - 1) % n};
    return position;
  }

  /**
   * sum of Manhattan distances between blocks and goal
   *
   * @return
   */
  public int manhattan() {
    int manhattanNum = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int num = blocks[i][j];
        if (num == BLANK) continue;
        int row = position(num)[0];
        int col = position(num)[1];
        if (row != i || col != j) {
          manhattanNum += Math.abs(row - i) + Math.abs(col - j);
        }
      }
    }
    return manhattanNum;
  }

  /**
   * is this board the goal board?
   *
   * @return
   */
  public boolean isGoal() {
    return hamming() == 0;
  }

  /**
   * a board that is obtained by exchanging any pair of blocks
   *
   * @return
   */
  public Board twin() {
    int[][] twinBlocks = copyBlocks(blocks);
    if (blocks[0][0] != 0 && blocks[0][1] != 0) {
      swap(twinBlocks, 0, 0, 0, 1);
    } else {
      swap(twinBlocks, 1, 0, 1, 1);
    }
    Board twin = new Board(twinBlocks);
    return twin;
  }

  /**
   * copy blocks
   *
   * @param original
   * @return
   */
  private int[][] copyBlocks(int[][] original) {
    int[][] twinBlocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        twinBlocks[i][j] = original[i][j];
      }
    }
    return twinBlocks;
  }

  /**
   * swap two blocks
   *
   * @param b
   * @param row1
   * @param col1
   * @param row2
   * @param col2
   */
  private void swap(int[][] b, int row1, int col1, int row2, int col2) {
    int temp = b[row1][col1];
    b[row1][col1] = b[row2][col2];
    b[row2][col2] = temp;
  }

  /**
   * does this board equal y?
   *
   * @param y
   * @return
   */
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null || y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    if (n != that.dimension()) return false;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (blocks[i][j] != that.blocks[i][j]) return false;
      }
    }
    return true;
  }

  /**
   * all neighboring boards
   *
   * @return
   */
  public Iterable<Board> neighbors() {
    List<Board> neighbors = new ArrayList<>();
    // find the blank
    int row = 0, col = 0;
    out:
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (blocks[i][j] == BLANK) {
          row = i;
          col = j;
          break out;
        }
      }
    }
    // up
    if (row > 0) {
      int[][] neighborBlock1 = copyBlocks(blocks);
      swap(neighborBlock1, row, col, row - 1, col);
      neighbors.add(new Board(neighborBlock1));
    }
    // down
    if (row < n - 1) {
      int[][] neighborBlock2 = copyBlocks(blocks);
      swap(neighborBlock2, row, col, row + 1, col);
      neighbors.add(new Board(neighborBlock2));
    }
    // left
    if (col > 0) {
      int[][] neighborBlock3 = copyBlocks(blocks);
      swap(neighborBlock3, row, col, row, col - 1);
      neighbors.add(new Board(neighborBlock3));
    }
    // right
    if (col < n - 1) {
      int[][] neighborBlock4 = copyBlocks(blocks);
      swap(neighborBlock4, row, col, row, col + 1);
      neighbors.add(new Board(neighborBlock4));
    }
    return neighbors;
  }

  /**
   * string representation of this board (in the output format specified below)
   *
   * @return
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * unit tests
   *
   * @param args
   */
  public static void main(String[] args) {
//    int[][] test = {{1, 3}, {0, 2}};
//    int[][] test1 = {{2, 1}, {3, 0}};
//    Board board = new Board(test);
//    Board board1 = new Board(test1);
//    StdOut.println("dimension is" + board.dimension());
//    StdOut.println("hamming is" + board.hamming());
//    StdOut.println("manhattan is" + board.manhattan());
//    StdOut.println(board);
//    StdOut.println(board.twin());
//    StdOut.println(board.equals(board1));
//    StdOut.println(board);
//    StdOut.println(board.neighbors());
  }
}