package domain.aspectwords;

import domain.entity.AspectWords;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class JsonAspectWordsParserTest {

    private JsonAspectWordsParser jsonAspectWordsParser;

    @Rule
    public ExpectedException exceptions = ExpectedException.none();

    @Before
    public void setup() {
        this.jsonAspectWordsParser = new JsonAspectWordsParser();
    }

    @Test
    public void it_should_throw_an_illegal_argument_exception_if_the_file_is_not_found() {

        String wrongFilePath = "file/doesnt/exist.json";

        exceptions.expect(IllegalArgumentException.class);
        exceptions.expectMessage("File '" + wrongFilePath + "' does not exist.");

        this.jsonAspectWordsParser.parse(wrongFilePath);
    }

    @Test
    public void it_should_throw_an_illegal_argument_exception_if_the_file_contains_no_aspect_words() {

        String zeroAspectWords = "src/test/resources/aspectwords/zero-aspect-words.json";

        exceptions.expect(IllegalArgumentException.class);
        exceptions.expectMessage("File does not contain any aspect words.");

        this.jsonAspectWordsParser.parse(zeroAspectWords);
    }

    @Test
    public void it_should_extract_a_list_of_aspect_words_from_a_json_file() {

        String correctAspectWords = "src/test/resources/aspectwords/aspect-words.json";

        AspectWords aspectWords = this.jsonAspectWordsParser.parse(correctAspectWords);

        List<String> aw = aspectWords.getAspectWords();

        assertThat(aw.size(), is(3));
        assertThat(aw, hasItem("battery"));
        assertThat(aw, hasItem("screen"));
        assertThat(aw, hasItem("display"));
    }
}
