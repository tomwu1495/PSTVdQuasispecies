
/**
 * Write a description of class SeqExtractOneSeq here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SeqExtractOneSeq {
    public static void main(String[] args) {
        String inputSequence = "TTAGCCCTT";
        
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
                    int index = line.indexOf(inputSequence);
                    if (index != -1 && index + inputSequence.length() + 8 <= line.length()) {
                        String bases = line.substring(index + inputSequence.length(), index + inputSequence.length() + 8);
                        String output = "\n" + bases;
                        writer.write(output);
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