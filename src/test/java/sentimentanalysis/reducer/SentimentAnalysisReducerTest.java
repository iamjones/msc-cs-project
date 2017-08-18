package sentimentanalysis.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

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

        review.put(new Text("asin"), new Text("ASINString"));
        review.put(new Text("reviewerName"), new Text("John Smith"));
        review.put(new Text("reviewTime"), new Text("09 13, 2009"));

        MapWritable extractedAspects = new MapWritable();

        MapWritable battery = new MapWritable();
        battery.put(new Text("aspectWord"), new Text("battery"));
        battery.put(new Text("sentence"), new Text("The battery was very powerful"));
        battery.put(new Text("sentenceTagged"), new Text("battery_NN very_RB powerful_JJ"));

        extractedAspects.put(new IntWritable(0), battery);
        review.put(new Text("extractedAspects"), extractedAspects);

        Text inputKey = new Text("ASINString");
        List<MapWritable> inputValues = new ArrayList<>();
        inputValues.add(review);

        List<Pair<Text, MapWritable>> output = reduceDriver.withInput(inputKey, inputValues).run();

        MapWritable doc1 = output.get(0).getSecond();

        assertThat(output.get(0).getFirst(), is(new Text("ASINString")));

        assertThat(doc1.get(new Text("asin")), is(new Text("ASINString")));
        assertThat(doc1.get(new Text("reviewerName")), is(new Text("John Smith")));
        assertThat(doc1.get(new Text("reviewTime")), is(new Text("09 13, 2009")));

        MapWritable extractedAspectsResult = (MapWritable) doc1.get(new Text("extractedAspects"));

        MapWritable batteryResult = (MapWritable) extractedAspectsResult.get(new IntWritable(0));
        assertThat(batteryResult.get(new Text("sentence")), is(new Text("The battery was very powerful")));
        assertThat(batteryResult.get(new Text("aspectWord")), is(new Text("battery")));
        assertThat(Double.parseDouble(batteryResult.get(new Text("sentimentScore")).toString()), greaterThan(0.0));
    }

    @Test
    public void it_should_give_a_negative_score_and_store_the_data_for_a_positive_review() throws
        IOException {

        MapWritable review = new MapWritable();

        review.put(new Text("asin"), new Text("ABCDEFG123"));
        review.put(new Text("reviewerName"), new Text("Bill Jones"));
        review.put(new Text("reviewTime"), new Text("01 01, 2012"));

        MapWritable extractedAspects = new MapWritable();

        MapWritable screen = new MapWritable();
        screen.put(new Text("aspectWord"), new Text("screen"));
        screen.put(new Text("sentence"), new Text("The screen was not good value for money"));
        screen.put(new Text("sentenceTagged"), new Text("not_RB good_JJ value_NN money_NN"));

        extractedAspects.put(new IntWritable(0), screen);
        review.put(new Text("extractedAspects"), extractedAspects);

        Text inputKey = new Text("ABCDEFG123");
        List<MapWritable> inputValues = new ArrayList<>();
        inputValues.add(review);

        List<Pair<Text, MapWritable>> output = reduceDriver.withInput(inputKey, inputValues).run();

        MapWritable doc1 = output.get(0).getSecond();

        assertThat(output.get(0).getFirst(), is(new Text("ABCDEFG123")));

        assertThat(doc1.get(new Text("asin")), is(new Text("ABCDEFG123")));
        assertThat(doc1.get(new Text("reviewerName")), is(new Text("Bill Jones")));
        assertThat(doc1.get(new Text("reviewTime")), is(new Text("01 01, 2012")));

        MapWritable extractedAspectsResult = (MapWritable) doc1.get(new Text("extractedAspects"));

        MapWritable screenResult = (MapWritable) extractedAspectsResult.get(new IntWritable(0));
        assertThat(screenResult.get(new Text("sentence")), is(new Text("The screen was not good value for money")));
        assertThat(screenResult.get(new Text("aspectWord")), is(new Text("screen")));
        assertThat(Double.parseDouble(screenResult.get(new Text("sentimentScore")).toString()), lessThan(0.0));
    }
}
