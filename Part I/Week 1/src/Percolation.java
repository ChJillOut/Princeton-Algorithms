
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int count;
    private final int size;
    private boolean[][] status;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufTop;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        status = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        size = n;

    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * size + col;
    }



    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > size || col > size || row <= 0 || col <= 0) {
            throw new IllegalArgumentException("row or column index is out of range.");
        }
        else {
            if (!isOpen(row, col)) {
                status[row - 1][col - 1] = true;
                connectOpenSites(row, col);
                count += 1;
            }

        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > size || col > size || row <= 0 || col <= 0) {
            throw new IllegalArgumentException("row or column index is out of range.");
        }

        return status[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > size || col > size || row <= 0 || col <= 0) {
            throw new IllegalArgumentException("row or column index is out of range.");
        }
        int p = xyTo1D(row, col);
        return ufTop.connected(p, 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (size == 1) {
            return isOpen(1, 1);
        }
        return uf.connected(0, size * size + 1);
    }

    // check any open neighbors around the current open site and union them together
    private void connectOpenSites(int row, int col) {
        int p;
        int q = xyTo1D(row, col);

        if (row == 1) {
            uf.union(q, 0);
            ufTop.union(q, 0);
        }
        if (row == size) {
            uf.union(q, size * size + 1);
        }

        if (row < size && isOpen(row + 1, col)) {
            p = xyTo1D(row + 1, col);
            uf.union(p, q);
            ufTop.union(p, q);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            p = xyTo1D(row - 1, col);
            uf.union(p, q);
            ufTop.union(p, q);
        }

        if (col < size && isOpen(row, col + 1)) {
            p = xyTo1D(row, col + 1);
            uf.union(p, q);
            ufTop.union(p, q);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            p = xyTo1D(row, col - 1);
            uf.union(p, q);
            ufTop.union(p, q);
        }

    }


    // test client (optional)
    public static void main(String[] args) {
        Percolation test = new Percolation(2);
        test.open(1, 1);
        test.open(2, 2);
        System.out.println(test.isFull(1, 1));
        System.out.println(test.percolates());
        System.out.println(test.numberOfOpenSites());

    }
}
