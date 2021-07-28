import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Array describing whether a site is blocked, open, or closed.
    private SiteState[] sites;

    // Weighted quick union grid to keep track of components.
    private final WeightedQuickUnionUF grid, gridWithoutBottom;

    // size of the grid n and number of open sites.
    private int openSites = 0;
    private final int n;

    // Enumeration to indicate status of site as either open or blocked
    private enum SiteState
    {
        BLOCKED, OPEN
    }

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;

        this.sites = new SiteState[n * n + 2];
        this.grid = new WeightedQuickUnionUF(n * n + 2);
        this.gridWithoutBottom = new WeightedQuickUnionUF(n*n + 1);

        sites[0] = SiteState.OPEN;
        sites[n * n + 1] = SiteState.OPEN;
        for (int i = 1; i < n * n + 1; i++) {
           sites[i] = SiteState.BLOCKED;
        }

        for (int i = 1; i <= n; i++) {
            grid.union(0, gridCoord(1, i));
            gridWithoutBottom.union(0, gridCoord(1, i));
        }

        for (int i = 1; i <= n; i++) {
            grid.union(n * n + 1, gridCoord(n, i));
        }
    }


    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) return;

        sites[gridCoord(row, col)] = SiteState.OPEN;
        openSites++;

        if (row - 1 != 0 && isOpen(row - 1, col)) {
            grid.union(gridCoord(row, col), gridCoord(row - 1, col));
            gridWithoutBottom.union(gridCoord(row, col), gridCoord(row - 1, col));
        }

        if (row + 1 != n + 1 && isOpen(row + 1, col)) {
            grid.union(gridCoord(row, col), gridCoord(row + 1, col));
            gridWithoutBottom.union(gridCoord(row, col), gridCoord(row + 1, col));
        }

        if (col - 1 != 0 && isOpen(row, col - 1)) {
            grid.union(gridCoord(row, col), gridCoord(row, col - 1));
            gridWithoutBottom.union(gridCoord(row, col), gridCoord(row, col - 1));
        }

        if (col + 1 != n + 1 && isOpen(row, col + 1)) {
            grid.union(gridCoord(row, col), gridCoord(row, col + 1));
            gridWithoutBottom.union(gridCoord(row, col), gridCoord(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return sites[gridCoord(row, col)] == SiteState.OPEN;
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return (gridWithoutBottom.find(gridCoord(row, col)) == gridWithoutBottom.find(0)) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {

        // taking care of the case where n = 1. In this case, the weighted union
        // would return that top and bottom are connected even if the only site
        // in the grid is not open
        if (n == 1 && !isOpen(1, 1)) return false;

        return grid.find(0) == grid.find(n*n + 1);
    }

    private int gridCoord(int row, int col) {
        return (row - 1) * n + col;
    }
}
