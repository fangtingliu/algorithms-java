/******************************************************************************
 *  Author:  Fangting Liu
 *  Github:  https://github.com/fangtingprahl
 *  Purpose:  Deque (double-ended) calss
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item item;
        private Node next = null;
        private Node prev = null;
    }

    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (size == 0) {
            last = first;
        } else {
            oldFirst.prev = first;
            if (size == 1) {
                last.prev = first;
            }
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        if (size == 1) {
            last = null;
            first = null;
        } else  {
            first = first.next;
            if (size == 2) {
                last.prev = null;
            }
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        if (size == 1) {
            last = null;
            first = null;
        } else {
            last = last.prev;
            last.next = null;
            if (size == 2) {
                first.next = null;
            }
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String [] args) {
//        Deque<Integer> deque = new Deque<Integer>();
//        deque.addFirst(0);
//        deque.addFirst(2);
//        deque.addFirst(4);
//        deque.addFirst(6);
//        deque.addFirst(7);
//        deque.addFirst(8);
//        deque.removeLast();
//        deque.addFirst(10);
//        StdOut.println(deque.size());
////        for (int s : deque) {
////            StdOut.println(s);
////        }
//        StdOut.println("remove");
//        deque.removeLast();
//        StdOut.println(deque.size());
    }
}