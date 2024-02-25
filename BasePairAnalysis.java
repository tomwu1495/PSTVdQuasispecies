
/**
 * Write a description of class BasePairAnalysis here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BasePairAnalysis {
    public static void main(String[] args) {
        String referenceSequence = "GTGGTTCCGGAACCGC";
        String inputFile = "sequence.txt";
        String outputFile = "basepairs.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String sequence;
            while ((sequence = br.readLine()) != null) {
                Map<String, Integer> basePairCounts = analyzeBasePairs(referenceSequence, sequence);
                writeResults(bw, sequence, basePairCounts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> analyzeBasePairs(String reference, String sequence) {
        Map<String, Integer> basePairCounts = new HashMap<>();

        for (int i = 0; i < reference.length() / 2; i++) {
            char refFirst = reference.charAt(i);
            char refSecond = reference.charAt(reference.length() - 1 - i);
            char seqFirst = sequence.charAt(i);
            char seqSecond = sequence.charAt(sequence.length() - 1 - i);

            String pair = String.valueOf(seqFirst) + String.valueOf(seqSecond);
            String refPair = String.valueOf(refFirst) + String.valueOf(refSecond);

            if (!pair.equals(refPair)) {
                basePairCounts.put(pair, basePairCounts.getOrDefault(pair, 0) + 1);
            }
        }

        return basePairCounts;
    }

    private static void writeResults(BufferedWriter bw, String sequence, Map<String, Integer> basePairCounts)
            throws IOException {
        StringBuilder result = new StringBuilder(sequence + " classic:");
        int classicCount = 0;
        int otherCount = 0;

        for (Map.Entry<String, Integer> entry : basePairCounts.entrySet()) {
            String pair = entry.getKey();
            int count = entry.getValue();

            if (isClassicPair(pair)) {
                classicCount += count;
            } else {
                otherCount += count;
            }
        }

        result.append(classicCount > 0 ? "" + classicCount : "");
        for (Map.Entry<String, Integer> entry : basePairCounts.entrySet()) {
            String pair = entry.getKey();
            int count = entry.getValue();

            if (isClassicPair(pair)) {
                result.append(" ").append(pair).append(":").append(count);
            }
        }

        result.append(" others:").append(otherCount);
        bw.write(result.toString());
        bw.newLine();
    }

    private static boolean isClassicPair(String pair) {
        return pair.equals("AT") || pair.equals("TA") ||
                pair.equals("GC") || pair.equals("CG") ||
                pair.equals("AC") || pair.equals("CA") ||
                pair.equals("GT") || pair.equals("TG");
    }
}