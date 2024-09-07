/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champ = "";
        double index = 0;
        while (!StdIn.isEmpty()) {
            index++;
            var word = StdIn.readString();
            var override = StdRandom.bernoulli(1 / index);
            if (override) {
                champ = word;
            }
        }

        StdOut.println(champ);
    }
}
