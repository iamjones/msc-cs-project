package domain.sentimentscore;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class SentimentScoreTest {

    private SentimentScore sentimentScore;

    @Before
    public void setup() {
        this.sentimentScore = new SentimentScore();
    }

    @Test
    public void it_should_calculate_a_positive_overall_sentiment_correctly() {
        Double positive = 1.2;
        Double negative = 1.0;

        Double overallSentiment = this.sentimentScore.calculateOverallSentiment(positive, negative);

        assertThat(overallSentiment, greaterThan(0.0));
    }

    @Test
    public void it_should_calculate_a_negative_overall_sentiment_correctly() {
        Double positive = 0.4;
        Double negative = 1.0;

        Double overallSentiment = this.sentimentScore.calculateOverallSentiment(positive, negative);

        assertThat(overallSentiment, lessThan(0.0));
    }

    @Test
    public void it_should_calculate_a_neutral_overall_sentiment_correctly() {
        Double positive = 0.0;
        Double negative = 0.0;

        Double overallSentiment = this.sentimentScore.calculateOverallSentiment(positive, negative);

        assertThat(overallSentiment, is(0.0));
    }

    @Test
    public void it_should_calulate_the_overall_sentiment_when_the_negative_value_is_zero() {
        Double positive = 1.0;
        Double negative = 0.0;

        Double overallSentiment = this.sentimentScore.calculateOverallSentiment(positive, negative);

        assertThat(overallSentiment, is(0.0));
    }

    @Test
    public void it_should_calculate_the_rating_for_a_neutral_sentiment_correctly() {

        Double overallSentiment = 0.0;
        Double rating = this.sentimentScore.calculateStarRatingFromOverallSentiment(overallSentiment);

        assertThat(rating, is(3.0));
    }

    @Test
    public void it_should_calculate_the_rating_for_a_positive_sentiment_correctly() {

        Double overallSentiment = 1.0;
        Double rating = this.sentimentScore.calculateStarRatingFromOverallSentiment(overallSentiment);

        assertThat(rating, is(5.0));
    }

    @Test
    public void it_should_calculate_the_rating_for_a_negative_sentiment_correctly() {

        Double overallSentiment = -1.0;
        Double rating = this.sentimentScore.calculateStarRatingFromOverallSentiment(overallSentiment);

        assertThat(rating, is(1.0));
    }
}
