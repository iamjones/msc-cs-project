package domain.sentimentanalysis;

/**
 * Interface for any class that should do sentiment analysis.
 */
public interface SentimentAnalysis {

    Double getSentiment(String document);
}
