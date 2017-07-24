package domain.sentimentanalysis.sentiwordnet;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SentiWordNetSentimentAnalysisTest {

    private SentiWordNetSentimentAnalysis sentiWordNetSentimentAnalysis;

    @Before
    public void setup() {
        this.sentiWordNetSentimentAnalysis = new SentiWordNetSentimentAnalysis();
    }

    @Test
    public void it_should_classify_a_negative_document_as_negative() {

        String document = "the_DT battery_NN was_VBD very_RB bad_JJ bad_JJ bad_JJ bad_JJ bad_JJ bad_JJ";
        Double score    = this.sentiWordNetSentimentAnalysis.getSentiment(document);

        assertThat(score < 0, is(true));
    }

    @Test
    public void it_should_classify_a_positive_document_as_positive() {

        String document = "the_DT battery_NN was_VBD very_RB good_JJ good_JJ good_JJ good_JJ good_JJ";
        Double score    = this.sentiWordNetSentimentAnalysis.getSentiment(document);

        assertThat(score > 0, is(true));
    }
}
