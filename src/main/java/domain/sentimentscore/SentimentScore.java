package domain.sentimentscore;

import com.google.common.math.DoubleMath;

/**
 * Class containing methods to calculate a sentiment score.
 */
public class SentimentScore {

    /**
     *
     * @param positive Double
     * @param negative Double
     * @return Double
     */
    public Double calculateOverallSentiment(Double positive, Double negative) {

        Double overallSentiment = DoubleMath.log2(positive / negative) / 2;

        if (overallSentiment.isNaN()) {
            return 0.0;
        }

        return overallSentiment;
    }

    /**
     *
     * @param overallSentiment Double
     * @return Double
     */
    public Double calculateStarRatingFromOverallSentiment(Double overallSentiment) {

        return 2 * overallSentiment + 3;
    }

}
