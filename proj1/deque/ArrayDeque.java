package deque;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    @SuppressWarnings("unchecked")
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }
    public ArrayDeque(T item){
        items[5] = item;
        nextFirst=4;
        nextLast=6;
        size=1;
    }

    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst--;
        if (nextFirst==-1){
            resize(2*size);
        }
    }

    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast++;
        if (nextLast == items.length){
            resize(2*size);
        }
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        ArrayDeque deque = (ArrayDeque) items[nextFirst+1];
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }
}
