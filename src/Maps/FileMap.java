package Maps;

import java.util.*;

public class FileMap implements Map
{
    /*
    For this hashmap, you will use arraylists which will provide easy but costly implementation.
    Your should provide and explain the complexities for each method in your report.
    * */
    private ArrayList<String> fnames;
    private ArrayList<ArrayList<Integer>> occurrences;

    public FileMap() {
        fnames = new ArrayList<>();
        occurrences = new ArrayList<>();
    }

    @Override
    public int size() {
        return fnames.size();
    }

    @Override
    public boolean isEmpty() {
        return fnames.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return fnames.contains(key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        if (value instanceof ArrayList)
            return occurrences.contains(value);
        return false;
    }

    @Override
    public ArrayList<Integer> get(Object key) {
        int index = fnames.indexOf(key.toString());

        if (index == -1)
            return null;

        return occurrences.get(index);
    }

    @Override
    /*Each put operation will extend the occurrence list*/
    public ArrayList<Integer> put(Object key, Object value) {
        if (!(value instanceof ArrayList) || containsKey(key.toString()))
            throw new IllegalArgumentException();

        fnames.add(key.toString());
        occurrences.add((ArrayList<Integer>) value);
        return (ArrayList<Integer>) value;
    }

    @Override
    public ArrayList<Integer> remove(Object key) {
        int index = fnames.indexOf(key.toString());
        if (index == -1)
            throw new IllegalArgumentException("Removing non-existent item.");
        fnames.remove(index);
        return occurrences.remove(index);
    }

    @Override
    public void putAll(Map m) {
        if (!(m instanceof FileMap))
            throw new IllegalArgumentException();

        int i = 0;
        for (String entry : ((FileMap) m).fnames) {
            if (!this.containsKey(entry)) {
                this.put(entry, ((FileMap) m).occurrences.get(i));
            } else {
                int index = fnames.indexOf(entry);
                for (int current : ((FileMap) m).occurrences.get(index))
                    if (this.occurrences.get(index).indexOf(current) == -1)
                        this.occurrences.get(index).add(current);
            }
        }
    }

    @Override
    public void clear() {
        fnames.clear();
        occurrences.clear();
    }

    @Override
    public Set keySet() {
        return new HashSet<>(fnames);
    }

    @Override
    public Collection values() {
        return new HashSet<>(occurrences);
    }

    @Override
    public HashSet<Entry<String, ArrayList<Integer>>> entrySet() {
        HashSet<Entry<String, ArrayList<Integer>>> set = new HashSet<>();

        for (int i = 0; i < fnames.size(); ++i){
            set.add(new AbstractMap.SimpleEntry<>(fnames.get(i), occurrences.get(i)));
        }
        return set;
    }
}
