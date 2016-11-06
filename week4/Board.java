import java.util.Stack;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;

public class Board {
    private int[][] board;
    private int n;

    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        n = blocks.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int counts = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != i * n + j + 1) {
                    counts++;
                }
            }
        }
        return counts;
    }

    public int manhattan() {
        int counts = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    int shouldJ = (board[i][j] - 1) % n;
                    int shouldI = (board[i][j] - shouldJ - 1) / n;
                    int diff = Math.abs(j - shouldJ) + Math.abs(i - shouldI);
                    counts += diff;
                }
            }
        }
        return counts;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    public Board twin() {
        int[][] blocks = copy();
        while (true) {
            int row1 = StdRandom.uniform(n);
            int col1 = StdRandom.uniform(n);
            int row2 = StdRandom.uniform(n);
            int col2 = StdRandom.uniform(n);
            if ((row1 != row2 || col1 != col2) && blocks[row1][col1] != 0 && blocks[row2][col2] != 0) {
                swap(blocks, row1, col1, row2, col2);
                return new Board(blocks);
            }
        }
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != getClass())
            return false;
        if (y == this)
            return true;
        return y.toString().equals(toString());
    }

    private void swap(int[][] blocks, int row1, int col1, int row2, int col2) {
        int temp = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = temp;
    }

    private int[][] copy() {
        int[][] cb = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cb[i][j] = board[i][j];
            }
        }
        return cb;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int i = 0, j = 0;
        while (i < n && j < n) {
            if (board[i][j] == 0) {
                while (i > 0) {
                    int[][] blocks = copy();
                    swap(blocks, i - 1, j, i, j);
                    stack.push(new Board(blocks));
                    break;
                }
                while (i < n - 1) {
                    int[][] blocks = copy();
                    swap(blocks, i + 1, j, i, j);
                    stack.push(new Board(blocks));
                    break;
                }
                while (j > 0) {
                    int[][] blocks = copy();
                    swap(blocks, i, j - 1, i, j);
                    stack.push(new Board(blocks));
                    break;
                }
                while (j < n - 1) {
                    int[][] blocks = copy();
                    swap(blocks, i, j + 1, i, j);
                    stack.push(new Board(blocks));
                    break;
                }
                break;
            }
            if (j < n - 1) {
                j++;
            } else {
                i++;
                j = 0;
            }
        }
        return stack;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);
//        Board second = new Board(blocks);
//        System.out.println(initial.isGoal());
//        System.out.println(initial.toString());

//        for (Board board : initial.neighbors())
//            System.out.println(board.toString());

//        System.out.println("twin: " + initial.twin().equals(initial));
//        System.out.println("itself: " + initial.equals(initial));
//        System.out.println("second: " + second.equals(initial));
    }
}