package domain.entity.RootMeanSquareError;

import domain.entity.RootMeanSequareError.Aspect;
import domain.entity.RootMeanSequareError.Review;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReviewTest {

    private Review review;

    @Before
    public void setup() {
        this.review = new Review();
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_asin() {
        String asin = "B000PCH3FI";

        this.review.setAsin(asin);

        assertThat(this.review.getAsin(), is(asin));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_reviewer_name() {
        String reviewerName = "John Smith";

        this.review.setReviewerName(reviewerName);

        assertThat(this.review.getReviewerName(), is(reviewerName));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_review_date() {
        String reviewDate = "01 30, 2009";

        this.review.setReviewTime(reviewDate);

        assertThat(this.review.getReviewTime(), is(reviewDate));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_user_rating() {
        Double userRating = 2.0;

        this.review.setUserRating(userRating);

        assertThat(this.review.getUserRating(), is(userRating));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_predicted_rating() {
        Double predictedRating = 2.0;

        this.review.setPredictedRating(predictedRating);

        assertThat(this.review.getPredictedRating(), is(predictedRating));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_overall_sentiment_score() {
        Double overallSentimentScore = 2.0;

        this.review.setOverallSentimentScore(overallSentimentScore);

        assertThat(this.review.getOverallSentimentScore(), is(overallSentimentScore));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_extracted_aspects() {
        Map<Integer, Aspect> extractedAspects = new HashMap<>();
        extractedAspects.put(0, new Aspect());

        this.review.setExtractedAspects(extractedAspects);

        assertThat(this.review.getExtractedAspects(), is(extractedAspects));
    }
}
