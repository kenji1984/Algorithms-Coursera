import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private int numItems; // number of items in deque
    private Node first;  // first item of deque
    private Node last;  // last item of deque
    
    private class Node {
        private Node next;
        private Node previous;
        private Item data;
    }
    
    public Deque() {
        init();
    }
    
    private void init() {
        numItems = 0;
        first = null;
        last = null;
    }
    
    public boolean isEmpty() { return numItems == 0; }
    
    public int size() { return numItems; }
    
    /**
     *  Add item to front of deque (cutting in line)
     *  Throws java.lang.NullPointerException if null item is added
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Adding null item");
        
        Node newNode = new Node();
        newNode.data = item;
        
        if (first == null) first = last = newNode;
        else {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        }
        numItems++;
    }
    
    /**
     *  Add item to end of deque
     *  Throws java.lang.NullPointerException if null item is added
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Adding null item");
        
        Node newNode = new Node();
        newNode.data = item;
        if (last == null) last = first = newNode;
        else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        numItems++;
    }
    
    /**
     *  Remove item from front of deque
     *  Throws NoSuchElementException if trying to remove item from an empty deque
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Cannot remove. Deque is empty.");
        
        Item item = first.data;
        if (numItems == 1) {
            init(); // resetting all variables to 0
            return item;
        }
        first = first.next;
        first.previous = null;
        numItems--;
        return item;
    }
    
    /**
     *  Remove item from end of deque
     *  Throws NoSuchElementException if deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item item = last.data;
        if (numItems == 1) {
            init(); // resetting all variables to 0
            return item;
        }
        last = last.previous;
        last.next = null;
        numItems--;
        return item;
    }
    
    /**
     *  Returns an iterator to this deque that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        
        private Node current = first;
        
        public void remove() { throw new UnsupportedOperationException(); }
        public boolean hasNext() { return current != null; }
        
        /**
         *  Return the next element after the current item
         *  Throws java.util.NoSuchElementException if current is the last item in deque
         */
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.data;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 1; i <= 5; i++) {
            d.addFirst(i);
            d.addLast(5 + i);
        }
        
        for (int i : d) System.out.print(i + " ");
        System.out.println();
        while (!d.isEmpty()) System.out.print(d.removeLast() + " ");
        //d.removeFirst();
    }
}