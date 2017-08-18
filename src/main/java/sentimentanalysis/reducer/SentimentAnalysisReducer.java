package sentimentanalysis.reducer;

import domain.sentimentanalysis.SentimentAnalysis;
import domain.sentimentanalysis.sentiwordnet.SentiWordNetSentimentAnalysis;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

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
//            Writable sentence           = review.get(new Text("sentence"));
//            Writable sentenceTagged     = review.get(new Text("sentenceTagged"));
//            Writable aspectWord         = review.get(new Text("aspectWord"));
            Writable reviewerName       = review.get(new Text("reviewerName"));
            Writable reviewTime         = review.get(new Text("reviewTime"));

            MapWritable extractedAspects = (MapWritable) review.get(new Text("extractedAspects"));

            extractedAspects.entrySet();

            MapWritable extractedAspectsClassified = new MapWritable();

            int c = 0;
            for (Map.Entry extractedAspect : extractedAspects.entrySet()) {

                MapWritable aspectMap = (MapWritable) extractedAspect.getValue();

                Double sentimentScore = this.sentimentAnalysis.getSentiment(aspectMap.get(new Text("sentenceTagged")).toString());

                if (!Double.isNaN(sentimentScore)) {

                    BigDecimal sentimentScoreRounded = BigDecimal.valueOf(sentimentScore).setScale(5, RoundingMode.HALF_UP);

                    MapWritable aspectClassification = new MapWritable();

                    aspectClassification.put(new Text("sentence"), aspectMap.get(new Text("sentence")));
                    aspectClassification.put(new Text("aspectWord"), aspectMap.get(new Text("aspectWord")));
                    aspectClassification.put(
                        new Text("sentimentScore"),
                        new Text(sentimentScoreRounded.toString())
                    );

                    extractedAspectsClassified.put(new IntWritable(c), aspectClassification);
                    c++;
                }
            }

            MapWritable mapWritable = new MapWritable();
            mapWritable.put(new Text("asin"), asin);

            if (reviewerName != null) {
                mapWritable.put(new Text("reviewerName"), reviewerName);
            }

            if (reviewTime != null) {
                mapWritable.put(new Text("reviewTime"), reviewTime);
            }

            mapWritable.put(new Text("extractedAspects"), extractedAspectsClassified);

            context.write(key, mapWritable);
        }
    }
}