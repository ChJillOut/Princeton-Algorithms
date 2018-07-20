import edu.princeton.cs.algs4.StdIn;



public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> stringRQ = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            stringRQ.enqueue(s);

        }
        while (k > 0) {
            System.out.println(stringRQ.dequeue());
            k -= 1;
        }

    }
}

