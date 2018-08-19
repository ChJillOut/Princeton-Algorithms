import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Board {
    private int n;
    private int[][] blocks;
    private int[][] goal;
    private int blankRow;
    private int blankCol;

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)

    public Board(int[][] blocks) {
       this.blocks = blocks;
       n = blocks.length;
       int num = 1;
       goal = new int[n][n];
       for (int i = 0; i < n; i++) {
           for (int j =0; j < n; j++) {
               goal[i][j] = num;
               num += 1;
           }
       }
       goal[n-1][n-1] = 0;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int num = 1;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    num += 1;
                    continue;
                }
                if (blocks[i][j] != num) {
                    count += 1;
                }
                num += 1;
            }
        }
        return count;
    }


    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sumDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j =0; j < n; j++) {
                int goalNum = goal[i][j];
                if (blocks[i][j] == goalNum || goalNum == 0) {
                    continue;
                }
                else {
                    int distance = calculateDistance(goalNum, i, j);
                    sumDistance += distance;
                }

            }
        }
        return sumDistance;
    }

    private int calculateDistance(int goalNum, int goalRow, int goalCol) {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j =0; j < n; j++) {
                if (blocks[i][j] == goalNum) {
                    distance = Math.abs(i-goalRow) + Math.abs(j-goalCol);
                    break;
                }
            }
        }
        return distance;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return blocks.equals(goal);
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (blocks[0][0] == 0) {
            int swap = blocks[0][1];
            blocks[0][1] = blocks[1][1];
            blocks[1][1] = swap;
        }
        else {
            int swap = blocks[0][0];
            blocks[0][0] = blocks[0][1];
            blocks[0][1] = swap;
        }
        Board twinBoard = new Board(blocks);
        return twinBoard;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (this.n != that.n) {
            return false;
        }

        if (this.blankCol != that.blankCol) {
            return false;
        }

        if (this.blankRow != that.blankRow) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        List<Board> neighborList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }

        if (blankRow > 0) {
            exchTop(blankRow, blankCol, neighborList);
        }
        if (blankCol > 0) {
            exchLeft(blankRow, blankCol, neighborList);
        }
        if (blankRow < n - 1) {
            exchBottom(blankRow, blankCol, neighborList);
        }
        if (blankCol < n - 1) {
            exchRight(blankRow, blankCol, neighborList);
        }
        return neighborList;
    }

    private int[][] copy(int[][] blocks) {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = blocks[i][j];
            }
        }
        return clone;
    }

    private void exchTop ( int row, int col, List neighborList){
        int[][] neighbor = copy(blocks);
        int swap = neighbor[row - 1][col];
        neighbor[row][col] = swap;
        neighbor[row - 1][col] = 0;
        neighborList.add(new Board(neighbor));
    }

    private void exchBottom ( int row, int col, List neighborList){
        int[][] neighbor = copy(blocks);
        int swap = neighbor[row + 1][col];
        neighbor[row][col] = swap;
        neighbor[row + 1][col] = 0;
        neighborList.add(new Board(neighbor));
    }

    private void exchLeft ( int row, int col, List neighborList){
        int[][] neighbor = copy(blocks);
        int swap = neighbor[row][col - 1];
        neighbor[row][col] = swap;
        neighbor[row][col - 1] = 0;
        neighborList.add(new Board(neighbor));
    }

    private void exchRight ( int row, int col, List neighborList){
        int[][] neighbor = copy(blocks);
        int swap = neighbor[row][col + 1];
        neighbor[row][col] = swap;
        neighbor[row][col + 1] = 0;
        neighborList.add(new Board(neighbor));
    }

    // string representation of this board (in the output format specified below)
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

    // unit tests (not graded)
    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        int hamming = initial.hamming();
        int manhattan = initial.manhattan();
        System.out.println(hamming);
        System.out.println(manhattan);
        Iterable<Board> neighborlist = initial.neighbors();
        for (Board i : neighborlist) {
            String s = i.toString();
            System.out.println(s);
        }
    }
}
