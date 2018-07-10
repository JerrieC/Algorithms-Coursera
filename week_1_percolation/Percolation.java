import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Jiayu Chen
 * @since 07/03/2018
 */
public class Percolation {

  private boolean[][] gridOpen;

  private boolean[] connBottom;

  private boolean percolates;

  private int numberOfOpenSites;

  private final int gridSize;

  private final int indexOfFirst;

  private final WeightedQuickUnionUF uf;

  /**
   * create n-by-n grid, with all sites blocked.
   *
   * @param
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    gridSize = n;
    indexOfFirst = 0;
    // there are two dummy site on the first or last row of the grid
    uf = new WeightedQuickUnionUF(n * n + 2);
    // initialize the grid
    gridOpen = new boolean[n][n];
    connBottom = new boolean[n * n];
    percolates = false;
    // iterate the boolean matrix to initiate every site as false
    for (int site = 0; site < n; site++) {
      uf.union(indexOfFirst, index(1, site + 1));
    }
  }

  /**
   * open site (row, col) if it is not open already.
   *
   * @param row 
   * @param col 
   */
  public void open(int row, int col) {
    checkIndex(row, col);
    boolean bottom = false;
    int index = index(row, col);
    if (!isOpen(row, col)) {
      gridOpen[row - 1][col - 1] = true;
      numberOfOpenSites++;
      // up
      if (row > 1 && isOpen(row - 1, col)) {
        if (connBottom[uf.find(index)] || connBottom[uf.find(index(row - 1,
                col))]) {
          bottom = true;
        }
        uf.union(index, index(row - 1, col));
      }
      // down
      if (row < gridSize && isOpen(row + 1, col)) {
        if (connBottom[uf.find(index)] || connBottom[uf.find(index(row + 1,
                col))]) {
          bottom = true;
        }
        uf.union(index, index(row + 1, col));
      }
      // left
      if (col > 1 && isOpen(row, col - 1)) {
        if (connBottom[uf.find(index)] || connBottom[uf.find(index(row,
                col - 1))]) {
          bottom = true;
        }
        uf.union(index, index(row, col - 1));
      }
      // right
      if (col < gridSize && isOpen(row, col + 1)) {
        if (connBottom[uf.find(index)] || connBottom[uf.find(index(row,
                col + 1))]) {
          bottom = true;
        }
        uf.union(index, index(row, col + 1));
      }
      if (row == gridSize) {
        bottom = true;
      }
      connBottom[uf.find(index(row, col))] = bottom;
      if (uf.connected(index, indexOfFirst) && connBottom[uf.find(index)]) {
        percolates = true;
      }
    }
  }

  /**
   * is site (row, col) open.
   *
   * @param row 
   * @param col
   * @return
   */
  public boolean isOpen(int row, int col) {
    checkIndex(row, col);
    return gridOpen[row - 1][col - 1];
  }

  /**
   * whether the site (row, col) is full.
   *
   * @param row
   * @param col
   * @return full 
   */
  public boolean isFull(int row, int col) {
    checkIndex(row, col);
    return isOpen(row, col) && uf.connected(indexOfFirst, index(row, col));
  }

  /**
   * number of open sites.
   *
   * @return 
   */
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  /**
   * whether the system percolate.
   *
   * @return 
   */
  public boolean percolates() {
    return percolates;
  }

  /**
   * calculate the index of a site using it's row and column index.
   *
   * @param row 
   * @param col 
   * @return 
   */
  private int index(int row, int col) {
    checkIndex(row, col);
    return (row - 1) * gridSize + col - 1;
  }

  /**
   * check index.
   *
   * @param row
   * @param col
   */
  private void checkIndex(int row, int col) {
    if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * main method.
   *
   * @param args
   */
  public static void main(String[] args) {
    // test
  }
}
