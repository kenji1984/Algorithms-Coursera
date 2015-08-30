import java.util.*;
import java.io.*;

public class Solver {
    private Node process; // holds all the process nodes linked by previous pointer
    private int numMoves;
    private int stacksize;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node previous;
        
        public Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }
        
        public int compareTo(Node other) {
            Integer thisBoard = board.manhattan() + moves;
            Integer otherBoard = other.board.manhattan() + other.moves;
            return thisBoard.compareTo(otherBoard);
        }
    }
    
    /**
     *  Finds the closest neighbors that are connected to the initial node
     *  Makes a previous link as it goes until it reach the goal
     *  Follow the link back to initial node and add all the nodes to a stack
     */
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("initial board is null.");
        
        MinPQ<Node> minQ = new MinPQ<Node>();
        MinPQ<Node> twinQ = new MinPQ<Node>();
        
        process = new Node(initial, null, 0);
        minQ.insert(process);
        
        Node remove = null;
        while (!minQ.isEmpty()) {
            // remove lowest priority node
            process = minQ.deleteMin();
            remove = process;
            
            if (process.board.isGoal()) break;
            
            // iterate through all the neighbors
            for (Board board : process.board.neighbors()) {
                process = new Node(board, remove, remove.moves+1); 
                if (remove.previous != null && process.equals(remove.previous)) continue;
                minQ.insert(process);                   
            }            
        }  
        numMoves = process.moves;
    }
    
    // returns true if calling node and other node is equal
    private boolean equals(Node other) {
        return process.board.equals(other.board);
    }
    
    /**
     *  return true if the node is solvable
     */
    public boolean isSolvable() {
        return true;
    }
    
    public int moves() { return numMoves; }
    
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();
        while (process != null) {
            solution.push(process.board);
            process = process.previous;
            stacksize++;
        }
        return solution;
    }
    
    
    public static void main(String[] args) {
        Scanner sc = null;
        // create initial board from file
        try{
            sc = new Scanner(new File(args[0]), "UTF-8");
        }
        catch (IOException ioe) { System.out.println("Could not open file" + args[0]); }
        
        int N = sc.nextInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = sc.nextInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
        solver.solution();
        System.out.println(solver.stacksize);
        System.out.println("finish");
    }
}