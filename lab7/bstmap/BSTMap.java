package bstmap;



import java.util.Iterator;
import java.util.Set;


public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {

    private BSTNode root;
    private int size=0;

    private class BSTNode{
        private K key;
        private V val;
        private BSTNode left,right;
        private int size;

        public BSTNode(K key,V val){
            this.key = key;
            this.val = val;
        }

    }



    public void clear() {
        root=null;
        size=0;
    }


    public boolean containsKey(K key) {
        return containsKey(root,key);
    }

    private boolean containsKey(BSTNode x, K key) {
        if(x==null){
            return false;
        }
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            return containsKey(x.left,key);
        } else if (cmp>0) {
            return containsKey(x.right,key);
        }
        return true;
    }

    public V get(K key) {
        return get(root,key);
    }

    private V get(BSTNode x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        }
        return x.val;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        root = put(root,key,value);
        size++;
    }

    private BSTNode put(BSTNode x, K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (x ==null){
            return new BSTNode(key,value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            x.left = put(x.left,key,value);
        } else if (cmp>0) {
            x.right = put(x.right,key,value);
        }else {
            x.val = value;
        }
        return x;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(BSTNode x){
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        System.out.println(x.key.toString() + " -> " + x.val.toString());
        printInOrder(x.right);
    }


    public Set<K> keySet() {
        return (Set<K>) new UnsupportedOperationException("keySet is not supported");
    }

    public V remove(K key) {
        return (V) new UnsupportedOperationException("remove is not supported");
    }

    public V remove(K key, V value) {
        return (V) new UnsupportedOperationException("remove is not supported");
    }

    public Iterator<K> iterator() {
        return (Iterator<K>) new UnsupportedOperationException("iterator is not supported");
    }
}
