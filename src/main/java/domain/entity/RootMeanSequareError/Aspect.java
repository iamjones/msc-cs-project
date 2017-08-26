package domain.entity.RootMeanSequareError;

public class Aspect {

    private String aspectWord;

    private Double sentimentScore;

    private String sentence;

    public String getAspectWord() {
        return aspectWord;
    }

    public void setAspectWord(String aspectWord) {
        this.aspectWord = aspectWord;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
