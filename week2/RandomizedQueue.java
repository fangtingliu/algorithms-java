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
    private int n = 0;
    private int size;

    public RandomizedQueue() {
        size = 0;
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
        n = 0;
        for (int i = 0; i < n; i++) {
            if (copy[i] != null) {
                itemArr[n++] = copy[i];
            }
        }
        itemArr = copy;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int ind = StdRandom.uniform(n);
        while (itemArr[ind] == null) {
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
        int ind = StdRandom.uniform(n);
        while (itemArr[ind] == null) {
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

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return itemArr[--i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String [] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(384);
        rq.size();
        rq.dequeue();
        rq.enqueue(144);
        rq.dequeue();
        rq.isEmpty();
        StdOut.println(rq.size());
        StdOut.println("last");
        rq.enqueue(478);
        StdOut.println("size");
        StdOut.println(rq.size());
        for (int s : rq) {
            StdOut.println(s);
        }
    }
}