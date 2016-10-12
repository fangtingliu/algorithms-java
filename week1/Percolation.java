/******************************************************************************
 *  Author:  Fangting Liu
 *  Github:  https://github.com/fangtingprahl
 *  Dependencies: StdIn.java StdOut.java
 *  Purpose:  This is my solution use WeightedQuickUnionUF
 *  Correctness:  26/26 tests passed
 *  Memory:       9/8 tests passed
 *  Timing:       4/9 tests passed
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF grid;
    private boolean[][] open;
    private boolean[] bottom;
    private int size;
    private boolean percolated;

    public Percolation(int n) {
        size = n;
        percolated = false;
        if (n <= 0) {
            throw new IllegalArgumentException("n is less than 0");
        }
        grid = new WeightedQuickUnionUF(n * n + 1);
        open = new boolean[n][n];
        bottom = new boolean[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                bottom[i * size + j] = false;
                open[i][j] = false;
            }
        }
    }

    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            int pos = (i - 1) * size + (j - 1);
            open[i - 1][j - 1] = true;

            if (i == 1) {
                grid.union(pos, size * size);
            }

            if (i == size) {
                bottom[pos] = true;
            }

            int root1 = pos;
            int root2;
            if (j - 1 > 0 && isOpen(i, j - 1)) {
                root2 = grid.find(pos - 1);
                grid.union(pos, pos - 1);
                updateBottom(root1, root2);
                root1 = grid.find(pos);;
            }
            if (j + 1 <= size && isOpen(i, j + 1)) {
                root2 = grid.find(pos + 1);
                grid.union(pos, pos + 1);
                updateBottom(root1, root2);
                root1 = grid.find(pos);
            }
            if (i - 1 > 0 && isOpen(i - 1, j)) {
                root2 = grid.find(pos - size);
                grid.union(pos, pos - size);
                updateBottom(root1, root2);
                root1 = grid.find(pos);
            }
            if (i + 1 <= size && isOpen(i + 1, j)) {
                root2 = grid.find(pos + size);
                grid.union(pos, pos + size);
                updateBottom(root1, root2);
            }

            if (grid.connected(pos, size * size) && bottom[root1]) {
                percolated = true;
            }
        }
    }

    private void updateBottom(int root1, int root2) {
        bottom[root1] = bottom[root1] || bottom[root2];
        bottom[root2] = bottom[root2] || bottom[root1];
    }

    public boolean isOpen(int i, int j) {
        if (0 < i && i <= size &&  0 < j && j <= size) {
            return open[i - 1][j - 1];
        } else {
            throw new IndexOutOfBoundsException("Input is outside its prescribed range");
        }
    }

    public boolean isFull(int i, int j) {
        if (0 < i && i <= size &&  0 < j && j <= size) {
            return isOpen(i, j) && grid.connected((i - 1) * size + (j - 1), size * size);
        } else {
            throw new IndexOutOfBoundsException("Input is outside its prescribed range");
        }
    }

    public boolean percolates() {
        return percolated;
    }

    public static void main(String[] args) {
    }
}