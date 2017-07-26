package sentimentanalysis.reducer;

import domain.sentimentanalysis.SentimentAnalysis;
import domain.sentimentanalysis.sentiwordnet.SentiWordNetSentimentAnalysis;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reduces the web content based on URL and sentiment score.
 */
public class SentimentAnalysisReducer extends Reducer<Text, MapWritable, Text, MapWritable> {

    private SentimentAnalysis sentimentAnalysis;

    @Override
    public void setup(Reducer.Context context) {
        this.sentimentAnalysis = new SentiWordNetSentimentAnalysis();
    }

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
    public void reduce(
        Text key,
        Iterable<MapWritable> values,
        Context context
    ) throws IOException, InterruptedException {

        for (MapWritable review : values) {

            Writable asin               = review.get(new Text("asin"));
            Writable sentence           = review.get(new Text("sentence"));
            Writable normalisedSentence = review.get(new Text("normalisedSentence"));
            Writable aspectWord         = review.get(new Text("aspectWord"));

            MapWritable mapWritable = new MapWritable();
            mapWritable.put(new Text("asin"), asin);
            mapWritable.put(new Text("sentence"), sentence);
            mapWritable.put(new Text("normalisedSentence"), normalisedSentence);
            mapWritable.put(new Text("aspectWord"), aspectWord);
            mapWritable.put(
                new Text("sentimentScore"),
                new DoubleWritable(this.sentimentAnalysis.getSentiment(normalisedSentence.toString()))
            );

            context.write(key, mapWritable);
        }

    }
}