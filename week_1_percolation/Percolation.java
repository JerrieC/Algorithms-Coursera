import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * To model a percolation system.
 *
 * @author Jiayu Chen
 * @purpose
 * @guide
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
   * @param n grid row's or column's number
   */
  public Percolation(final int n) {
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
   * @param row the index of a site's row
   * @param col the index of a site's column
   */
  public void open(final int row, final int col) {
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
   * @param row the index of a site's row
   * @param col the index of a site's column
   * @return open state of site
   */
  public boolean isOpen(final int row, final int col) {
    checkIndex(row, col);
    return gridOpen[row - 1][col - 1];
  }

  /**
   * whether the site (row, col) is full.
   *
   * @param row the index of a site's row
   * @param col the index of a site's column
   * @return full state of site
   */
  public boolean isFull(final int row, final int col) {
    checkIndex(row, col);
    return isOpen(row, col) && uf.connected(indexOfFirst, index(row, col));
  }

  /**
   * number of open sites.
   *
   * @return number of open sites
   */
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  /**
   * whether the system percolate.
   *
   * @return percolate state of grid
   */
  public boolean percolates() {
    return percolates;
  }

  /**
   * calculate the index of a site using it's row and column index.
   *
   * @param row the index of a site's row
   * @param col the index of a site's column
   * @return site index
   */
  private int index(final int row, final int col) {
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
