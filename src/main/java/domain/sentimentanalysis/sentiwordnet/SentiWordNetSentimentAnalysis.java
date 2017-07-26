package domain.sentimentanalysis.sentiwordnet;

import domain.postags.PosTags;
import domain.sentimentanalysis.SentimentAnalysis;

import java.util.Map;

/**
 * Does sentiment analysis using the SentiWordNet scheme.
 */
public class SentiWordNetSentimentAnalysis implements SentimentAnalysis {

    private final String sentiwordnetsource = "src/main/resources/sentiwordnet/sentiwordnet3.txt";

    private Map<String, Double> lexicon;

    private PosTags posTags;

//    private Map<String, String> wordPos = new HashMap<>();

    public SentiWordNetSentimentAnalysis() {

        SentiWordNetBuilder sentiWordNetBuilder = new SentiWordNetBuilder(this.sentiwordnetsource);

        this.lexicon = sentiWordNetBuilder.getScoredWords();

        this.posTags = new PosTags();

        // @TODO - move this. Does not really belong here
//        this.wordPos.put("n", "noun");
//        this.wordPos.put("v", "verb");
//        this.wordPos.put("a", "adjective");
//        this.wordPos.put("s", "adjective satellite");
//        this.wordPos.put("r", "adverb");
    }

    /**
     * Does sentiment analysis using the SentiWordNet scheme.
     *
     * @param document String
     * @return Double
     */
    @Override
    public Double getSentiment(String document) {

        Double count      = 0.0;
        Integer wordCount = 0;

        for (String word : document.trim().split(" ")) {

            String[] parts = word.split("_");
            String type = null;

            if (this.posTags.isAdverb(word)) {
                type = "r";
            } else if (this.posTags.isVerb(word)) {
                type = "v";
            } else if (this.posTags.isAdjective(word)) {
                type = "a";
            } else if (this.posTags.isNoun(word)) {
                type = "n";
            }

            if (type == null) {
                continue;
            }

            // @TODO - dont forget about the adjective satellite type

            if (this.lexicon.get(parts[0] + "-" + type) != null) {
                count += this.lexicon.get(parts[0] + "-" + type);
                wordCount++;
            }
        }

        return count / wordCount;
    }
}
