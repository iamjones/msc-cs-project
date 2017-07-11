package sentimentanalysis.reducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;

public class SentimentAnalysisReducerTest {

    private ReduceDriver<Text, Text, Text, DoubleWritable> reduceDriver;

    @Before
    public void setUp() {
        SentimentAnalysisReducer sentimentAnalysisReducer = new SentimentAnalysisReducer();

        reduceDriver = ReduceDriver.newReduceDriver(sentimentAnalysisReducer);
    }
}
