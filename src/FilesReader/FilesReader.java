package FilesReader;

import Maps.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesReader {
    public static int fileCount = 0;

    public static WordMap getWordMap() throws FileNotFoundException {
        return getWordMap("./dataset");
    }

    public static WordMap getWordMap(String dir) throws FileNotFoundException {
        File folder = new File(dir);
        File[] files = folder.listFiles();
        WordMap map = new WordMap();

        if (files == null)
            return null;

        fileCount = files.length;

        for (File current : files) {

            Scanner input = new Scanner(current);
            String fileName = current.getName();
            int wordOrder = 0;

            while (input.hasNext()){
                String word = input.next();
                word = word.trim().replaceAll("\\p{Punct}", "");

                //ignore empty strings
                if (!word.equals("")) {
                    wordOrder++;

                    if (!map.containsKey(word))
                        map.put(word, new FileMap());
                    if (!map.get(word).containsKey(fileName))
                        map.get(word).put(fileName, new ArrayList<Integer>());
                    map.get(word).get(fileName).add(wordOrder);
                }
            }
        }
        return map;
    }
}
