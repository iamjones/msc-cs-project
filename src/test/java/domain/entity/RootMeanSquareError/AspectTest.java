package domain.entity.RootMeanSquareError;

import domain.entity.RootMeanSequareError.Aspect;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AspectTest {

    private Aspect aspect;

    @Before
    public void setup() {
        this.aspect = new Aspect();
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_aspect_word() {
        String aspectWord = "battery";

        this.aspect.setAspectWord(aspectWord);

        assertThat(this.aspect.getAspectWord(), is(aspectWord));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_sentiment_score() {
        Double sentimentScore = 0.025;

        this.aspect.setSentimentScore(sentimentScore);

        assertThat(this.aspect.getSentimentScore(), is(sentimentScore));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_sentence() {
        String sentence = "The battery was good";

        this.aspect.setSentence(sentence);

        assertThat(this.aspect.getSentence(), is(sentence));
    }
}
