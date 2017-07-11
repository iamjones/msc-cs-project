package domain.entity;

import java.util.List;

/**
 * Contains a list of aspect words
 */
public class AspectWords {

    private List<String> aspectWords;

    public AspectWords(
        List<String> aspectWords
    ) {
        this.aspectWords = aspectWords;
    }

    public List<String> getAspectWords() {
        return aspectWords;
    }

    public void setAspectWords(
        List<String> aspectWords
    ) {
        this.aspectWords = aspectWords;
    }
}
