
/**
 * Write a description of class SeqExtractTwoSeqs here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SeqExtractTwoSeqs {
    public static void main(String[] args) {
        String inputSequence1 = "CTAAACTC";
        String inputSequence2 = "TAGCCCTT";

        try {
            BufferedReader reader = new BufferedReader(new FileReader("sequencingData.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            String line;
            String sequenceID = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">")) {
                    if (sequenceID != null) {
                        writer.newLine();
                    }
                    sequenceID = line;
                    writer.write(sequenceID);
                } else if (sequenceID != null) {
                    int index1 = line.indexOf(inputSequence1);
                    int index2 = line.indexOf(inputSequence2);
                    String bases1;
                    String bases2;
                    if (index1 != -1 && index2 != -1) {
                        int endIndex1 = index1 + inputSequence1.length() + 8;
                        int endIndex2 = index2 + inputSequence2.length() + 8;
                        if (endIndex1 <= line.length() && endIndex2 <= line.length()) {
                            bases1 = line.substring(index1 + inputSequence1.length(), endIndex1);
                            bases2 = line.substring(index2 + inputSequence2.length(), endIndex2);
                            String output = "\n" + bases1 + bases2;
                            writer.write(output);
                        }
                    }
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}