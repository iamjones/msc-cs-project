package domain.aspectwords;

import domain.entity.AspectWords;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds methods to help identify if documents contains an aspect word.
 */
public class AspectWordsMatcher {

    private AspectWords aspectWords;

    /**
     * Holds methods to help identify if documents contains an aspect word.
     *
     * @param aspectWordFilePath String
     */
    public AspectWordsMatcher(String aspectWordFilePath) {

        // Parse the file containing the aspect words into an AspectWords entity
        HadoopAspectWordsParser hadoopAspectWordsParser = new HadoopAspectWordsParser();
        this.aspectWords = hadoopAspectWordsParser.parse(aspectWordFilePath);
    }

    /**
     * Returns any aspect words that are found in the document. There could be more than
     * one aspect word in the document so we need to return a List of them all.
     *
     * @param document String
     * @return List<String>
     */
    public List<String> getMatchedAspectWords(String document) {

        List<String> aspectWords = new ArrayList<>();
        List<String> aw          = this.aspectWords.getAspectWords();

        for (String word : document.split(" ")) {
            if (aw.contains(word)) {
                aspectWords.add(word);
            }
        }

        return aspectWords;
    }
}
