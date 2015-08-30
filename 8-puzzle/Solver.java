public class Solver {
    private Node process; // node contain a board, number of moves and link to previous node
    private Stack<Board> solution;  // stack with all the nodes from goal node to initial node
    private int minMoves;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node previous;
        
        public Node(Board board, int moves) {
            this.board = board;
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
        process = new Node(initial, 0);
        minQ.insert(process);
        
        Node remove = null;
        while (!minQ.isEmpty()) {
            // remove lowest priority node
            process = minQ.deleteMin();
            
            // keep the connection to previous nodes
            process.previous = remove; 
            remove = process;
            
            if (process.board.isGoal()) break;
            
            // iterate through all the neighbors
            for (Board board : process.board.neighbors()) {
                process = new Node(board, remove.moves+1);                 
                if (!process.equals(remove.previous)) {
                    minQ.insert(process);   
                }
            }            
        }
        minMoves = process.moves;
        
        // we have found a solution at process. Moving from process back, adding to stack
        solution = new Stack<Board>();
        while (process != null) { 
            solution.push(process.board); 
            process = process.previous; 
        }
    }
    
    // returns true if calling node and other node is equal
    private boolean equals(Node other) {
        return process.compareTo(other) == 0;
    }
    
    /**
     *  return true if the node is solvable
     */
    public boolean isSolvable() {
        return true;
    }
    
    public int moves() { return process.moves; }
    
    public Iterable<Board> solution() {
        return solution;
    }
    
    public static void main(String[] args) {
        Board a = new Board(new int[][]{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}});
        Solver solver = new Solver(a);
        for (Board board : solver.solution()) {
            System.out.println("min moves: " + solver.minMoves + "\n" + board);
            System.out.println();
        }
        System.out.println("finish..");
    }
}