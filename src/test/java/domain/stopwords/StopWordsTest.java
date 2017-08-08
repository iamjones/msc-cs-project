package domain.stopwords;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A test for the methods of the StopWords class.
 */
public class StopWordsTest {

    private StopWords stopWords;

    @Before
    public void setUp() {
        this.stopWords = new StopWords();
    }

    @Test
    public void it_should_remove_stop_words_from_a_string_and_return_a_list() {

        String sentence = "This sentence contains some stop words";
        List<String> doc = Arrays.asList(sentence.split(" "));

        List<String> words = this.stopWords.removeStopWords(doc);

        assertThat(words.contains("sentence"), is(true));
        assertThat(words.contains("stop"), is(true));
        assertThat(words.contains("words"), is(true));
    }
}
