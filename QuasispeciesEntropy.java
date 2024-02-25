
/**
 * Write a description of class QuasispeciesEntropy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuasispeciesEntropy {
    public static void main(String[] args) {
        String fileName = "output.txt";
        Map<String, Integer> sequenceCounts = new HashMap<>();
        int totalReads = 0;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String currentSequence = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(">")) {
                    // New read, store the previous sequence if present
                    if (!currentSequence.equals("")) {
                        sequenceCounts.put(currentSequence, sequenceCounts.getOrDefault(currentSequence, 0) + 1);
                        totalReads++;
                        currentSequence = "";
                    }
                } else {
                    currentSequence += line;
                }
            }
            // Store the last sequence
            sequenceCounts.put(currentSequence, sequenceCounts.getOrDefault(currentSequence, 0) + 1);
            totalReads++;

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file '" + fileName + "'");
            e.printStackTrace();
        }

        // Calculate the normalized entropy
        double entropy = 0.0;
        for (String sequence : sequenceCounts.keySet()) {
            double frequency = (double) sequenceCounts.get(sequence) / totalReads;
            entropy -= frequency * Math.log(frequency);
        }
        entropy /= Math.log(sequenceCounts.size());

        System.out.println("Total reads: " + totalReads);
        System.out.println("Unique sequences: " + sequenceCounts.size());
        System.out.println("Normalized entropy: " + entropy);
    }
}