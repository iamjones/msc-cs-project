package domain.aspectwords;

import domain.entity.AspectWords;

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
        AspectWordsParser aspectWordsParser = new AspectWordsParser();
        this.aspectWords = aspectWordsParser.parse(aspectWordFilePath);
    }

    /**
     * Takes a document and check whether it contains an aspect word.
     *
     * @param document String
     * @return boolean
     */
    public boolean containsAspectWord(String document) {

        for (String aspectWord : this.aspectWords.getAspectWords()) {
            if (document.contains(aspectWord)) {
                return true;
            }
        }

        return false;
    }
}
