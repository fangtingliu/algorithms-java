import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private int moves;
    private SearchNode goalNode = null;

    private class SearchNode implements Comparable<SearchNode> {
        private int moves = 0;
        private SearchNode previousSearchNode = null;
        private Board board;
        private int boardM;

        public SearchNode(Board initial) {
            board = initial;
            boardM = board.manhattan();
        }

        public int compareTo(SearchNode n) {
            int nM = n.board.manhattan();
            if (boardM == nM) {
                return 0;
            } else {
                return boardM - nM;
            }
        }
    }

    public Solver(Board initial) {
        MinPQ<SearchNode> minq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinMinq = new MinPQ<SearchNode>();

        Board twin = initial.twin();

        SearchNode init = new SearchNode(initial);
        SearchNode twinInit = new SearchNode(twin);

        minq.insert(init);
        twinMinq.insert(twinInit);
        while (minq.size() > 0) {
            SearchNode curr = minq.delMin();
            SearchNode twinCurr = twinMinq.delMin();
            if (curr.board.isGoal()) {
                goalNode = curr;
                moves = goalNode.moves;
                break;
            }

            if (twinCurr.board.isGoal()) {
                break;
            }

            for (Board neighborBoard : curr.board.neighbors()) {
                if (curr.previousSearchNode == null || !neighborBoard.equals(curr.previousSearchNode.board)) {
                    SearchNode next = new SearchNode(neighborBoard);
                    next.moves = curr.moves + 1;
                    next.previousSearchNode = curr;
                    minq.insert(next);
                }
            }

            for (Board neighborBoard : twinCurr.board.neighbors()) {
                if (twinCurr.previousSearchNode == null || !neighborBoard.equals(twinCurr.previousSearchNode.board)) {
                    SearchNode next = new SearchNode(neighborBoard);
                    next.previousSearchNode = twinCurr;
                    twinMinq.insert(next);
                }
            }

        }

    }

    public boolean isSolvable() {
        return goalNode != null;
    }

    public int moves() {
        if (isSolvable()) {
            return moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        if (goalNode == null) return null;
        stack.push(goalNode.board);
        SearchNode pointer = goalNode;
        while (pointer.previousSearchNode != null) {
            pointer = pointer.previousSearchNode;
            stack.push(pointer.board);
        }
        return stack;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}