package domain.sentimentanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Does sentiment analysis using the SentiWordNet scheme.
 */
public class SentiWordNetSentimentAnalysis implements SentimentAnalysis {

    private Map<String, Double> scoredWords = new HashMap<>();

//    private Map<String, String> wordPos = new HashMap<>();

    /**
     * Reads the SentiWordNet file and aggregates each word and POS type to
     * an average score.
     *
     * @param pathToSWN3 String
     * @throws IllegalArgumentException If the SWN3 file doesn't exist
     */
    public SentiWordNetSentimentAnalysis(String pathToSWN3) {

        // @TODO - move this. Does not really belong here
//        this.wordPos.put("n", "noun");
//        this.wordPos.put("v", "verb");
//        this.wordPos.put("a", "adjective");
//        this.wordPos.put("s", "adjective satellite");
//        this.wordPos.put("r", "adverb");

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
                String[] terms   = parts[4].trim().split(" ");

                for (String term : terms) {
                    Double score = Double.parseDouble(parts[2]) - Double.parseDouble(parts[3]);

                    String[] termPos = term.trim().split("#");

                    String termAndType = termPos[0] + "-" + wordType;

                    if (this.scoredWords.containsKey(termAndType)) {

                        Double currentScore = this.scoredWords.get(termAndType);

                        score = (currentScore + score) / 2;
                    }

                    this.scoredWords.put(termAndType, score);
                }
            }

        } catch (IOException ex) {
            // @TODO - log this somewhere useful
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("The SentiWordNet source must be supplied.");
        } finally {

            try {
                if (sentiwordnet != null) {
                    sentiwordnet.close();
                }
            } catch (IOException ex) {
                // @TODO - log this somewhere useful
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Does sentiment analysis using the SentiWordNet scheme.
     *
     * @param document String
     * @return Double
     */
    @Override
    public Double getSentiment(String document) {
        return null;
    }

    /**
     * Returns the Map of words with sentiment scores.
     * @return Map
     */
    public Map<String, Double> getScoredWords() {
        return this.scoredWords;
    }
}
