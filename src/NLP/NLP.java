package NLP;

import java.io.FileNotFoundException;
import java.util.*;

import Maps.*;
import FilesReader.*;

public class NLP
{
    private WordMap map;

    /*You should not use File_Map class in this file since only word hash map is aware of it.
    In fact, you can define the File_Map class as a nested class in Word_Map,
     but for easy evaluation we defined it separately.
     If you need to access the File_Map instances, write wrapper methods in Word_Map class.
    * */

    /*Reads the dataset from the given dir and created a word map */
    public void readDataset(String dir) {
        try {
            map = FilesReader.getWordMap(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void readDataset() {
        try {
            map = FilesReader.getWordMap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /*Finds all the bigrams starting with the given word*/
    public List<String> bigrams(String word){
        ArrayList<String> list = new ArrayList<>();

        FileMap mapOfWord = map.get(word);
        if (mapOfWord == null)
            throw new IllegalArgumentException("Non-existent word");

        HashSet<Map.Entry<String, ArrayList<Integer>>> set = mapOfWord.entrySet();

        for (Map.Entry<String, ArrayList<Integer>> entry : set) {
            for (Integer position : entry.getValue()) {
                Integer searchedPosition = position + 1;
                for (WordMap.Node searchedNode : map) {
                    if (searchedNode.getOccurrences().containsKey(entry.getKey()) &&
                            searchedNode.getOccurrences().get(entry.getKey()).contains(searchedPosition)){
                        String result = word + " " +searchedNode.getWord();
                        if (!list.contains(result))
                            list.add(result);
                        break;
                    }
                }
            }
        }

        return list;
    }


    /*Calculates the tfIDF value of the given word for the given file */
    public float tfIDF(String word, String fileName) {
        FileMap mapOfWord = map.get(word);
        if (mapOfWord == null)
            throw new IllegalArgumentException("Non-existent word");

        ArrayList<Integer> listOfOccurrences = mapOfWord.get(fileName);
        if (listOfOccurrences == null)
            return 0f;

        int termInDoc = listOfOccurrences.size();
        int wordsInDoc = 0;
        for (WordMap.Node current : map){
                FileMap tempMap = current.getOccurrences();
                if (tempMap != null){
                    ArrayList<Integer> tempArr = tempMap.get(fileName);
                    if (tempArr != null)
                        wordsInDoc += tempArr.size();
                }
        }
        float tf = (float)termInDoc / (float)wordsInDoc;


        float idf = (float)Math.log((double) FilesReader.fileCount / (double)mapOfWord.size());
        return tf * idf;
    }

    /*Print the WordMap by using its iterator*/
    public  void printWordMap() {
        for (WordMap.Node current : map) {
            System.out.println("The word \"" + current + "\" was used at:");
            Set<Map.Entry<String, ArrayList<Integer>>> set = current.getOccurrences().entrySet();
            for (Map.Entry<String, ArrayList<Integer>> entry : set){
                System.out.println("\t" + entry.getKey() + ":");
                for (Integer occurance : entry.getValue())
                    System.out.println("\t\t"+occurance);
            }
        }
    }

}
