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
        if (n == itemArr.length) {
            if (size >= itemArr.length / 2) {
                resize(2 * itemArr.length);
            } else {
                resize(itemArr.length);
            }
        }
        itemArr[n++] = item;
        size++;
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
        private int i = n;
        private int count = 0;

        public boolean hasNext() {
            return i > 0 && count < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = itemArr[--i];
            count += 1;
            while (item == null && i > 0) {
                item = itemArr[--i];
            }
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String [] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
//        rq.enqueue(2);
//        rq.enqueue(3);
        rq.size();
        StdOut.println(rq.dequeue());
        StdOut.println(rq.sample());
//        StdOut.println(rq.sample());
//        StdOut.println(rq.sample());
        for (int s : rq) {
            StdOut.println(s);
        }
    }
}