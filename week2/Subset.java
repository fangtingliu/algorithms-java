/******************************************************************************
 *  Author:  Fangting Liu
 *  Github:  https://github.com/fangtingprahl
 *  Purpose:  Subset (double-ended) calss
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        int printCounts = Integer.parseInt(args[0]);
        RandomizedQueue<String> store = new RandomizedQueue<String>();
        String curr;
        while (!StdIn.isEmpty()) {
            curr = StdIn.readString();
            store.enqueue(curr);
        }
        while (printCounts > 0) {
            StdOut.println(store.dequeue());
            printCounts--;
        }
    }
}
