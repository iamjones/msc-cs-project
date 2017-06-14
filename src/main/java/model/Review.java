package model;

import java.util.List;

/**
 * A POJO to represent a review.
 */
public class Review {

    private String reviewerID;

    private String asin;

    private String reviewerName;

    private List<Integer> helpful;

    private String reviewText;

    private Double overall;

    private String summary;

    private Integer unixReviewTime;

    private String reviewTime;

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerId) {
        this.reviewerID = reviewerId;
    }

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

    public List<Integer> getHelpful() {
        return helpful;
    }

    public void setHelpful(List<Integer> helpful) {
        this.helpful = helpful;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Double getOverall() {
        return overall;
    }

    public void setOverall(Double overall) {
        this.overall = overall;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getUnixReviewTime() {
        return unixReviewTime;
    }

    public void setUnixReviewTime(Integer unixReviewTime) {
        this.unixReviewTime = unixReviewTime;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}
