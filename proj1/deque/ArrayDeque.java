package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int head;
    private int tail;
    private static final int DEFAULT_CAPACITY = 8;

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        items = (T[]) new Object[DEFAULT_CAPACITY];
        head = 0;
        tail = 0;

    }

    public ArrayDeque(T item) {
        items = (T[]) new Object[DEFAULT_CAPACITY];
        head = 7;
        tail = 0;
        items[head] = item;
        size = 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * DEFAULT_CAPACITY);
        }
        head = (head - 1 + items.length) % items.length;
        items[head] = item;
        size++;

    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * DEFAULT_CAPACITY);
        }
        items[tail] = item;
        tail = (tail + 1) % items.length;
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty())
            return null;
        T item = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty())
            return null;
        tail = (tail - 1 + items.length) % items.length;
        T item = items[tail];
        items[tail] = null;
        size--;
        return item;
    }

    private void resize(int capacity) {
        int newCapacity = items.length * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[(head + i) % items.length];
        }
        items = newArray;
        head = 0;
        tail = size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            int current = (head + i + items.length) % items.length;
            System.out.println(items[current] + " ");
        }
        System.out.println();
    }


    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index > size) {
            throw new RuntimeException("out of index");
        }
        int indexNode = (head + index + items.length) % items.length;
        return items[indexNode];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int current = head;

        @Override
        public boolean hasNext() {
            return current != tail;
        }

        @Override
        public T next() {
            T item = items[current];
            current += 1;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<T> arr1 = (ArrayDeque<T>) o;
        if (this.size != arr1.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (arr1.get(i) != this.get(i)) {
                return false;
            }
        }
        return true;
    }

}
//    public static void main(String[] args) {
//        ArrayDeque<String> deque = new ArrayDeque<>();
//
//        deque.addFirst("A");
//        deque.addLast("B");
//        deque.addFirst("C");
//        deque.addLast("D");
//
//        System.out.println("Deque after additions:");
//        deque.printDeque(); // C A B D
//        System.out.println(deque.get(2));
//        deque.removeFirst();
//        deque.removeLast();
//
//        System.out.println("Deque after removals:");
//        deque.printDeque(); // A B
//    }

