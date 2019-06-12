package Maps;

import java.util.Iterator;

public class WordMapIterator implements Iterator<WordMap.Node> {
    private WordMap.Node current;

    WordMapIterator(WordMap map) {
        current = map.getHead();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public WordMap.Node next() {
        WordMap.Node data = current;
        current = current.getNext();
        return data;
    }
}