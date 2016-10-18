/******************************************************************************
 *  Author:  Fangting Liu
 *  Github:  https://github.com/fangtingprahl
 *  Purpose:  Subset (double-ended) calss
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) {
        int printCounts = Integer.parseInt(args[0]);
        RandomizedQueue<String> store = new RandomizedQueue<String>();
        int sizeSoFar = 0;
        while (!StdIn.isEmpty()) {
            sizeSoFar++;
            String curr = StdIn.readString();
            if (store.size() < printCounts) {
                store.enqueue(curr);
            } else {
                int ind = StdRandom.uniform(1, sizeSoFar + 1);
                if (ind <= printCounts) {
                    store.dequeue();
                    store.enqueue(curr);
                }
            }
        }
        while (!store.isEmpty()) {
            StdOut.println(store.dequeue());
            printCounts--;
        }
    }
}
