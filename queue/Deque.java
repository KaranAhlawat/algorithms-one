/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterator<Item> {

    private Node first, last;
    private int count;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;

        first = new Node();
        first.item = item;
        first.next = null;
        first.prev = null;

        if (isEmpty()) last = first;
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;

        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = null;

        if (isEmpty()) first = last;
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        count--;
        if (isEmpty()) last = null;
        else           first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        count--;
        if (isEmpty()) first = null;
        else           last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public boolean hasNext() {
        throw new UnsupportedOperationException();
    }

    public Item next() {
        throw new UnsupportedOperationException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();

        System.out.println("IS EMPTY: " + deck.isEmpty());

        for (int i = 0; i < 10; i++) {
            deck.addFirst(i);
            System.out.println("SIZE: " + deck.size());
            System.out.println("IS EMPTY: " + deck.isEmpty());
        }

        System.out.println("Elements 0-9 added. We should have seen 1 to 10 printed");

        for (Integer i : deck) {
            System.out.println(i);
        }

        System.out.println("Finished iterating over the iterator. Elements should appear from 9 to 0.");

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.removeLast());
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 removed. They should appear from 0 to 9.");

        for (int i = 0; i < 10; i++) {
            deck.addLast(i);
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 added.");

        for (Integer i : deck) {
            System.out.println(i);
        }

        System.out.println("Finished iterating over the iterator. Elements should appear from 0 to 9");

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.removeFirst());
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 removed. Elements should appear from 0 to 9");
    }
}
