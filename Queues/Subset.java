public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        { 
            rq.enqueue(StdIn.readString());
        }
        
        if (k > rq.size()) throw new IndexOutOfBoundsException();
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}