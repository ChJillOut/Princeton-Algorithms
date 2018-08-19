import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Stack;
import java.util.List;
import java.util.Iterator;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private MinPQ<SearchNode> minPQ;
    private MinPQ<SearchNode> minPQtwin;
    private int moves;
    private Stack<Board> solution = new Stack<>();
    private boolean solvable;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        Board initialTwin = initial.twin();
        SearchNode currSearchNode = new SearchNode(initial);
        SearchNode currSearchNodeTwin = new SearchNode(initialTwin);


        minPQ = new MinPQ<>();
        minPQtwin = new MinPQ<>();

        minPQ.insert(currSearchNode);
        currSearchNode = minPQ.delMin();
        SearchNode prevSearchNode = currSearchNode;


        minPQtwin.insert(currSearchNodeTwin);
        currSearchNodeTwin = minPQtwin.delMin();
        SearchNode prevSearchNodeTwin = currSearchNodeTwin;

        solution.push(currSearchNode.getBoard());


        while (!currSearchNode.getBoard().isGoal() || !currSearchNodeTwin.getBoard().isGoal()) {
            solve(initial, currSearchNode, prevSearchNode, minPQ);
            prevSearchNode = currSearchNode;
            initial = currSearchNode.getBoard();
            currSearchNode = minPQ.delMin();
            solution.push(currSearchNode.getBoard());

            solve(initialTwin, currSearchNodeTwin, prevSearchNodeTwin, minPQtwin);
            prevSearchNodeTwin = currSearchNodeTwin;
            initialTwin = currSearchNodeTwin.getBoard();
            currSearchNodeTwin = minPQtwin.delMin();

            moves += 1;
        }

        if (currSearchNode.getBoard().isGoal()) {
            solvable = true;
        }
        else {
            solvable = false;
        }


    }

    private void solve(Board initial, SearchNode currSearchNode, SearchNode prevSearchNode, MinPQ<SearchNode> minPQ) {

        Iterable<Board> neighbors = initial.neighbors();
        for (Board i: neighbors) {
            if (i.equals(prevSearchNode.getBoard())) {
                continue;
            }
            minPQ.insert(new SearchNode(i));
        }

    }





    private class SearchNode implements Comparable<SearchNode> {
        private Board root;
        private int priority;

        public SearchNode(Board board) {
            this.root = board;
            priority = board.manhattan() + moves;
        }

        public Board getBoard() {
            return root;
        }


        @Override
        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) {
                return -1;
            }
            else if (this.priority > that.priority) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        else {
            return moves;
        }

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new Solution<>();
    }

    private class Solution<Board> implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new SolutionIterator<>();
        }

        private class SolutionIterator<Board> implements Iterator<Board> {

            @Override
            public Board next() {
                return (Board) solution.pop();
            }

            @Override
            public boolean hasNext() {
                while (!solution.isEmpty()) {
                    return true;
                }
                return false;
            }
        }

    }

    // solve a slider puzzle (given below)
    public static void main(String[] args)  {
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
