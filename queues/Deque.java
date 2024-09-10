import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static class Node<T> {
        public Node<T> prev;
        public Node<T> next;
        public T value;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<Item> first;
    private Node<Item> last;
    private int count = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("");
        }

        var newNode = new Node<Item>(item);
        count++;
        if (first == null) {
            first = newNode;
            return;
        }

        newNode.next = first;
        first.prev = newNode;
        first = newNode;

        if (last == null) {
            last = first.next;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("");
        }

        var newNode = new Node<Item>(item);
        count++;
        if (first == null) {
            first = newNode;
            return;
        }

        if (last == null) {
            last = newNode;
            last.prev = first;
            first.next = newNode;
            return;
        }

        newNode.prev = last;
        last.next = newNode;
        last = newNode;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        count--;
        var item = first.value;

        if (first.next == null) { // 1 el in list
            first = null;
            last = null;
        } else if (first.next == last) { // 2 el in list
            first = last;
            first.next = null;
            first.prev = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        count--;
        Item item;
        if (last == null) { // 1 el in list
            item = first.value;
            first = null;
        } else if (first.next == last) { // 2 el in list
            item = last.value;
            first.next = null;
            last = null;
        } else {
            item = last.value;
            last = last.prev;
            last.next = null;
        }

        return item;
    }


    private static class ListIterator<T> implements Iterator<T> {
        private Node<T> current;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            T item = current.value;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        ListIterator(Node<T> initial) {
            current = initial;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // unit testing (required)
    public static void main(String[] args) {
        var instance = new Deque<String>();
        instance.addFirst("first");
        StdOut.println(instance.size());

        instance.addLast("middle");
        StdOut.println(instance.size());

        instance.addLast("last");
        StdOut.println(instance.size());
        StdOut.println(instance.isEmpty());


        // test iterator
        for (String s : instance) {
            StdOut.println(s);
        }

        // drain
        StdOut.println(instance.removeFirst());
        StdOut.println(instance.size());


        StdOut.println(instance.removeFirst());
        StdOut.println(instance.size());
        StdOut.println(instance.removeFirst());
        StdOut.println(instance.size());


        StdOut.println("\nsingle last remove first");
        instance.addLast("last");
        StdOut.println(instance.size());
        StdOut.println(instance.removeFirst());
        StdOut.println(instance.size());
        StdOut.println(instance.isEmpty());


        StdOut.println("\nsingle fist remove last");
        instance.addFirst("first");
        StdOut.println(instance.size());
        StdOut.println(instance.removeLast());
        StdOut.println(instance.size());
        StdOut.println(instance.isEmpty());
    }
}
