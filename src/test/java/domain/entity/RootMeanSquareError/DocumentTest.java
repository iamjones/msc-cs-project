package domain.entity.RootMeanSquareError;

import domain.entity.RootMeanSequareError.Document;
import domain.entity.RootMeanSequareError.Review;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DocumentTest {

    private Document document;

    @Before
    public void setup() {
        this.document = new Document();
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_index() {
        String index = "reviews";

        this.document.set_index(index);

        assertThat(this.document.get_index(), is(index));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_type() {
        String type = "review";

        this.document.set_type(type);

        assertThat(this.document.get_type(), is(type));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_id() {
        String id = "AV4gREsXprM7Dc2VvvLr";

        this.document.set_id(id);

        assertThat(this.document.get_id(), is(id));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_score() {
        Double score = 1.0;

        this.document.set_score(score);

        assertThat(this.document.get_score(), is(score));
    }

    @Test
    public void it_should_be_able_to_set_and_get_the_review() {
        Review review = new Review();

        this.document.set_source(review);

        assertThat(this.document.get_source(), is(review));
    }
}
