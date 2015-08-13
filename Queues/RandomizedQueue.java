import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int numItems; // number of items inside queue
    private int capacity; // max capacity of the queue
    private Item[] items; // array to store queue items
    
    public RandomizedQueue() {
        numItems = 0;
        capacity = 1;
        items = (Item[]) new Object[capacity];
    }
    
    public boolean isEmpty() { return numItems == 0; }
    
    public int size() { return numItems; }
    
    
    /**
     *  Add an item to the tail of the RandomizedQueue
     *  Throws java.lang.NullPointerException when trying to add null item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (numItems == capacity) resize(capacity * 2);
        
        items[numItems++] = item;
    }
    
    
    /**
     *  Removes and return a random item 
     *  Throws java.util.NoSuchElementException when dequeue and empty queue
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        if (numItems > 0 && numItems <= (capacity / 3)) resize(capacity / 2);
        
        int rand = StdRandom.uniform(numItems);
        
        Item item = items[rand];
        items[rand] = items[--numItems];
        items[numItems] = null;
        return item;
    }
    
    
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        
        int rand = StdRandom.uniform(numItems);
        return items[rand];
    }
    
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        for (int i = 1; i <= 5; i++) {
            d.enqueue(i);
            d.enqueue(i + 5);
        }
        
        //test for iterator independence
        for (int i : d) {
            for (int j : d) System.out.print(j + " ");
            System.out.println('\n');
        }
        System.out.println("Dequeuing...");
        while (!d.isEmpty()) System.out.print(d.dequeue() + " ");
    }
    
    /**
     *  Resize items array to size newLength
     */
    private void resize(int newLength) {
        // keep reference to the old items
        Item[] old = items;
        
        // create a new array of items with the newLength
        items = (Item[]) new Object[newLength];
        
        // copy old items into items
        for (int i = 0; i < numItems; i++) {
            items[i] = old[i];
        }
        capacity = newLength;
    }
    
    
    /**
     *  Returns an iterator that iterates through items in random order 
     */
    public Iterator<Item> iterator() { return new RandomIterator(); }
    
    private class RandomIterator implements Iterator<Item> {
        
        private int i = 0;
        private int[] randIndexes = new int[numItems]; 
        
        /**
         *  Construct an independent instance of RandomizedQueue Iterator
         */
        public RandomIterator() {
            generateRandomIndexes();
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
        public boolean hasNext() { return i != randIndexes.length; }
        
        /**
         *  Return the next item in the RandomizedQueue
         *  Throws java.util.NoSuchElementException if i is at tail
         */
        public Item next() { 
            if (!hasNext()) throw new NoSuchElementException();
            Item item = items[randIndexes[i++]];
            return item;
        }
        
        /**
         *  Generate an shuffled array of int from 0 to numItems
         */
        private void generateRandomIndexes() {
            // perform knuth shuffle algorithm to generate randomIndexes array
            for (int j = 0; j < numItems; j++) {
                randIndexes[j] = j;
                int rand = StdRandom.uniform(j + 1);
                swap(randIndexes, j, rand);
            }
        }
        
        private void swap(int[] a, int j, int k) {
            int temp = a[j];
            a[j] = a[k];
            a[k] = temp;
        }
    }
}