package domain.entity.RootMeanSequareError;

import org.apache.htrace.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

    private String asin;

    private String reviewerName;

    private String reviewTime;

    private Double userRating;

    private Double predictedRating;

    private Double overallSentimentScore;

    private Map<Integer, Aspect> extractedAspects;

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Double getPredictedRating() {
        return predictedRating;
    }

    public void setPredictedRating(Double predictedRating) {
        this.predictedRating = predictedRating;
    }

    public Double getOverallSentimentScore() {
        return overallSentimentScore;
    }

    public void setOverallSentimentScore(Double overallSentimentScore) {
        this.overallSentimentScore = overallSentimentScore;
    }

    public Map<Integer, Aspect> getExtractedAspects() {
        return extractedAspects;
    }

    public void setExtractedAspects(Map<Integer, Aspect> extractedAspects) {
        this.extractedAspects = extractedAspects;
    }
}
