import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> { // resizing queue
    private int count = 0;
    private Item[] items = (Item[]) new Object[1];

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        this.count++;
        resize();
        if (this.count == 1) {
            items[0] = item;
        } else {
            var newIndex = StdRandom.uniformInt(this.count);
            if (items[newIndex] == null) {
                items[newIndex] = item;
            } else {
                var toMove = items[newIndex];
                items[newIndex] = item;
                items[this.count - 1] = toMove;
            }
        }


    }

    // remove and return a random item
    public Item dequeue() {
        if (this.count == 0) {
            throw new NoSuchElementException();
        }
        var item = items[count - 1];
        items[count - 1] = null;
        this.count--;
        this.resize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.count == 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniformInt(count)];
    }

    private class RQIterator implements Iterator<Item> {
        private Item[] elems = (Item[]) new Object[count]; // 0s
        private int current = 0;

        RQIterator() {
            for (int i = 0; i < count; i++) {
                var place = StdRandom.uniformInt(count);
                if (elems[place] != null) {
                    var val = elems[place];
                    elems[place] = items[i];
                    elems[i] = val;
                } else {
                    elems[i] = items[i];
                }
            }
        }

        public boolean hasNext() {
            return current < elems.length;
        }

        public Item next() {
            if (current >= count) {
                throw new NoSuchElementException();
            }
            Item val = elems[current];
            current++;
            return val;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        var instance = new RandomizedQueue<String>();
        instance.enqueue("s");
        StdOut.println("size " + instance.size());
        StdOut.println(instance.dequeue());
        StdOut.println(instance.isEmpty());

        instance.enqueue("1");
        instance.enqueue("2");
        instance.enqueue("3");
        instance.enqueue("4");
        instance.enqueue("5");
        instance.enqueue("6");
        instance.enqueue("7");
        StdOut.println(instance.size());


        StdOut.println("\n check iterator 1");

        for (String s : instance) {
            StdOut.println(s);
        }

        StdOut.println("\n check iterator 2");

        for (String s : instance) {
            StdOut.println(s);
        }

        StdOut.println("\n check sample");
        StdOut.println(instance.sample());
        StdOut.println(instance.sample());
        StdOut.println(instance.sample());


        StdOut.println("\n check dequeue");
        StdOut.println(instance.dequeue());
        StdOut.println(instance.dequeue());
        StdOut.println(instance.dequeue());
        StdOut.println(instance.dequeue());
        StdOut.println(instance.dequeue());
        StdOut.println(instance.dequeue());
        StdOut.println(instance.dequeue());
        StdOut.println("size " + instance.size());
    }


    private void resize() {
        if (this.count == this.items.length) {
            resizeTo(this.items.length * 2);
        }

        if (this.count <= this.items.length / 4) {
            resizeTo(this.items.length / 2);
        }
    }

    private void resizeTo(int newCapacity) {
        Item[] newItems = (Item[]) new Object[newCapacity];
        for (int i = 0; i < count; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

}
