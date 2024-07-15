package bstmap;



import java.util.Iterator;
import java.util.Set;


public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {

    private BSTNode root;
    private int size;
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

    public BSTMap(){

    }


    public void clear() {
        root=null;
        size=0;
    }


    public boolean containsKey(K key) {
        return containsKey(root,key);
    }

    private boolean containsKey(BSTNode x, K key) {
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            containsKey(x.left,key);
        } else if (cmp>0) {
            containsKey(x.right,key);
        }else {
            return true;
        }
        return false;
    }

    public V get(K key) {
        return get(root,key);
    }

    private V get(BSTNode x, K key) {
        if (key == null){
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x==null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            get(x.left,key);
        } else if (cmp>0) {
            get(x.right,key);
        }else {
            return x.val;
        }
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
        int cmp = key.compareTo(x.key);
        if (cmp<0){
            put(x.left,key,value);
        } else if (cmp>0) {
            put(x.right,key,value);
        }else {
            x.val = value;
        }
        return x;
    }

    public Set<K> keySet() {
        return (V) new UnsupportedOperationException("keySet is not supported");
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
