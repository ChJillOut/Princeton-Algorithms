import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        a = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (isFull()) {
            resize(a.length * 2);
        }

        a[size] = item;
        size += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
        int index = StdRandom.uniform(size);
        Item removedItem = a[index];

        a[index] = a[size-1];
        a[size-1] = null;
        size -= 1;
        if ((double) size < a.length/4) {
            shrink(a.length/2);
        }

        return removedItem;
    }

    private boolean isFull() {
        return (size == a.length);
    }

    private void resize(int capacity) {
        Item[] b = (Item[]) new Object[capacity];
        System.arraycopy(a, 0, b, 0, size);
        a = b;
    }

    private void shrink(int capacity) {
        Item[] b = (Item[]) new Object[capacity];
        System.arraycopy(a, 0, b, 0, size);
        a = b;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
        int index = StdRandom.uniform(size);
        return a[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private int count = 0;
        private final Item[] b;

        private RandomizedQueueIterator() {
            b = (Item[]) new Object[size];
            System.arraycopy(a, 0, b, 0, size);
            StdRandom.shuffle(b);
        }


        @Override
        public boolean hasNext() {
            return (count < size);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            Item nextItem = b[count];
            count += 1;
            return nextItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

