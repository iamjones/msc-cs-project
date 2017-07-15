package domain.postags;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PosTagsTest {

    private PosTags posTags;

    @Before
    public void setup() {
        this.posTags = new PosTags();
    }

    @Test
    public void it_should_classify_an_adverb_as_true() {

        assertThat(this.posTags.isAdverb("word_RB"), is(true));
    }

    @Test
    public void it_should_classify_a_comparative_adverb_as_true() {

        assertThat(this.posTags.isAdverb("word_RBR"), is(true));
    }

    @Test
    public void it_should_classify_a_superlative_adverb_as_true() {

        assertThat(this.posTags.isAdverb("word_RBS"), is(true));
    }

    @Test
    public void it_should_classify_a_wh_adverb_as_true() {

        assertThat(this.posTags.isAdverb("word_WRB"), is(true));
    }
}
