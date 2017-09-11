package domain.sentimentanalysis.sentiwordnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for processing the SentiWordNet 3 lexicon into a map of
 * term -> score so it can be used for sentiment analysis.
 */
public class SentiWordNetBuilder {

    private Map<String, Double> scoredWords = new HashMap<>();

    /**
     * Reads the SentiWordNet file and aggregates each word and POS type to
     * an average score.
     *
     * @throws IllegalArgumentException If the SWN3 file doesn't exist
     */
    public SentiWordNetBuilder(String pathToSWN3) {

        BufferedReader sentiwordnet = null;

        try {

            sentiwordnet = new BufferedReader(new FileReader(pathToSWN3));

            String line;
            while ((line = sentiwordnet.readLine()) != null) {

                if (line.trim().startsWith("#")) {
                    continue;
                }

                String[] parts  = line.trim().split("\t");
                String wordType = parts[0];
                String[] terms  = parts[4].trim().split(" ");

                for (String term : terms) {
                    Double score       = Double.parseDouble(parts[2]) - Double.parseDouble(parts[3]);
                    String[] termPos   = term.trim().split("#");
                    String termAndType = termPos[0] + "-" + wordType;

                    if (this.scoredWords.containsKey(termAndType)) {

                        Double currentScore = this.scoredWords.get(termAndType);

                        score = (currentScore + score) / 2;
                    }

                    this.scoredWords.put(termAndType, score);
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("The SentiWordNet source must be supplied.");
        } finally {

            try {
                if (sentiwordnet != null) {
                    sentiwordnet.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Returns the Map of words with sentiment scores.
     * @return Map
     */
    public Map<String, Double> getScoredWords() {
        return this.scoredWords;
    }
}
