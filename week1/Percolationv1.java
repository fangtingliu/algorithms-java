/******************************************************************************
 *  Author:  Fangting Liu
 *  Github:  https://github.com/fangtingprahl
 *  Dependencies: StdIn.java StdOut.java
 *  Purpose:  This is my first solution with a nested array as the data-structure
 *  Correctness:  26/26 tests passed
 *  Memory:       9/8 tests passed
 *  Timing:       4/9 tests passed
 ******************************************************************************/

public class Percolation {

    private int[][] grid;
    private int[][] full;
    private int size;

    public static void main(String[] args) {
    }

    public Percolation(int n) {
        size = n;
        if (n <= 0) {
            throw new IllegalArgumentException("n is less than 0");
        }
        grid = new int[n][n];
        full = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
                full[i][j] = 0;
            }
        }
    }

    public void open(int i, int j) {
        if (0 < i && i <= size &&  0 < j && j <= size) {
            grid[i - 1][j - 1] = 1;

            if (i - 1 == 0) {
                markFull(i, j);
            } else {
                if (j - 1 > 0 && isFull(i, j - 1)) {
                    markFull(i, j);
                } else if (j + 1 <= size && isFull(i, j + 1)) {
                    markFull(i, j);
                } else if (i - 1 > 0 && isFull(i - 1, j)) {
                    markFull(i, j);
                } else if (i + 1 <= size && isFull(i + 1, j)) {
                    markFull(i, j);
                }
            }
        } else {
            throw new IndexOutOfBoundsException("Input is outside its prescribed range");
        }
    }

    private void markFull(int i, int j) {
        full[i - 1][j - 1] = 1;
        if (j - 1 > 0 && isOpen(i, j - 1) && !isFull(i, j - 1)) {
            markFull(i, j - 1);
        }
        if (j + 1 <= size && isOpen(i, j + 1) && !isFull(i, j + 1)) {
            markFull(i, j + 1);
        }
        if (i + 1 <= size && isOpen(i + 1, j) && !isFull(i + 1, j)) {
            markFull(i + 1, j);
        }
        if (i - 1 > 0 && isOpen(i - 1, j) && !isFull(i - 1, j)) {
            markFull(i - 1, j);
        }

    }

    public boolean isOpen(int i, int j) {
        if (0 < i && i <= size &&  0 < j && j <= size) {
            return grid[i-1][j-1] == 1;
        } else {
            throw new IndexOutOfBoundsException("Input is outside its prescribed range");
        }
    }

    public boolean isFull(int i, int j) {
        if (0 < i && i <= size &&  0 < j && j <= size) {
            return full[i-1][j-1] == 1;
        } else {
            throw new IndexOutOfBoundsException("Input is outside its prescribed range");
        }
    }

    public boolean percolates() {
        for (int j = 0; j < size; j++) {
            if (full[size - 1][j] == 1) {
                return true;
            }
        }
        return false;
    }
}