import NLP.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        // ENTER THE DIRECTORY OF THE DATASET BELOW
        String dir = "";

        try {
            parseInput(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void parseInput(String dir) throws FileNotFoundException {
        NLP nlp = new NLP();

        if (dir.equals(""))
            nlp.readDataset();
        else
            nlp.readDataset(dir);

        File input = new File("./input.txt");
        Scanner parser = new Scanner(input);

        while (parser.hasNextLine()){
            String command = parser.next();
            System.out.println(command.equals("bigram") ? nlp.bigrams(parser.next()).toString() : nlp.tfIDF(parser.next(), parser.next()));
        }

    }
}
