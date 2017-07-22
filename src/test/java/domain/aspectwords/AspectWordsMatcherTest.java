package domain.aspectwords;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AspectWordsMatcherTest {

    private AspectWordsMatcher aspectWordsMatcher;

    @Before
    public void setup() {
        this.aspectWordsMatcher = new AspectWordsMatcher("src/test/resources/aspectwords/aspect-words.json");
    }

    @Test
    public void it_should_find_and_return_the_aspect_word() {

        String document = "The battery was very good";

        List<String> expected = new ArrayList<>();
        expected.add("battery");

        assertThat(this.aspectWordsMatcher.getMatchedAspectWords(document), is(expected));
    }

    @Test
    public void it_should_find_and_return_multiple_aspect_words() {

        String document = "The battery screen and display was very good";

        List<String> expected = new ArrayList<>();
        expected.add("battery");
        expected.add("screen");
        expected.add("display");

        assertThat(this.aspectWordsMatcher.getMatchedAspectWords(document), is(expected));
    }
}
