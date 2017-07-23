package sentimentanalysis.reducer;

import domain.entity.ReviewWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class SentimentAnalysisReducerTest {

    private ReduceDriver<LongWritable, Text, Text, ReviewWritable> reduceDriver;

    @Before
    public void setUp() {
        SentimentAnalysisReducer sentimentAnalysisReducer = new SentimentAnalysisReducer();

        reduceDriver = ReduceDriver.newReduceDriver(sentimentAnalysisReducer);
    }

    @Test
    public void it_should_give_a_score_to_a_sentence() {

    }
}
