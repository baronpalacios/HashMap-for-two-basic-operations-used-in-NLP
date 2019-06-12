package Maps;

import java.util.*;

public class WordMap implements Map, Iterable<WordMap.Node>
{
    public static class Node {
        String word;
        FileMap occurrences;
        Node next;

        private Node(String word, FileMap occurrences) {
            this.word = word;
            this.occurrences = occurrences;
            this.next = null;
        }

        private void connect(Node next) {
            this.next = next;
        }

        public String getWord() {
            return word;
        }

        public FileMap getOccurrences() {
            return occurrences;
        }

        Node getNext() {
            return next;
        }

        @Override
        public String toString() {
            return word;
        }
    }

    private final static int initialCapacity=11;
    private final static float loadFactor = 0.75f;
    private int currentCapacity = initialCapacity;
    private int size = 0;
    private Node[] table;
    private Node head = null;
    private Node lastAdded = null;

    public Node getHead() {
        return head;
    }

    public WordMap() {
        this.table = new Node[initialCapacity];
    }

    private boolean rehashNeeded(){
        return (float)size/(float)currentCapacity >= loadFactor;
    }

    private void rehash(){
        Node[] temp = new Node[currentCapacity];
        int i = 0;
        for (Node current: this) {
            temp[i] = current;
            ++i;
        }

        currentCapacity = currentCapacity * 2 + 1;
        head = null;
        lastAdded = null;
        table = new Node[currentCapacity];

        for (int j = 0; j < i; ++j)
            put(temp[j].getWord(), temp[j].getOccurrences());
    }

    private int calculateHash(String entry){
        return Math.abs(entry.hashCode()) % currentCapacity;
    }

    @Override
    public Iterator<Node> iterator() {
        return new WordMapIterator(this);
    }

    @Override
    public int size() {
        return currentCapacity;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsKey(Object key) {
        for (Node current : this)
            if (current.toString().equals(key.toString()))
                return true;

        return false;
    }

    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsValue(Object value) {
        if (!(value instanceof FileMap))
            return false;

        for (Node current : this)
            if (current.getOccurrences().equals(value))
                return true;

        return false;
    }

    @Override
    public FileMap get(Object key) {
        for (Node current : this)
            if (current.toString().equals(key.toString()))
                return current.getOccurrences();

        return null;
    }

    @Override
    /*
    Use linear probing in case of collision
    * */
    public FileMap put(Object key, Object value) {
        if (!(value instanceof FileMap) || containsKey(key))
            throw new IllegalArgumentException();

        if (rehashNeeded())
            rehash();
        
        Node added = new Node(key.toString(), (FileMap)value);
        int index = calculateHash(key.toString());
        boolean placed = false;
        
        while (!placed) {
            if (table[index] == null){
                table[index] = added;
                placed = true;
            } else {
                index = (index + 1) % currentCapacity;
            }
        }

        if (head == null) {
            head = added;
            lastAdded = added;
        } else {
            lastAdded.connect(added);
            lastAdded = added;
        }

        ++size;
        
        return (FileMap)value;
    }

    @Override
    /*You do not need to implement remove function
    * */
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {
        currentCapacity = initialCapacity;
        table = new Node[currentCapacity];
        head = null;
        lastAdded = null;
    }

    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Set keySet() {
        HashSet<String> set = new HashSet<>();
        for (Node current: this)
            set.add(current.getWord());
        return set;
    }

    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Collection values() {
        HashSet<FileMap> set = new HashSet<>();
        for (Node current: this)
            set.add(current.getOccurrences());
        return set;
    }

    @Override
    /*You do not need to implement entrySet function
     * */
    public HashSet<Entry<String,FileMap>> entrySet() {
        HashSet<Entry<String,FileMap>> set = new HashSet<>();
        for (Node current: this)
            set.add(new AbstractMap.SimpleEntry<>(current.getWord(), current.getOccurrences()));
        return set;
    }
}
