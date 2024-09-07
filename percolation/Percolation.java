/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF ufPercolation;
    private final WeightedQuickUnionUF ufFullCheck;
    private final boolean[][] open;
    private final int size;
    private int openCount = 0;
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        virtualTop = 0; // Virtual top node
        virtualBottom = n * n + 1; // Virtual bottom node

        // UF structure for percolation (includes both virtual top and bottom)
        ufPercolation = new WeightedQuickUnionUF(n * n + 2);

        // UF structure for fullness check (only includes virtual top)
        ufFullCheck = new WeightedQuickUnionUF(n * n + 1);

        open = new boolean[n][n]; // grid for tracking open sites
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        if (!open[row][col]) {
            open[row][col] = true;
            openCount++;
            int unionIdx = unionIndex(row, col);

            // Connect to virtual top if in the first row
            if (row == 0) {
                ufPercolation.union(virtualTop, unionIdx);
                ufFullCheck.union(virtualTop, unionIdx); // Also in full check UF
            }

            // Connect to virtual bottom if in the last row (only in percolation UF)
            if (row == size - 1) {
                ufPercolation.union(unionIdx, virtualBottom);
            }

            // Connect with adjacent open sites
            if (isOpenSafe(row - 1, col)) {
                ufPercolation.union(unionIdx, unionIndex(row - 1, col));
                ufFullCheck.union(unionIdx, unionIndex(row - 1, col)); // Only for full check
            }
            if (isOpenSafe(row + 1, col)) {
                ufPercolation.union(unionIdx, unionIndex(row + 1, col));
                ufFullCheck.union(unionIdx, unionIndex(row + 1, col)); // Only for full check
            }
            if (isOpenSafe(row, col - 1)) {
                ufPercolation.union(unionIdx, unionIndex(row, col - 1));
                ufFullCheck.union(unionIdx, unionIndex(row, col - 1)); // Only for full check
            }
            if (isOpenSafe(row, col + 1)) {
                ufPercolation.union(unionIdx, unionIndex(row, col + 1));
                ufFullCheck.union(unionIdx, unionIndex(row, col + 1)); // Only for full check
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        return open[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        return ufFullCheck.find(unionIndex(row, col)) == ufFullCheck.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPercolation.find(virtualTop) == ufPercolation.find(virtualBottom);
    }

    // test client (optional)
    public void main(String[] args) {
    }

    // Helper methods
    private void checkBounds(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException();
        }
    }

    private int unionIndex(int row, int col) {
        return row * size + col + 1;
    }

    private boolean isOpenSafe(int row, int col) {
        try {
            checkBounds(row, col);
            return open[row][col];
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }
}
