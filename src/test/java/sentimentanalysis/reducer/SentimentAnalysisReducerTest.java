package sentimentanalysis.reducer;

import domain.entity.ReviewWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;

public class SentimentAnalysisReducerTest {

    private ReduceDriver<Text, ReviewWritable, Text, ReviewWritable> reduceDriver;

    @Before
    public void setUp() {
        SentimentAnalysisReducer sentimentAnalysisReducer = new SentimentAnalysisReducer();

        reduceDriver = ReduceDriver.newReduceDriver(sentimentAnalysisReducer);
    }

//    @Test
//    public void it_should_give_a_positive_score_and_store_the_data_for_a_positive_review()
//        throws IOException {
//
//        Text inputKey = new Text("ASINString");
//        List<ReviewWritable> inputValues = new ArrayList<>();
//
//        ReviewWritable review = new ReviewWritable();
//
//
//
//
//        reduceDriver.withInput(inputKey, inputValues).run();
//    }
}
