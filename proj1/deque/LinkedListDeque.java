package deque;

import jh61b.junit.In;

public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    private int size;

    public class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(T item){
            this.item = item;
        }
    }

    public  LinkedListDeque(){
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item);
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel;
        int i = size;
        while (i>0){
            p = p.next;
            i--;
            System.out.print(p.item+"-->");
        }
        System.out.println();
    }


    public T removeFirst(){
        if (isEmpty())
            return null;
        Node firstNode = sentinel.next;
        sentinel.next = firstNode.next;
        firstNode.prev = sentinel;
        size--;
        return firstNode.item;
    }

    public T removeLast(){
        if (isEmpty())
            return null;
        Node lastNode= sentinel.prev;
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        size--;
        return lastNode.item;
    }

    public T get(int i){
        if (isEmpty() || i>=size)
            return null;
        Node p = sentinel.next;
        while (i>0){
            p = p.next;
            i--;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node currentNode) {
        if (index == 0) {
            return currentNode.item;
        }
        return getRecursiveHelper(index - 1, currentNode.next);
    }


    public static void main(String[] args) {
        LinkedListDeque deque = new LinkedListDeque();
        deque.addFirst("pan");
        deque.addLast("pan");
        deque.addLast(2);
        deque.addLast(5);
        deque.addLast(0);
        System.out.println(deque.getRecursive(0));

        deque.printDeque();
    }

}
