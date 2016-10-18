/******************************************************************************
 *  Author:  Fangting Liu
 *  Github:  https://github.com/fangtingprahl
 *  Purpose:  RandomizedQueue (double-ended) calss
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemArr;
    private int n;
    private int size;

    public RandomizedQueue() {
        size = 0;
        n = 0;
        itemArr = (Item[]) new Object[1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        itemArr[n++] = item;
        size++;
        if (n == itemArr.length) {
            if (size >= itemArr.length / 2) {
                resize(2 * itemArr.length);
            } else {
                resize(itemArr.length);
            }
        }
    }

    private void resize(int l) {
        Item[] copy = (Item[]) new Object[l];
        int p = 0;
        for (int i = 0; i < n; i++) {
            if (itemArr[i] != null) {
                copy[p++] = itemArr[i];
            }
        }
        itemArr = copy;
        n = p;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int ind = StdRandom.uniform(n);
        if (n == 0)
            ind = 0;
        while (itemArr[ind] == null && n > 0) {
            ind = StdRandom.uniform(n);
        }
        Item item = itemArr[ind];
        itemArr[ind] = null;
        size--;
        if (ind == n - 1) {
            n--;
        }
        if (size <= itemArr.length / 4) {
            resize(itemArr.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int ind = StdRandom.uniform(n);
        if (n == 0)
            ind = 0;
        while (itemArr[ind] == null && n > 0) {
            ind = StdRandom.uniform(n);
        }
        Item item = itemArr[ind];
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int count = 0;
        private boolean[] used = new boolean[n];

        public boolean hasNext() {
            return count < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int ind = StdRandom.uniform(n);
            if (n == 0)
                ind = 0;
            while ((itemArr[ind] == null && n > 0) || used[ind]) {
                ind = StdRandom.uniform(n);
            }
            used[ind] = true;
            Item item = itemArr[ind];
            count++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String [] args) {
//        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
//        rq.isEmpty();
//        rq.size();
//        rq.isEmpty();
//        rq.enqueue(30);
//        rq.sample();
//        rq.sample();
//        rq.dequeue();
//        rq.isEmpty();
//        rq.isEmpty();
//        rq.size();
//        rq.size();
//        rq.enqueue(14);
//        for (int s : rq) {
//            StdOut.println(s);
//        }
    }
}