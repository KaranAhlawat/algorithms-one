/* *****************************************************************************
 *  Name: Karan Ahlawat
 *  Date: 14 August 2021
 *  Description: 8puzzle assignment from Algorithms - I course, Board.java class
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int dimension;
    private final int[][] board;

    public Board(int[][] tiles) {
        dimension = tiles.length;

        board = copyBoardContent(tiles);
    }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(dimension() + "\n");

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                boardString.append(board[i][j] + " ");
            }

            boardString.append("\n");
        }

        return  boardString.toString();
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int hamming = 0;

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if(solution(row, col) != 0 && solution(row, col) != board[row][col])
                    hamming++;
            }
        }

        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;

        for(int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                manhattan += distanceToCorrectPosition(board[row][col], row, col);
            }
        }

        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                if (solution(i, j) != board[i][j])
                    return false;
        }

        return true;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        Board otherBoard = (Board) y;

        return Arrays.deepEquals(this.board , otherBoard.board);
    }

    public Board twin() {
        int swappingRow = 0, swappingCol = 0;
        int twinRow = dimension - 1, twinCol = dimension - 1;

        if (board[swappingRow][swappingCol] == 0) {
            swappingCol++;
        }

        if (board[twinRow][twinCol] == 0) {
            twinCol--;
        }

        return copyFromOrignalAndSwap(swappingRow, swappingCol, twinRow, twinCol);
    }

    public Iterable<Board> neighbors() {
        int blankRow = 0, blankCol = 0;
        boolean blankFound = false;
        ArrayList<Board> neighbors = new ArrayList<>();

        for (; blankRow < dimension; blankRow++) {
            for (; blankCol < dimension; blankCol++) {
                if (board[blankRow][blankCol] == 0) {
                    blankFound = true;
                    break;
                }
            }

            if (blankFound) break;
        }

        if (blankRow == dimension) blankRow--;
        if (blankCol == dimension) blankCol--;

        if (blankRow > 0) neighbors.add(copyFromOrignalAndSwap(blankRow, blankCol, blankRow - 1, blankCol));
        if (blankCol > 0) neighbors.add((copyFromOrignalAndSwap(blankRow, blankCol, blankRow, blankCol -1)));
        if (blankRow < (dimension - 1)) neighbors.add(copyFromOrignalAndSwap(blankRow, blankCol, blankRow + 1, blankCol));
        if (blankCol < (dimension - 1)) neighbors.add(copyFromOrignalAndSwap(blankRow, blankCol, blankRow, blankCol + 1));

        return neighbors;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.print(initial.toString());
        System.out.println(initial.manhattan());
        for (Board neighbor:
                initial.neighbors()) {
            System.out.print(neighbor.toString());
        }

    }

    private int distanceToCorrectPosition(int value, int currRow, int currCol) {
        if (value == 0) return 0;

        int correctRow = (value - 1) / dimension;
        int correctCol = (value - 1) % dimension;

        return Math.abs(currRow - correctRow) + Math.abs(currCol - correctCol);
    }

    private int solution(int row, int col) {
        if (row == (dimension - 1) && col == (dimension - 1)) return 0;

        return row * dimension + col + 1;
    }

    private int[][] copyBoardContent(int[][] original) {
        int[][] destination = new int[dimension][dimension];

        for (int row = 0; row < dimension; row++)
            for (int col = 0; col < dimension; col++)
                destination[row][col] = original[row][col];

        return destination;
    }

    private Board copyFromOrignalAndSwap(int originalRow, int originalCol, int newRow, int newCol) {
        int[][] boardCopy = copyBoardContent(board);

        boardCopy[originalRow][originalCol] = board[newRow][newCol];
        boardCopy[newRow][newCol] = board[originalRow][originalCol];

        return new Board(boardCopy);
    }
}
