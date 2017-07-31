package domain.postags;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class PosTagsTest {

    private PosTags posTags;

    @Before
    public void setup() {
        this.posTags = new PosTags();
    }

    @Test
    public void it_should_classify_a_noun_as_true() {

        assertThat(this.posTags.isNoun("word_NN"), is(true));
    }

    @Test
    public void it_should_classify_a_noun_plural_as_true() {

        assertThat(this.posTags.isNoun("word_NNS"), is(true));
    }

    @Test
    public void it_should_classify_a_noun_proper_noun_as_true() {

        assertThat(this.posTags.isNoun("word_NNP"), is(true));
    }

    @Test
    public void it_should_classify_a_noun_proper_noun_plural_as_true() {

        assertThat(this.posTags.isNoun("word_NNPS"), is(true));
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

    @Test
    public void it_should_classify_a_vb_verb_as_true() {

        assertThat(this.posTags.isVerb("word_VB"), is(true));
    }

    @Test
    public void it_should_classify_a_vbd_verb_as_true() {

        assertThat(this.posTags.isVerb("word_VBD"), is(true));
    }

    @Test
    public void it_should_classify_a_vbg_verb_as_true() {

        assertThat(this.posTags.isVerb("word_VBG"), is(true));
    }

    @Test
    public void it_should_classify_a_vbn_verb_as_true() {

        assertThat(this.posTags.isVerb("word_VBN"), is(true));
    }

    @Test
    public void it_should_classify_a_vbp_verb_as_true() {

        assertThat(this.posTags.isVerb("word_VBP"), is(true));
    }

    @Test
    public void it_should_classify_a_vbz_verb_as_true() {

        assertThat(this.posTags.isVerb("word_VBZ"), is(true));
    }

    @Test
    public void it_should_classify_a_jj_adjective_as_true() {

        assertThat(this.posTags.isAdjective("word_JJ"), is(true));
    }

    @Test
    public void it_should_classify_a_jjr_adjective_as_true() {

        assertThat(this.posTags.isAdjective("word_JJR"), is(true));
    }

    @Test
    public void it_should_classify_a_jjs_adjective_as_true() {

        assertThat(this.posTags.isAdjective("word_JJS"), is(true));
    }

    @Test
    public void it_should_return_a_string_of_words_if_an_adverb_is_within_range_of_the_aspect_word() {

        String document = "The_DT battery_NN was_VBD very_RB good_JJ";
        String aspectWord = "battery";
        int nGram = 5;

        String words = this.posTags.isAdverbInRangeOfAspectWord(document, aspectWord, nGram);

        assertThat(words, is("was_VBD very_RB good_JJ"));
    }

    @Test
    public void it_should_return_null_if_an_adverb_is_not_within_range_of_the_aspect_word() {

        String document = "The_DT battery_NN was_VBD good_JJ good_JJ good_JJ good_JJ good_JJ very_RB";
        String aspectWord = "battery";
        int nGram = 5;

        String words = this.posTags.isAdverbInRangeOfAspectWord(document, aspectWord, nGram);

        assertThat(words, is(nullValue()));
    }
}
