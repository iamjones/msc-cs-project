package domain.aspectwords;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AspectWordsMatcherTest {

    private AspectWordsMatcher aspectWordsMatcher;

    @Before
    public void setup() {
        this.aspectWordsMatcher = new AspectWordsMatcher("src/test/resources/aspectwords/aspect-words.json");
    }

    @Test
    public void it_should_identify_document_as_containing_an_aspect_word() {

        String document = "The aspect word is battery";

        assertThat(this.aspectWordsMatcher.containsAspectWord(document), is(true));
    }

    @Test
    public void it_should_identify_document_as_not_containing_an_aspect_word() {

        String document = "Does not contain an aspect word.";

        assertThat(this.aspectWordsMatcher.containsAspectWord(document), is(false));
    }
}
