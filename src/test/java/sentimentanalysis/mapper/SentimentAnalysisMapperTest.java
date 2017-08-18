package sentimentanalysis.mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SentimentAnalysisMapperTest {

    private MapDriver<LongWritable, Text, Text, MapWritable> sentimentAnalysisMapper;

    private String testJson;

    @Before
    public void setUp() {
        SentimentAnalysisMapper mapper = new SentimentAnalysisMapper();

        sentimentAnalysisMapper = MapDriver.newMapDriver(mapper);

        Mapper.Context context = sentimentAnalysisMapper.getContext();
        Configuration configuration = context.getConfiguration();

        configuration.set("aspectWordsFilePath", "src/test/resources/aspectwords/aspect-words.json");

        testJson = "{"
            + "\"reviewerID\": \"AO94DHGC771SJ\","
            + "\"asin\": \"0528881469\","
            + "\"reviewerName\": \"amazdnu\","
            + "\"helpful\": [0, 0],"
            + "\"reviewText\": \"I bought the phone but I must state that I have never had "
            + "an electrical device with a battery that performs so poorly.\","
            + "\"overall\": 5.0,"
            + "\"summary\": \"Gotta have GPS!\","
            + "\"unixReviewTime\": 1370131200,"
            + "\"reviewTime\": \"06 2, 2013\""
            + "}";
    }

    @Test
    public void it_should_extract_a_single_sentence_containing_one_aspect_word_and_sentiment_word()
        throws IOException {

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text(testJson);

        List<Pair<Text, MapWritable>> output = sentimentAnalysisMapper.withInput(inputKey, inputValue).run();

        assertThat(output.get(0).getFirst(), is(new Text("0528881469")));

        MapWritable rsp1 = output.get(0).getSecond();

        assertThat(rsp1.get(new Text("asin")), is(new Text("0528881469")));
        assertThat(rsp1.get(new Text("reviewerName")), is(new Text("amazdnu")));
        assertThat(rsp1.get(new Text("reviewTime")), is(new Text("06 2, 2013")));

        MapWritable extractedAspects = (MapWritable) rsp1.get(new Text("extractedAspects"));

        MapWritable battery = (MapWritable) extractedAspects.get(new IntWritable(0));
        assertThat(battery.get(new Text("sentence")), is(new Text("I bought the phone but I must state that I have never had an electrical device with a battery that performs so poorly")));
        assertThat(battery.get(new Text("aspectWord")), is(new Text("battery")));
        assertThat(battery.get(new Text("sentenceTagged")), is(new Text("device_NN electrical_JJ never_RB state_VB")));
    }

    @Test
    public void it_should_extract_all_phrases_containing_aspect_words_and_sentiment_words_with_large_review()
        throws IOException {

        String testJson = "{"
            + "\"reviewerID\": \"AO94DHGC771SJ\","
            + "\"asin\": \"0528881469\","
            + "\"reviewerName\": \"amazdnu\","
            + "\"helpful\": [0, 0],"
            + "\"reviewText\": \"I bought the phone but I must state that I have never had "
            + "an electrical device with a battery that performs so poorly."
            + "I really feel that the display and screen were perfectly implemented.\","
            + "\"overall\": 5.0,"
            + "\"summary\": \"Gotta have GPS!\","
            + "\"unixReviewTime\": 1370131200,"
            + "\"reviewTime\": \"06 2, 2013\""
            + "}";

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text(testJson);

        List<Pair<Text, MapWritable>> output = sentimentAnalysisMapper.withInput(inputKey, inputValue).run();

        assertThat(output.get(0).getFirst(), is(new Text("0528881469")));

        // Battery
        MapWritable rsp1 = output.get(0).getSecond();

        assertThat(rsp1.get(new Text("asin")), is(new Text("0528881469")));
        assertThat(rsp1.get(new Text("reviewerName")), is(new Text("amazdnu")));
        assertThat(rsp1.get(new Text("reviewTime")), is(new Text("06 2, 2013")));

        MapWritable extractedAspects = (MapWritable) rsp1.get(new Text("extractedAspects"));

        MapWritable battery = (MapWritable) extractedAspects.get(new IntWritable(0));
        assertThat(battery.get(new Text("sentence")), is(new Text("I bought the phone but I must state that I have never had an electrical device with a battery that performs so poorly")));
        assertThat(battery.get(new Text("aspectWord")), is(new Text("battery")));
        assertThat(battery.get(new Text("sentenceTagged")), is(new Text("device_NN electrical_JJ never_RB state_VB")));

        MapWritable display = (MapWritable) extractedAspects.get(new IntWritable(1));
        assertThat(display.get(new Text("sentence")), is(new Text("I really feel that the display and screen were perfectly implemented")));
        assertThat(display.get(new Text("aspectWord")), is(new Text("display")));
        assertThat(display.get(new Text("sentenceTagged")), is(new Text("feel_VB really_RB")));

        MapWritable screen = (MapWritable) extractedAspects.get(new IntWritable(2));
        assertThat(screen.get(new Text("sentence")), is(new Text("I really feel that the display and screen were perfectly implemented")));
        assertThat(screen.get(new Text("aspectWord")), is(new Text("screen")));
        assertThat(screen.get(new Text("sentenceTagged")), is(new Text("display_NN feel_VB really_RB")));
    }
}