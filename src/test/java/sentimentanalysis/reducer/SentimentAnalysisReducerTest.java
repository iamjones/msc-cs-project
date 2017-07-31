package sentimentanalysis.reducer;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SentimentAnalysisReducerTest {

    private ReduceDriver<Text, MapWritable, Text, MapWritable> reduceDriver;

    @Before
    public void setUp() {
        SentimentAnalysisReducer sentimentAnalysisReducer = new SentimentAnalysisReducer();

        reduceDriver = ReduceDriver.newReduceDriver(sentimentAnalysisReducer);
    }

    @Test
    public void it_should_give_a_positive_score_and_store_the_data_for_a_positive_review()
        throws IOException {

        MapWritable review = new MapWritable();
        review.put(new Text("sentence"), new Text("The battery was very powerful."));
        review.put(new Text("normalisedSentence"), new Text("battery very powerful"));
        review.put(new Text("sentenceTagged"), new Text("battery_NN very_RB powerful_JJ"));
        review.put(new Text("asin"), new Text("ASINString"));
        review.put(new Text("aspectWord"), new Text("battery"));
        review.put(new Text("reviewerName"), new Text("John Smith"));
        review.put(new Text("reviewTime"), new Text("09 13, 2009"));

        Text inputKey = new Text("ASINString");
        List<MapWritable> inputValues = new ArrayList<>();
        inputValues.add(review);

        reduceDriver.withInput(inputKey, inputValues).run();

        // Check that this item is in Elasticseach
    }
}
