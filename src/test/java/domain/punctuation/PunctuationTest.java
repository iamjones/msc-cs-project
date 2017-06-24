package domain.punctuation;

import domain.punctuation.Punctuation;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PunctuationTest {

    private Punctuation punctuation;

    @Before
    public void setUp() {
        this.punctuation = new Punctuation();
    }

    @Test
    public void it_should_remove_all_types_of_punctuation_from_string() {

        String sentence = "Hello World!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        String expected = "Hello World";

        assertThat(this.punctuation.removeAllPunctuation(sentence), is(expected));
    }

    @Test
    public void it_should_remove_multiple_occurrences_of_the_same_punctuation() {

        String sentence = "Hello..............";
        String expected = "Hello";

        assertThat(this.punctuation.removeAllPunctuation(sentence), is(expected));
    }
}
