package domain.sentimentanalysis.sentiwordnet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SentiWordNetBuilderTest {

    private SentiWordNetBuilder sentiWordNetBuilder;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {

        String swn3Source = "src/test/resources/sentiwordnet/sentiwordnet3.txt";

        this.sentiWordNetBuilder = new SentiWordNetBuilder(swn3Source);
    }

    @Test
    public void it_should_throw_an_exception_if_the_sentiwordnet_source_does_not_exist() {

        String wrongSwn3Source = "does/not/exist.txt";

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The SentiWordNet source must be supplied.");

        this.sentiWordNetBuilder = new SentiWordNetBuilder(wrongSwn3Source);
    }

    @Test
    public void it_should_extract_and_build_a_map_of_words_with_a_sentiment_score() {

        Map<String, Double> classifiedWords = this.sentiWordNetBuilder.getScoredWords();

        Map<String, Double> expectedOutput = new HashMap<>();
        expectedOutput.put("able-a", 0.1875);
        expectedOutput.put("unable-a", -0.48875);

        assertThat(classifiedWords, is(expectedOutput));
    }

    @Test
    public void it_should_extract_and_build_if_two_terms_are_mentioned_on_one_line() {

        String swn3Source = "src/test/resources/sentiwordnet/sentiwordnet3-multi.txt";

        this.sentiWordNetBuilder = new SentiWordNetBuilder(swn3Source);

        Map<String, Double> classifiedWords = this.sentiWordNetBuilder.getScoredWords();

        Map<String, Double> expectedOutput = new HashMap<>();
        expectedOutput.put("unvalued-a", -0.375);
        expectedOutput.put("unsung-a", -0.375);
        expectedOutput.put("unappreciated-a", -0.375);
        expectedOutput.put("granted-a", 0.0);
        expectedOutput.put("given-a", 0.0);

        assertThat(classifiedWords, is(expectedOutput));
    }

//    @Test
//    public void it_should_extract_and_build_the_entire_lexicon() {
//
//        String swn3Source = "src/main/resources/sentiwordnet/sentiwordnet3.txt";
//
//        this.sentiWordNetSentimentAnalysis = new SentiWordNetBuilder(swn3Source);
//
//        Map<String, Double> classifiedWords = this.sentiWordNetSentimentAnalysis.getScoredWords();
//
//        Map<String, Double> expectedOutput = new HashMap<>();
//
//        Double goodNoun = classifiedWords.get("good-n");
//        Double goodAdjective = classifiedWords.get("good-a");
//        Double goodAdverb = classifiedWords.get("good-r");
//
//        assertThat(classifiedWords, is(expectedOutput));
//    }
}
