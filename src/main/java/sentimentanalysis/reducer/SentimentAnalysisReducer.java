package project.reducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reduces the web content based on URL and sentiment score.
 */
public class WebContentReducer extends Reducer<Text, Text, Text, DoubleWritable> {

    private final static String POSITIVE  = "Positive";
    private final static String NEGATIVE = "Negative";
    private final static String NEUTRAL = "Neutral";

    /**
     *
     * @param key Text
     * @param values Iterable<Text>
     * @param context Context
     * @throws IOException
     * @throws InterruptedException
     */
    public void reduce(Text key, Iterable<Text> values, Context context) {

    }
}