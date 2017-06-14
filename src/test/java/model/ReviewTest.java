package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReviewTest {

    private Review review;

    @Before
    public void setUp() {
        this.review = new Review();
    }

    @Test
    public void should_be_able_to_set_and_get_reviewer_id() {

        this.review.setReviewerID("test-reviewer-id");

        assertThat(this.review.getReviewerID(), is("test-reviewer-id"));
    }

    @Test
    public void should_be_able_to_set_and_get_asin() {

        this.review.setAsin("123456ABC");

        assertThat(this.review.getAsin(), is("123456ABC"));
    }

    @Test
    public void should_be_able_to_set_and_get_reviewer_name() {

        this.review.setReviewerName("John Smith");

        assertThat(this.review.getReviewerName(), is("John Smith"));
    }

    @Test
    public void should_be_able_to_set_and_get_helpful() {

        List<Integer> helpful = new ArrayList<>();
        helpful.add(1);
        helpful.add(2);

        this.review.setHelpful(helpful);

        assertThat(this.review.getHelpful(), is(helpful));
    }

    @Test
    public void should_be_able_to_set_and_get_review_text() {

        this.review.setReviewText("This is a review.");

        assertThat(this.review.getReviewText(), is("This is a review."));
    }

    @Test
    public void should_be_able_to_set_and_get_overall() {

        this.review.setOverall(3.5);

        assertThat(this.review.getOverall(), is(3.5));
    }

    @Test
    public void should_be_able_to_set_and_get_summary() {

        this.review.setSummary("This is a summary review.");

        assertThat(this.review.getSummary(), is("This is a summary review."));
    }

    @Test
    public void should_be_able_to_set_and_get_unix_review_time() {

        this.review.setUnixReviewTime(1234567);

        assertThat(this.review.getUnixReviewTime(), is(1234567));
    }

    @Test
    public void should_be_able_to_set_and_get_review_time() {

        this.review.setReviewTime("01/01/2017 14:30:59");

        assertThat(this.review.getReviewTime(), is("01/01/2017 14:30:59"));
    }
}
