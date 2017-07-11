package domain.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AspectWordsTest {

    private AspectWords aspectWords;

    @Before
    public void setup() {
        this.aspectWords = new AspectWords(null);
    }

    @Test
    public void it_should_be_able_to_get_and_set_aspect_words() {

        List<String> aw = new ArrayList<>();
        aw.add("battery");
        aw.add("display");

        this.aspectWords.setAspectWords(aw);

        assertThat(this.aspectWords.getAspectWords(), is(aw));
    }
}
