/* *****************************************************************************
 *  Name: Karan Ahlawat
 *  Date: 28-07-2021
 *  Description: A randomized queue implementation using array and resizing.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int count;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        if (count == queue.length) resize(2 * queue.length);
        queue[count++] = item;
    }

    public Item dequeue() {
        if (count == 0) throw new NoSuchElementException("Queue is empty.");
        int randIndex = StdRandom.uniform(count);
        Item item = queue[randIndex];

        queue[randIndex] = queue[count - 1];
        queue[--count] = null;

        if (count == queue.length / 4) resize(queue.length / 2);

        return item;
    }

    public Item sample() {
        if (count == 0) throw new NoSuchElementException("Queue is empty.");
        int randIndex = StdRandom.uniform(count);

        return  queue[randIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private RandomizedQueue<Item> copy;
        
        RandomizedIterator() {
            copy = new RandomizedQueue<Item>();

            for (int i = 0; i < count; i++) {
                copy.enqueue(queue[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Item next() {
            if (copy.isEmpty()) throw new NoSuchElementException("The queue is empty.");

            return copy.dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported.");
        }
    }

    private void resize(int newSize) {
        if (newSize == 0) newSize = 1;
        Item[] temp = (Item[]) new Object[newSize];

        for (int i = 0; i < count; i++) {
            temp[i] = queue[i];
        }

        queue = temp;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();

        StdOut.println("Is empty? : " + test.isEmpty());

        for (int i = 0; i < 10; i++) {
            StdOut.println("Enqueueing :" + i);
            test.enqueue(i);
            StdOut.println("Size of queue: " + test.size());
        }

        StdOut.println("Is empty? : " + test.isEmpty());

        StdOut.println("Random elements: ");
        for (Integer i : test) {
            StdOut.println(i);
        }

        StdOut.println("Random sample : " + test.sample());
    }
}
