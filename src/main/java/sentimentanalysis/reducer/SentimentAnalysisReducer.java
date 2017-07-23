package sentimentanalysis.reducer;

import domain.entity.ReviewWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reduces the web content based on URL and sentiment score.
 */
public class SentimentAnalysisReducer extends Reducer<LongWritable, Text, Text, ReviewWritable> {

    /**
     * Processes the mapped data by calculating the sentiment score and writing the score
     * plus meta data about the review to ElasticSearch.
     *
     * @param key Text
     * @param values Iterable<Text>
     * @param context Context
     * @throws IOException
     * @throws InterruptedException
     */
    public void reduce(Text key, Iterable<ReviewWritable> values, Context context) {

    }
}