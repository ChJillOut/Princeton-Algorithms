import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        public Item item;
        public Node prev;
        public Node next;

    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null argument");
        }

        Node prevNode = first;
        first = new Node();
        first.item = item;
        first.next = prevNode;
        if (isEmpty()) {
            last = first;
        }
        else {
            prevNode.prev = first;
        }

        size += 1;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null argument");
        }

        Node prevNode = last;
        last = new Node();
        last.item = item;

        last.prev=prevNode;
        if (isEmpty()) {
            first = last;
        }
        else {
            prevNode.next = last;
        }

        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item removedItem = first.item;
        first = first.next;
        size -= 1;
        if (isEmpty()) {
            last = null;
        }

        else {
            first.prev = null;
        }

        return removedItem;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item removedItem = last.item;
        last = last.prev;
        size -= 1;
        if (isEmpty()) {
            first=null;
        }

        else {
            last.next=null;
        }


        return removedItem;

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node temp = first;


        @Override
        public boolean hasNext() {
            return (temp != null);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there are no more items to return.");
            }
            Item item = temp.item;
            temp = temp.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(0);
        System.out.println(deque.removeLast());
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());

    }
}
