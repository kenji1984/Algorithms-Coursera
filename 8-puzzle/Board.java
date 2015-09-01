import java.util.*;
import java.io.*;

public class Board {
    private int[][] blocks;
    private int N;
    
    public Board(int[][] blocks) {
        N = blocks[0].length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    
    public int dimension() { return N; }
    
    /**
     *  returns the number of blocks in the wrong position
     */
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int block = blocks[i][j]; 
                
                // if block at wrong place, add 1 to sum
                if (block != 0 && block != i*N + j+1) sum++;  
            }
        }
        return sum;
    }
    
    /**
     *  returns the total distance of all blocks from their positions
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = blocks[i][j];
                if (num == 0) continue;
                int actual_row = (num-1) / N;
                int actual_col = (num-1) % N;
                
                // add difference between actual row and column vs current row and col
                sum += Math.abs(actual_row - i) + Math.abs(actual_col - j);
            }
        }
        return sum;
    }
    
    /**
     *  returns true if the total distance of all the blocks from their position is 0
     */
    public boolean isGoal() {
        return manhattan() == 0;
    }
    
    /**
     *  return an invalid(non-zero) neighbor 
     *  valid neighbor can only be obtain by switching 0 block
     */
    public Board twin() {        
        if (N < 2) return this;
        
        // neighbor the first 2 item in row 0 if 0 isn't in there
        if (!zeroInRow(0)) return neighbor(0, 0, 1, "row");
        else return neighbor(1, 0, 1, "row"); // else neighbor first 2 items from row 1        
    }
    
    // only need to check first 2 items for 0
    private boolean zeroInRow(int row) {
        for (int i = 0; i < 2; i++) {
            if (blocks[row][i] == 0) return true;
        }
        return false;
    }
    
    /**  return neighbor by swapping blocks adjacent to the 0 block
      *  intput: r      - the row or column number to switch on
      *          i, j   - the position of the 2 blocks
      *          swapBy - by row or by col
      *  Note to self: this function alone takes N^2 time complexity
      */
    private Board neighbor(int r, int i, int j, String swapBy) {
        Board board = new Board(blocks);
        if (swapBy.equals("row")) {
            int temp = board.blocks[r][i];
            board.blocks[r][i] = board.blocks[r][j];
            board.blocks[r][j] = temp;
        }         
        else if (swapBy.equals("col")) {
            int temp = board.blocks[i][r];
            board.blocks[i][r] = board.blocks[j][r];
            board.blocks[j][r] = temp;            
        }
        return board;
    }
    
    /**
     *  returns true if 2 boards are equal
     */
    public boolean equals(Object y) {
        return this.toString().equals(y.toString());
    }
    
    /**
     *  returns a queue of all the board's neighbors as a queue
     */
    public Iterable<Board> neighbors() {
        
        int i = 0;
        int j = 0;
        loopouter: {
            for (i = 0; i < N; i++) {
                for (j = 0; j < N; j++) {
                    if (blocks[i][j] == 0) break loopouter;
                }
            }
        }
        
        java.util.Stack<Board> neighbors = new java.util.Stack<Board>();
        // left blocks
        if (!invalidBlock(i, j-1)) {
            neighbors.push(neighbor(i, j, j-1, "row"));
        }
        if (!invalidBlock(i, j+1)) {
            neighbors.push(neighbor(i, j, j+1, "row"));
        }
        if (!invalidBlock(i-1, j)) {            
            neighbors.push(neighbor(j, i-1, i, "col"));
        }
        if (!invalidBlock(i+1, j)) {            
            neighbors.push(neighbor(j, i+1, i, "col"));            
        }
        return neighbors;
    }
    
    // check where blocks[i][j] is valid
    private boolean invalidBlock(int i, int j) {
        return i < 0 || j < 0 || i >= N || j >= N;
    }
    
    // string representation of board
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%3d", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        int[][] b = {{4,1,3}, {2,0,5}, {7,8,6}};
        int[][] c = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(b);
        Board board2 = new Board(c);
        System.out.println(board.toString());
        System.out.print(board.manhattan()+ " " + board.isGoal());
        System.out.println();
        System.out.println(board2.manhattan() + " " + board2.isGoal());
        System.out.println("twin: ");
        Board d = board.twin();
        Board a = board2.twin();
        System.out.println(d.toString());
        System.out.println(a.toString());
        System.out.println(board.equals(d.twin()));
        
        System.out.println("neighbors:");
        for (Board bd : board.neighbors()) { System.out.println(bd); }
    }
}