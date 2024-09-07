/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF union;
    private final byte[][] state;
    private final int size;
    private int openCount = 0;
    private boolean percolates = false;

    private static final byte BOTTOM = 0b001;
    private static final byte OPEN = 0b010;
    private static final byte TOP = 0b100;
    private static final byte PERCOLATES = TOP | BOTTOM | OPEN;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;

        // UF structure for percolation (includes both virtual top and bottom)
        union = new WeightedQuickUnionUF(n * n);

        state = new byte[n][n]; // grid for tracking state sites
    }

    // opens the site (row, col) if it is not state already
    public void open(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        if (!isOpenSafe(row, col)) {
            state[row][col] = OPEN;
            openCount++;
            // Connect to virtual top if in the first row
            if (row == 0) {
                state[row][col] |= TOP;
            }

            // Connect to virtual bottom if in the last row (only in percolation UF)
            if (row == size - 1) {
                state[row][col] |= BOTTOM;
            }
            connect(row, col, row - 1, col);
            connect(row, col, row + 1, col);
            connect(row, col, row, col - 1);
            connect(row, col, row, col + 1);
        }

        if ((state[row][col] & PERCOLATES) == PERCOLATES) {
            percolates = true;
        }
    }

    private void connect(int row, int col, int crow, int ccol) {
        var newUnionIdx = unionIndex(row, col);
        if (isOpenSafe(crow, ccol)) {
            var cUnionIdx = unionIndex(crow, ccol);
            var root = union.find(cUnionIdx);
            union.union(newUnionIdx, cUnionIdx);
            var newRoot = union.find(cUnionIdx);
            state[row][col] = state[newRoot / size][newRoot % size] = (byte) (state[row][col] | state[root / size][root % size] | state[newRoot / size][newRoot % size]);
        }
    }

    // is the site (row, col) state?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        return (state[row][col] & OPEN) == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        var root = union.find(unionIndex(row, col));
        return (state[root / size][ root % size ] & (TOP | OPEN)) == (TOP | OPEN);
    }

    // returns the number of state sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // Helper methods
    private void checkBounds(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException();
        }
    }

    private int unionIndex(int row, int col) {
        return row * size + col;
    }

    private boolean isOpenSafe(int row, int col) {
        try {
            checkBounds(row, col);
            return (state[row][col] & OPEN) == OPEN;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }
}
