import java.util.*;

public class BST<Key extends Comparable<Key>, Value> {
	
	private Node root;
	
	private class Node {
		private Key key;
		private Value value;
		private Node left;
		private Node right;
		private int N;
		
		public Node(Key key, Value value, int N) {
			this.key = key;
			this.value = value;
			this.N = N;
		}
	}
	
	/**
	*	Returns the number of nodes in BST
	*/
	public int size() {
		return size(root);
	}
	
	/**
	*	Returns the number of nodes in a given sub tree
	*/
	private int size(Node i) {
		if (i == null) return 0;
		else return i.N;
	}
	
	/**
	*	put a key, value Node into a BST tree
	*	throws java.lang.IllegalArgumentException if key is null
	*/
	public void put(Key key, Value value) {
		if (key == null) throw new NullPointerException("Key is null. Put failed.");
		root = put(root, key, value);
	}
	
	
	/**
	*	Returns the value for the given key
	*	Returns null is key is not found
	*/
	public Value get(Key key) {
		return get(root, key);
	}
	
	public boolean contains(Key key) {
		return get(key) != null;
	}
	
	public boolean isEmpty() {
		return root == null;	
	}
	
	/**
	*	Removes smallest item from BST
	*/
	public void deleteMin() {
		root = deleteMin(root); // set root to the right if root.right becomes null
	}
	
	private Node deleteMin(Node i) {
		// return the right sub tree if i is the min node
		if (i.left == null) return i.right; 
		
		// unwinding stack, connect right sub tree to previous node's left
		i.left = deleteMin(i.left);
		
		// re-update node count
		i.N = size(i.left) + size(i.right) + 1;
		return i;
	}
	
	/**
	*	Removes the largest item in BST
	*/
	public void deleteMax() {
		root = deleteMax(root); // set root to the left if root becomes smallest element
	}
	
	private Node deleteMax(Node i) {
		if (i.right == null) return i.left;
		i.right = deleteMax(i.right);
		i.N = size(i.left) + size(i.right) + 1;
		return i;
	}
	
	/**
	*	Removes the key, value pair from BST
	*/
	public void delete(Key key) {
		root = delete(root, key);
	}
	
	private Node delete(Node i, Key key) {
		if (i == null) return null; // cannot find
		// find the node to be deleted (set i.left or i.right accordingly for unwinding)
		int compare = key.compareTo(i.key);
		if (compare < 0) i.left = delete(i.left, key);
		else if (compare > 0) i.right = delete(i.right, key);
		
		else /* i now points that item to be deleted */ {
			// if i only have left child, return i.left (i.left will be connected to previous i during unwind)
			if (i.right == null) return i.left;
			
			// if i only have right child, return i.right (i.right will be connected to previous i during unwind)
			if (i.left == null) return i.right;
			
			// if i has both child: 
			// 1. save a link to i (item to be deleted)
			Node t = i;
			
			// 2. set i to point to its successor min(t.right). Then connect left and right side of i
			i = min(t.right);
			
			// 3. set i.right to deleteMin(t.right). deleteMin(t.right) returns a subtree with min item removed
			i.right = deleteMin(t.right);
			
			// 4. connect left of i to the left sub tree of the deleted item
			i.left = t.left;
		}		
		i.N = size(i.left) + size(i.right) + 1;
		return i;
	}
	
	/**
	*	Returns the minimum node in the BST
	*/
	public Node min() {
		if (size(root) == 0) throw new NoSuchElementException("min() error. Tree is empty.");
		return min(root);
	}
	
	/**
	*	Returns the minimum node in a subtree
	*/
	private Node min(Node i) {		
		if (i.left == null) return i;
		return min(i.left);
	}
	
	public Node max() {
		if (size(root) == 0) throw new NoSuchElementException("max() error. Tree is empty");
		return max(root);
	}
	
	private Node max(Node i) {
		if (i.right == null) return i;
		return max(i.right);
	}
	
	/**
	*	Returns the number of keys that are less than key
	*/
	public int rank(Key key) {
		return rank(root, key);
	}
	
	private int rank(Node i, Key key) {
		if (i == null) return 0; 
		
		int compare = key.compareTo(i.key);
		if (compare > 0) return size(i.left) + 1 + rank(i.right, key);
		else if (compare < 0) return rank(i.left, key);
		else /* key == i.key */ return size(i.left);
	}
	
	/**
	*	Returns the key of rank k
	*/
	public Key select(int k) {
		return select(root, k);
	}
	
	private Key select(Node i, int k) {
		if (i == null) return null;
		if (size(i.left) == k) return i.key;
		else if (size(i.left) < k) 
			return select(i.right, k - size(i.left) - 1);
		else /* size(i.left) > k */ return select(i.left, k);
	}
	
	/**
	*	Returns the largest key less than or equal to key
	*/
	public Key floor(Key key) {
		return floor(root, key);
	}
	
	private Key floor(Node i, Key key) {
		if (i == null) return null;
		int compare = key.compareTo(i.key);
		
		if (compare < 0) return floor(i.left, key);
		if (compare == 0) return i.key;
		
		// if key > i, there might be an item on the right tree that's < key but > i. so save it to t
		// if there is no more item, then floor must be i
		Key t = floor(i.right, key);

		if (t == null) return i.key;
		else return t;
	}
	
	/**
	*	Returns the smallest key greater than key
	*/
	public Key ceiling(Key key) {
		return ceiling(root, key);
	}
	
	private Key ceiling(Node i, Key key) {
		if (i == null) return null;
		
		int compare = key.compareTo(i.key) ;
		if (compare == 0) return i.key;
		if (compare > 0) return ceiling(i.right, key);
		
		Key t = ceiling(i.left, key);
		if (t == null) return i.key;
		else return t;
	}
	
	/**
	*	Traverse down the right path from root if do not find a math
	*	If i = null, return null
	*	If a match found, return the match value
	*/
	private Value get(Node i, Key key) {
		if (i == null) return null;
		int compare = key.compareTo(i.key);
		
		if (compare < 0) return get(i.left, key);
		else if (compare > 0) return get(i.right, key);
		else return i.value;
	}
	
	/**
	*	Traverse down the right path until current i node becomes null
	*	Return that Node, and while unwinding the stack
	*	Connect the Node from current stack to current-1 stack up until root
	*/
	private Node put(Node i, Key key, Value value) {
		if (i == null) return new Node(key, value, 1);
		
		int compare = key.compareTo(i.key);
		if (compare < 0) i.left = put(i.left, key, value);
		else if(compare > 0) i.right = put(i.right, key, value);
		else i.value = value;
		
		//update N while unwinding stack
		i.N = size(i.left) + size(i.right) + 1;
		return i;
	}
	
	
	/**
	*	Returns an iterator that iterator through keys in breath-first level-order
	*	2 Deques are used so not very space efficient
	*/
	public Iterable<Key> keys() {
		Deque<Key> q = new Deque<Key>();
		levelOrder(q);
		return q;
	}
	
	/**
	*	Starts dequeing root from q, add deque item's children to the q if not null
	*	Keep dequeing until queue becomes empty
	*	For each deque, add deque's 2 children to the queue
	*/
	private void levelOrder(Deque<Key> q) {
		Deque<Node> n = new Deque<Node>();
		n.addLast(root);
		while (!n.isEmpty()) {
			Node t = n.removeFirst();
			q.addLast(t.key);
			if (t.left != null) n.addLast(t.left);
			if (t.right != null) n.addLast(t.right);
		}
	}
	
	// print tree in pre-order traversal
	private void print(Node root) {
		Node i = root;
		if (i == null) return;
		
		System.out.println(i.key + " " + i.value + " " + i.N);
		print(i.left);
		print(i.right);
	}
	
	public static void main(String[] args) {
		BST<String, Integer> bst = new BST<>();
		String[] s = {"S", "E", "X", "A", "R", "C", "H", "M"};
		for (int i = 0; i < s.length; i++) {
			bst.put(s[i], i);	
		}		
		
		bst.print(bst.root);
		System.out.println();
		for(String key: bst.keys()) System.out.println(key);
	}
}