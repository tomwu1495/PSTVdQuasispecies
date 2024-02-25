
/**
 * Write a description of class SequenceCounter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SequenceCounter {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line;
            Map<String, Integer> sequenceCounts = new HashMap<>();
            StringBuilder currentSequence = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">")) {
                    String sequence = currentSequence.toString();
                    if (!sequence.isEmpty()) {
                        if (!sequenceCounts.containsKey(sequence)) {
                            sequenceCounts.put(sequence, 0);
                        }
                        sequenceCounts.put(sequence, sequenceCounts.get(sequence) + 1);
                    }
                    currentSequence = new StringBuilder();
                } else {
                    currentSequence.append(line);
                }
            }
            String lastSequence = currentSequence.toString();
            if (!lastSequence.isEmpty()) {
                if (!sequenceCounts.containsKey(lastSequence)) {
                    sequenceCounts.put(lastSequence, 0);
                }
                sequenceCounts.put(lastSequence, sequenceCounts.get(lastSequence) + 1);
            }
            reader.close();
            List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(sequenceCounts.entrySet());
            sortedList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
            for (Map.Entry<String, Integer> entry : sortedList) {
                String sequence = entry.getKey();
                int count = entry.getValue();
                System.out.println(sequence + ": " + count + " reads");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}