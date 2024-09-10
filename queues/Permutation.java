import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        var len = Integer.parseInt(args[0]);
        var rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        var iterator = rq.iterator();

        for (int i = 0; i < len; i++) {
            StdOut.println(iterator.next());
        }
    }
}

