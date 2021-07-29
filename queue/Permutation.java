/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> permutations = new RandomizedQueue<String>();

        for (int i = 0; i < k; i++) {
            String temp = StdIn.readString();
            permutations.enqueue(temp);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(permutations.deque());
        }
    }
}
