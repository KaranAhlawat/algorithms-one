/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {

    private BoardNode solutionBoard;
    private boolean solvable = true;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board cannot be null.");

        MinPQ<BoardNode> gameTree = new MinPQ<>();
        BoardNode initialNode = new BoardNode(initial, null, 0);
        gameTree.insert(initialNode);

        MinPQ<BoardNode> twinTree = new MinPQ<>();
        BoardNode initialTwinNode = new BoardNode(initial.twin(), null, 0);
        twinTree.insert(initialTwinNode);

        while (solutionBoard == null && isSolvable()) {
            BoardNode currentNode = gameTree.delMin();
            if (currentNode.board.isGoal()) {
                solutionBoard = currentNode;
                return;
            }

            for (Board neighbor : currentNode.board.neighbors()) {
                if (currentNode.previousBoard != null && neighbor.equals(currentNode.previousBoard.board))
                    continue;
                else
                    gameTree.insert(new BoardNode(neighbor, currentNode, currentNode.moves + 1));
            }

            BoardNode twinNode = twinTree.delMin();
            if (twinNode.board.isGoal()) {
                solvable = false;
                return;
            }

            for (Board twinNeigbor : twinNode.board.neighbors()) {
                if (twinNode.previousBoard != null && twinNeigbor.equals(twinNode.previousBoard.board)) {
                    continue;
                }
                else {
                    twinTree.insert(new BoardNode(twinNeigbor, twinNode, twinNode.moves + 1));
                }
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) return -1;

        return solutionBoard.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Board[] pathToSolution = new Board[moves() + 1];
        int i = moves();
        BoardNode currentNode = solutionBoard;

        while (i >= 0) {
            pathToSolution[i--] = currentNode.board;
            currentNode = currentNode.previousBoard;
        }

        return new ArrayList<>(Arrays.asList(pathToSolution));
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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

    private class BoardNode implements Comparable<BoardNode> {
        public Board board;
        public BoardNode previousBoard;
        public int moves;
        public Integer cachedPriority;

        public BoardNode(Board board, BoardNode previousBoard, int moves) {
            this.board = board;
            this.previousBoard = previousBoard;
            this.moves = moves;
        }

        public int manhattanPriority() {
            if (cachedPriority == null) cachedPriority = moves + board.manhattan();
            return cachedPriority;
        }

        public int compareTo(BoardNode other) {
            return this.manhattanPriority() - other.manhattanPriority();
        }
    }
}
